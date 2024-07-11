package domain;

import java.util.*;
import java.util.stream.Collectors;

import domain.operations.Operations;
import helpers.InOut;

/**
 * Implements the solver controller which adds system functions to the board class.
 * 
 * @author Alexander Martínez
 */
public class BoardSolver extends Board {

    /**
     * BoardSolver constructor. (Should be used exclusively by Board class).
     * 
     * @param board is the solver's board in where it will operate.
     */
    public BoardSolver(Board board) {
        this.cells = board.cells.stream().map(c -> new Cell(c)).collect(Collectors.toCollection(ArrayList::new));
        this.partitions = board.partitions.stream().map(p -> new Partition(p)).collect(Collectors.toCollection(ArrayList::new));
        this.size = board.size;
    }

    /**
     * 
     * Checks if Board is completely solved.
     * 
     * @return false if there are conflicts between the cell values or any of its partitions are not solved. True otherwise.
     */
    public boolean isSolved() {
        var validatedPartitions = getValidatedPartitions();
        int[][] partitionsWithValues = getPartitionsWithValuesAndPositions()[0];
        for (int index = 0; index < this.partitions.size(); ++index) {
            final Partition partition = this.partitions.get(index);
            if (partition.result == 0 && partition.operations == Operations.NoOp) {
                // System.err.println("\nEmpty partition: " + InOut.toPrintableString(partition));
                return false;
            }
            boolean partitionSolved = partition.isSolved(partitionsWithValues[index]);
            if (!partitionSolved || !validatedPartitions[index]) {
                // System.err.println("\nPartition Solved: " + partitionSolved);
                // System.err.println("Validated: " + validatedPartitions[index]);
                // System.err.println(InOut.toPrintableString(partition));
                // System.err.println(InOut.toPrintableString(partitionsWithValues[index]));
                return false;
            }
        }

        for (final Cell c : this.cells) {
            if (c.getPartition() == -1 || c.getValue().isEmpty()) {
                // System.err.println("Cell empty or with no partition: " + InOut.toPrintableString(c));
                return false;
            }
        }
        return true;
    }

