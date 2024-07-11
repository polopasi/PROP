package domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import domain.operations.Operations;
import helpers.InOut;

public class BoardGenerator {

    Board board;
    Random random;

    /**
     * BoardGenerator constructor. (Should be used exclusively by Board class).
     * 
     * @param board is the solver's board in where it will operate.
     */
    public BoardGenerator(Board board) {
        this.board = new Board(board);
        this.random = new Random();
    }

    public BoardGenerator(Board board, long seed) {
        this.board = new Board(board);
        this.random = new Random(seed);
    }

    public Optional<Board> generate() {
        ArrayList<Operations> operations = new ArrayList<>();
        operations.add(Operations.ADDITION);
        operations.add(Operations.PRODUCT);
        operations.add(Operations.SUBTRACTION);
        operations.add(Operations.DIVISION);
        operations.add(Operations.MODULUS);
        operations.add(Operations.POWER);
        return this.generateWithLimits(2, this.board.size, 50, operations);
    }

    public Optional<Board> generateWithLimits(int minPartitionSize, int maxPartitionSize, int mergeProb,
                                              List<Operations> operationsToInclude) {
        //Verificar si hay operaciones y el minPartitionSize es 1:
        if (operationsToInclude.isEmpty() && minPartitionSize > 1) {
            return Optional.empty();
        }
        //Hacer copia de las celdas antes de modificarlas para luego devolverlas:
        ArrayList<Cell> ogCells = this.board.cells.stream()
                                                .map(c -> new Cell(c))
                                                .collect(Collectors.toCollection(ArrayList::new));
        /*
        Resolveremos el tablero antes de todo para respetar las restricciones del usuario. Si el tablero tiene
        particiones a resolver inicialmente.
        */
        if (this.board.partitions.size() > 0) {
            this.board = new Board(this.board.size, this.board.cells, this.board.partitions).asSolver().solve().orElse(new Board());
        }
        if (this.board.equals(new Board())) {
            return Optional.empty();
        }
        //Ver las operaciones que tenemos disponibles:
        ArrayList<Operations> sortedOperations = new ArrayList<>();
        ArrayList<Operations> nonLimitedOperations = new ArrayList<>();
        ArrayList<Operations> limitedOperations = new ArrayList<>();
        for (var operation : operationsToInclude) {
            switch (operation) {
                case ADDITION:
                case PRODUCT:
                    nonLimitedOperations.add(operation);
                    break;
                case POWER:
                case MODULUS:
                    limitedOperations.add(operation);
                default:
                    break;
            }
        }
        
        if (nonLimitedOperations.isEmpty()) {
            if (minPartitionSize > 2) {
                return Optional.empty();
            }
            maxPartitionSize = 2;
        }
        for (var op : nonLimitedOperations) {
            sortedOperations.add(op);
        }
        for (var op : limitedOperations) {
            sortedOperations.add(op);
        }
        if (operationsToInclude.contains(Operations.SUBTRACTION)) {
            sortedOperations.add(Operations.SUBTRACTION);
        }
        if (operationsToInclude.contains(Operations.DIVISION)) {
            sortedOperations.add(Operations.DIVISION);
        }
        //Una vez hayamos verificado los valores y limpiado casos de error, vamos a generar el tablero comenzando por las particiones.
        int nCells = this.board.cells.size();
        for (int i = 0; i < nCells; ++i) {
            if (this.board.cells.get(i).getPartition() == -1) {
                this.generatePartitions(i, this.board.partitions.size(), minPartitionSize, maxPartitionSize,
                        mergeProb);
            }
        }
        //Una vez generadas las particiones, llenamos el tablero de números y creamos las particiones.
        var partitionsData = getPartitionsWithValuesAndPositions();
        if (this.fillBoardWithNumbers(sortedOperations, partitionsData)) {
            this.assignOperations(sortedOperations, partitionsData);
            return Optional.of(this.board);
        }
        return Optional.empty();
    }

    private void generatePartitions(int cellIndex, int partitionToAssign, int minPartitionSize, int maxPartitionSize, int mergeProb) {
        final Cell cell = this.board.cells.get(cellIndex);
        final Partition partition = this.getPartition(partitionToAssign);
        if (cell.getPartition() == -1) {
            this.board.cells.set(cellIndex, new Cell(cell.getValue().orElse(0), partitionToAssign));
            partition.setSize(partition.getSize() + 1);
        }
        var neighbourCells = getNeighbours(cellIndex);
        for (int neighbour : neighbourCells) {
            if (neighbour == -1) {
                continue;
            }
            if (this.board.cells.get(neighbour).getPartition() == -1) {
                int prob = random.nextInt(100);
                if ((partition.size < minPartitionSize) || (partition.size < maxPartitionSize && prob < mergeProb)) {
                    this.generatePartitions(neighbour, partitionToAssign, minPartitionSize, maxPartitionSize,
                            mergeProb);
                }
            }
        }
    }

    private boolean fillBoardWithNumbers(List<Operations> operations, int[][][] partitionsData) {
        @SuppressWarnings("unchecked")
        TreeSet<Integer>[] rows = new TreeSet[this.board.size];
        @SuppressWarnings("unchecked")
        TreeSet<Integer>[] cols = new TreeSet[this.board.size];

        var set = IntStream.range(1, this.board.size + 1).boxed().collect(Collectors.toSet());

        Arrays.setAll(rows, e -> new TreeSet<>(set));
        Arrays.setAll(cols, e -> new TreeSet<>(set));
        
        for (int row = 0; row < this.board.size; ++row) {
            for (int col = 0; col < this.board.size; ++col) {
                int cellValue = getCellValue(row, col);
                rows[row].remove(cellValue);
                cols[col].remove(cellValue);
            }
        }
        var valuesToAccess = IntStream.range(1, this.board.size + 1).toArray();
        shuffleArray(valuesToAccess);
        return this.fillWithRandomNumbers(0, valuesToAccess, rows,
                                          0,             cols,
                                          partitionsData[0],                  operations,
                                          partitionsData[1]);
    }

    private boolean fillWithRandomNumbers(
        int partitionIndex, 
        int[] valuesToAccess, 
        TreeSet<Integer>[] rowValues,
        int currentPartitionCell,
        TreeSet<Integer>[] colValues,
        int[][] values,
        List<Operations> operations,
        int[][] positions)
    {
        if (partitionIndex >= this.board.partitions.size()) {
            return true;
        }
        // final int partitionID = partitions[partitionIndex];
        final int partitionID = partitionIndex;
        if (currentPartitionCell >= positions[partitionID].length) {
            return isPossible(values[partitionID], operations) && fillWithRandomNumbers(
                partitionIndex + 1,
                valuesToAccess, 
                rowValues,
                0,
                colValues,
                values,
                operations,
                positions
            );
        }
        
        // final Partition partition = this.board.partitions.get(partitionID);
        int cellIndex = positions[partitionID][currentPartitionCell];
        final Cell cell = this.board.cells.get(cellIndex);
        int cellRow = cellIndex / this.board.size;
        int cellCol = cellIndex % this.board.size;
        final var rowV = rowValues[cellRow];
        final var colV = colValues[cellCol];
        
        if (values[partitionID][currentPartitionCell] != 0) {
            return fillWithRandomNumbers(
                partitionIndex,
                valuesToAccess, 
                rowValues,
                currentPartitionCell+1,
                colValues,
                values,
                operations,
                positions
            );
        }

        for (int value : valuesToAccess) {
            //Verificar que el valor es válido.
            if (!rowV.contains(value) || !colV.contains(value)) {
                continue;
            }
            cell.setValue(value);
            values[partitionID][currentPartitionCell] = value;
            rowV.remove(value);
            colV.remove(value);
            
            final boolean filled = fillWithRandomNumbers(
                partitionIndex,
                valuesToAccess, 
                rowValues,
                currentPartitionCell+1,
                colValues,
                values,
                operations,
                positions);
            if (filled) {
                return true;
            }
            // Si no esta llena, significa que algo ha fallado, continuamos probando valores
            rowV.add(value);
            colV.add(value);
            cell.setValue(0);
            values[partitionID][currentPartitionCell] = 0;
        }
        return false;
    }