    private boolean isSolved(int[][] partitionsWithValues) {
        var validatedPartitions = getValidatedPartitions();
        for (int index = 0; index < this.partitions.size(); ++index) {
            boolean partitionSolved = this.partitions.get(index).isSolved(partitionsWithValues[index]);
            if (!(partitionSolved && validatedPartitions[index])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all the partitions of the board meet the minimal requirements.
     * 
     * @return true if the structure of the board's partitions is satisfactory.
     */
    public boolean isCorrect() {
        for (final var partition : this.partitions) {
            if (!partition.isCorrect(this.size)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the state of every cell in the board.
     * 
     * If the value of a cell is repeated within the same row and column, the cell's state is considered to be wrong.
     * On the other hand, if a cell value is unique withing the same row and column, the cell is considered to be valid.
     * 
     * @return A boolean array containing the state of every cell of the board.
     */
    public boolean[] getValidated() {
        boolean[] validatedArray = new boolean[this.cells.size()];
        Arrays.fill(validatedArray, true);
        //Tells me if a cell has been validated by its row.
        boolean[] validatedCellRow = new boolean[this.cells.size()];
        //Tells me if a cell has been validated by its column.
        boolean[] validatedCellCol = new boolean[this.cells.size()];
        for (int row = 0; row < this.size; ++row) {
            for (int column = 0; column < this.size; ++column) {
                //Verificamos la celda en cuestión
                int cellValue = getCellValue(row, column);
                int cellIndex = getCellIndex(row, column);
                if (cellValue == 0) {
                    continue;
                }
                boolean isFullyChecked = !validatedArray[cellIndex] &&
                                         validatedCellRow[cellIndex] &&
                                         validatedCellCol[cellIndex];
                if (isFullyChecked) {
                    continue;
                }
                //No need to check cells in the same row. Has already been done by another cell of the row!.
                if (!validatedCellRow[cellIndex]) {
                    //Check cells in the same row!
                    validatedCellRow[cellIndex] = true;
                    for (int cellRow = column + 1; cellRow < this.size; ++cellRow) {
                        int currentCellValue = getCellValue(row, cellRow);
                        int currentCellIndex = getCellIndex(row, cellRow);
                        if (currentCellValue == 0) {
                            continue;
                        }
                        if (currentCellValue == cellValue) {
                            validatedArray[cellIndex] = false;
                            validatedArray[currentCellIndex] = false;
                            validatedCellRow[currentCellIndex] = true;
                        }
                    }
                }
                //No need to check the cell at all because its row and column have been checked for duplicates already!.
                if (!validatedCellCol[cellIndex]) {
                    //Check cells in the same column!.
                    validatedCellCol[getCellIndex(row, column)] = true;
                    for (int cellCol = row + 1; cellCol < this.size; ++cellCol) {
                        int currentCellValue = getCellValue(cellCol, column);
                        int currentCellIndex = getCellIndex(cellCol, column);
                        if (currentCellValue == 0) {
                            continue;
                        }
                        if (currentCellValue == cellValue) {
                            validatedArray[cellIndex] = false;
                            validatedArray[currentCellIndex] = false;
                            validatedCellCol[currentCellIndex] = true;
                        }
                    }
                }
            }
        }
        return validatedArray;
    }

    /**
     * Checks the state of every partition in the board.
     * 
     * A partition's state is considered to be valid when the state of all of its cells is valid.
     * 
     * If any state of the partition's cells is not valid, the partition will be considered invalid.
     * 
     * @return A boolean array containing the state of every partition of the board.
     */
    public boolean[] getValidatedPartitions() {
        boolean[] validatedPartitions = new boolean[this.partitions.size()];
        Arrays.fill(validatedPartitions, true);
        boolean[] validatedCells = this.getValidated();
        for (int index = 0; index < validatedCells.length; ++index) {
            if (validatedCells[index] == false) {
                validatedPartitions[this.cells.get(index).getPartition()] = false;
            }
        }
        return validatedPartitions;
    }

    /**
     * 
     * @param row Row of the cell.
     * @param column Column of the cell
     * @return The value of the cell with position (row, column).
     */
    private int getCellValue(int row, int column) {
        return this.cells.get(getCellIndex(row, column)).getValue().orElse(0);
    }

    /**
     * 
     * @param row Row of the cell.
     * @param column Column of the cell
     * @return The array index of the cell with position (row, column).
     */
    private int getCellIndex(int row, int column) {
        return row * this.size + column;
    }

    /**
     * 
     * @return A two dimensional array where every element is an array resembling a partition with all its cell values.
     */
    private int[][][] getPartitionsWithValuesAndPositions() {
        final int[][] partitionsWithPositions = new int[this.partitions.size()][];
        final int[][] partitionsWithValues = new int[this.partitions.size()][];
        final int[] partitionSizes = new int[this.partitions.size()];

        for (int i = 0; i < this.partitions.size(); i++) {
            final var partition = this.partitions.get(i);
            partitionsWithValues[i] = new int[partition.getSize()];
            partitionsWithPositions[i] = new int[partition.getSize()];
        }
        
        for (int index = 0; index < this.cells.size(); ++index) {
            final var cell = this.cells.get(index);
            final var partition = cell.getPartition();
            
            if (partition == -1) {
                continue;
            }

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

    /**
     * Solves the kenken board.
     * 
     * @return A solved clone of the original board.
     */
    public Optional<Board> solve() {
        if (this.partitions.size() > 0) {
            final int[][][] partitionsWithValuesAndPositions = this.getPartitionsWithValuesAndPositions();
            final var partitionsWithValues = partitionsWithValuesAndPositions[0];
            final var partitionsWithPositions = partitionsWithValuesAndPositions[1];
            //boardClone gets modified in kenkenSolver and returns true if a solution was found.
            if (kenkenSolver(partitionsWithValues, partitionsWithPositions, 0, 0)) {
                return Optional.of(this);
            }
        }
        return Optional.empty();
    }

    /**
     * @param board is the board to solve
     * @return the solved board
     */
    private boolean kenkenSolver(int[][] partitionsWithValues, int[][] partitionsWithPositions, int currentPartition, int currentPartitionCell) {
        //Ya se han llenado todas las particiones, por lo tanto, reviso si el tablero está solucionado.
        if (currentPartition == this.partitions.size()) {
            return isSolved(partitionsWithValues);
        }
        //Aún no se han revisado todas las particiones.
        var partitionValues = partitionsWithValues[currentPartition];
        var partition = this.partitions.get(currentPartition);
        //Reviso si para la partición actual ya está resuelta, por lo cual no hace falta revisar las celdas.
        if (partition.isSolved(partitionValues)) {
            return kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition + 1, 0);
        }
        //Si la partición es trivial, intento llenar el resultado. Si esto genera conflictos, 
        if (partition.getSize() == 1) {
            final int cellIndex = partitionsWithPositions[currentPartition][currentPartitionCell];
            final Cell cell = this.cells.get(cellIndex);
            int solution = partition.getResult();
            
            if (cell.getValue().orElse(solution) != solution) {
                return false;
            }

            cell.setValue(solution);
            partitionValues[currentPartitionCell] = solution;
            if (isValid(cellIndex)) {
                return kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition + 1, 0);
            }
            else {
                return false;
            }
        }
        if (partition.getOperations() == Operations.NoOp) {
            return kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition + 1, 0);
        }
        //La partición no está resuelta aún. Por tanto, miro sus celdas para resolverla.
        //Si ya he revisado todas las celdas, miro si he encontrado solución o no.
        if (currentPartitionCell == partitionsWithValues[currentPartition].length) {
            var partitionCells = partitionsWithValues[currentPartition];
            if (this.partitions.get(currentPartition).isSolved(partitionCells)) {
                return kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition + 1, 0);
            }
            return false;
        }
        //Si la celda no está resuelta aún, miro de resolverla.
        if (partitionValues[currentPartitionCell] == 0) {
            final int cellIndex = partitionsWithPositions[currentPartition][currentPartitionCell];
            final Cell cell = this.cells.get(cellIndex);
            for (int value = 1; value <= this.size; ++value) {
                cell.setValue(value);
                if (!isValid(cellIndex)) {
                    continue;
                }
                partitionValues[currentPartitionCell] = value;
                if (kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition, currentPartitionCell + 1)) {
                    return true;
                }
            }
            cell.setValue(0);
            partitionValues[currentPartitionCell] = 0;
        }
        if (currentPartitionCell > 0) {
            return false;
        }
        return kenkenSolver(partitionsWithValues, partitionsWithPositions, currentPartition, currentPartitionCell + 1);
    }

    private boolean isValid(int index) {
        int cellRow = index / this.size;
        int cellCol = index % this.size;
        //Verificar filas.
        int cellValue = getCellValue(cellRow, cellCol);
        for (int row = 0; row < this.size; ++row) {
            if (row == cellRow) {
                continue;
            }
            if (getCellValue(row, cellCol) == cellValue) {
                return false;
            }
        }
        for (int col = 0; col < this.size; ++col) {
            if (col == cellCol) {
                continue;
            }
            if (getCellValue(cellRow, col) == cellValue) {
                return false;
            }
        }
        return true;
    }
}