    private boolean isPossible(int[] values, List<Operations> operations) {
        if (values.length == 1) {
            return true;
        }
        if (values.length == 2) {
            for (var op : operations) {
                if (op == Operations.DIVISION && values[0] % values[1] != 0) {
                    return false;
                }
                else if (op == Operations.SUBTRACTION && values[0] < values[1]) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        //La suma y la multiplicación funciona con todo.
        return (operations.contains(Operations.ADDITION) || operations.contains(Operations.PRODUCT));
    }

    private void assignOperations(List<Operations> operations, int[][][] partitionsData) {
        for (int i = 0; i < this.board.partitions.size(); ++i) {
            final var partition = this.board.partitions.get(i);
            assignRandomOp(partition, operations, partitionsData[0][i], partitionsData[1][i]);
        }
    }

    private void assignRandomOp(Partition partition, List<Operations> operations, int[] values, int[] cellPositions) {
        if (partition.getOperations() == Operations.NoOp) {
            if (partition.getSize() == 0) {
                return;
            }
            if (partition.getSize() == 1) {
                partition.setOperations(Operations.NoOp);
                partition.setResult(this.board.cells.get(cellPositions[0]).getValue().get());
            } else {
                partition.setOperations(getRandomOp(partition, operations, values));
                partition.setResult(partition.operate(values));
            }
        }
    }

    private Operations getRandomOp(Partition partition, List<Operations> operations, int[] values) {
        // operations map: [Add, Prod, Mod, Pow, Sub, Div].
        ArrayList<Operations> possibleOperations = new ArrayList<Operations>();
        for (final var op : operations) {
            if (domain.operations.Operation.fromEnum(op).operate(values) != -1) {
                possibleOperations.add(op);
            }
        }
        return operations.get(random.nextInt(possibleOperations.size()));
    }

    private int getCellValue(int row, int column) {
        return this.board.cells.get(getCellIndex(row, column)).getValue().orElse(0);
    }

    private int getCellIndex(int row, int column) {
        return row * this.board.size + column;
    }

    private int[][][] getPartitionsWithValuesAndPositions() {
        final int[][] partitionsWithPositions = new int[this.board.partitions.size()][];
        final int[][] partitionsWithValues = new int[this.board.partitions.size()][];
        final int[] partitionSizes = new int[this.board.partitions.size()];

        for (int i = 0; i < this.board.partitions.size(); i++) {
            final var partition = this.board.partitions.get(i);
            partitionsWithValues[i] = new int[partition.getSize()];
            partitionsWithPositions[i] = new int[partition.getSize()];
        }

        for (int index = 0; index < this.board.cells.size(); ++index) {
            final var cell = this.board.cells.get(index);
            final var partition = cell.getPartition();
            final var partitionPositionsArray = partitionsWithPositions[partition];
            final var partitionValuesArray = partitionsWithValues[partition];

            int value = cell.getValue().orElse(0).intValue();
            int lastCellInPartition = partitionSizes[partition];

            partitionValuesArray[lastCellInPartition] = value;
            partitionPositionsArray[lastCellInPartition] = index;
            partitionSizes[partition] += 1;
        }
        return new int[][][] { partitionsWithValues, partitionsWithPositions };
    }

    private int[] getNeighbours(int cellIndex) {
        int[] validNeighbours = new int[] { -1, -1, -1, -1 };
        int[] directions = { -this.board.size, // Top
                1, // Right
                this.board.size, // Bottom
                -1 }; // Left
        for (int i = 0; i < directions.length; ++i) {
            int cellDirection = cellIndex + directions[i];
            if (cellIndex % this.board.size == 0 && i == 3) {
                continue;
            }
            if (cellIndex % this.board.size == this.board.size - 1 && i == 1) {
                continue;
            }
            if (cellDirection < 0 || cellDirection >= this.board.cells.size()) {
                continue;
            }
            validNeighbours[i] = cellDirection;
        }
        return validNeighbours;
    }

    private Partition getPartition(int index) {
        if (index >= this.board.partitions.size()) {
            // Crear partition.
            Partition newPartition = new Partition(0, 0, Operations.NoOp);
            this.board.partitions.add(index, newPartition);
        }
        return this.board.partitions.get(index);
    }

    private void shuffleArray(int[] array) {
        int index, temp;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = this.random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}