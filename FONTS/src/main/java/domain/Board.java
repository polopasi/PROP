package domain;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.operations.*;

/**
 * Board contains all the data, cells and partitions of a kenken board.
 * A kenken board has a size N, which means there are N*N cells.
 * @author Pol Garcia Vernet
 */
public class Board {
    
    //Attributes

    protected int size;

    protected ArrayList<Cell> cells;
    
    protected ArrayList<Partition> partitions;

    //Constructors

    /** 
     * Creates an instance of Board empty and size 0.
     */
    public Board() {
        this.size = 0;
        cells = new ArrayList<Cell>();
        partitions = new ArrayList<Partition>();
    }

    /** 
     * Creates an instance of Board given a clone.
     * @param size is the size of board.
     * @param cells are the cells of board.
     * @param partitions are the partitions of board.
     */
    public Board(int size, ArrayList<Cell> cells, ArrayList<Partition> partitions) {
        this.size = size;
        this.cells = cells;
        this.partitions = partitions;
    }
        
    /** 
     * Creates an instance of Board given a clone.
     * @param clone is the clone.
     */
    public Board(Board clone) {
        this.size = clone.size;
        this.cells = clone.cells.stream().map(c -> new Cell(c)).collect(Collectors.toCollection(ArrayList::new));
        this.partitions = clone.partitions.stream().map(p -> new Partition(p)).collect(Collectors.toCollection(ArrayList::new));
    }

    //Getters
    
    /**
     * Gets the size of the board.
     * @return the size of the board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the partitions of board.
     * @return the partitions of board.
     */
    public ArrayList<Partition> getPartitions() {
        return partitions;
    }

    /**
     * Gets the cells of board.
     * @return the cells of board.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    //Setters

    /**
     * Sets size of board.
     * @param size is size of board.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets partitions of board.
     * @param partition are the partitions
     */
    public void setPartitions(ArrayList<Partition> partition) {
        this.partitions = partition;
    }

    /**
     * Sets cells of board.
     * @param cell are the cells.
     */
    public void setCells(ArrayList<Cell> cell) {
        this.cells = cell;
    }

    /**
     * Reads and converts a kenken file in standard PROP format to a new Board.
     * The operations (+ - * / expo %) are numbered this way:
     * Addition - 1
     * Substraction - 2
     * Product - 3
     * Division - 4
     * Power - 5
     * Modulus - 6
     * @param file file that defines a kenken board with the standard format of PROP.
     * @return returns the resulting board or a String if file is incorrect.
     */
    public static Board fromFile(String file) throws ParseException {
        try {

            //We split the file into a string[] without " " or "\n".
            String[] s = file.split("\\s+");
            
            ArrayList<Partition> partitionsBoard = new ArrayList<Partition>();
            
            //Sets size of board
            int sizeBoard;
            try {
                sizeBoard = Integer.parseInt(s[0]);
            } catch (Exception e) {
                throw new ParseException ("Expected an integer, instead got " + s[0] + ".", 0);
            }
            
    
            //Total of partitions of the board
            int numPartitions;
            try {
                numPartitions = Integer.parseInt(s[1]);        
            } catch (Exception e) {
                throw new ParseException("Expected an integer, instead got " + s[1] + ".", 0);
            }

            if (numPartitions == 0) {
                throw new ParseException("Number of Regions R is 0.", 0);
            }
            
            //Array of bool to catch exceptions later
            ArrayList<Boolean> visitedCell = new ArrayList<Boolean>(Collections.nCopies(sizeBoard*sizeBoard, false));

            //Creates an ArrayList of empty cells of size sizeBoard*sizeBoard.
            Cell cellEmpty = new Cell(0, 0);
            ArrayList<Cell> cellsBoard = new ArrayList<>(Collections.nCopies(sizeBoard*sizeBoard, cellEmpty));
            
            int i = 2;
            //for each partition
            for (int j = 0; j < numPartitions; ++j) {
                //Operation of the partition
                Integer op;
                try {
                    op = Integer.parseInt(s[i]);        
                } catch (Exception e) {
                    throw new ParseException("Expected an integer, instead got " + s[i] + ".", 0);
                }
                if (op < 0 || op > 6) throw new ParseException("Invalid operation: " + op + " must be 0 =< op <= 6" + ".", 0);
                Operations operation = toOperations(op);
                ++i;

                //Result of the partition
                int result;
                try {
                    result = Integer.parseInt(s[i]);        
                } catch (Exception e) {
                    throw new ParseException("Expected an integer, instead got " + s[i] + ".", 0);
                }
                ++i;

                //Number of cells inside the partition
                int numCellsOfPartition;
                try {
                    numCellsOfPartition = Integer.parseInt(s[i]);        
                } catch (Exception e) {
                    throw new ParseException("Expected an integer, instead got " + s[i] + ".", 0);
                }
                ++i;
    
                //for each cell inside the partition
                for (int k = 0; k < numCellsOfPartition; ++k) {
                    //Gets the row of the cell
                    int rowCell;
                    try {
                        rowCell = Integer.parseInt(s[i]);        
                    } catch (Exception e) {
                        throw new ParseException("Expected an integer, instead got " + s[i] + ".", 0);
                    }
                    ++i;
                    
                    //Gets the column of the cell
                    int colCell;
                    try {
                        colCell = Integer.parseInt(s[i]);        
                    } catch (Exception e) {
                        throw new ParseException("Expected an integer, instead got " + s[i] + ".", 0);
                    }
                    ++i;

                    /*
                     * If rows or column are negative or 0, or greater than sizeBoard, throws ParseException
                     */
                    if (rowCell <= 0 || rowCell > sizeBoard) {
                        throw new ParseException("Wrong row of Cell[" + rowCell + "][" + colCell + "]. Must be 0 < row <= " + sizeBoard, 0);
                    }
                    if (colCell <= 0 || colCell > sizeBoard) {
                        throw new ParseException("Wrong column of Cell[" + rowCell + "][" + colCell + "]. Must be 0 < column <= " + sizeBoard, 0);
                    }

                    int value = 0;
                    /*
                    * Checks if there's [content] inside the cell:
                     * evaluates if we are at the end of the String[] s and
                     * evaluates if the first char of the String s[i] is a '['
                     * in case it's true, it reads the number between [ cont ]
                     *                                                ^  ^   ^ 
                     *                                        charAt  0  1   2 
                     */
                    if (i < s.length && s[i].charAt(0) == '[') {
                        //Checks if the value inside [value] is a number
                        if (!Character.isDigit(s[i].charAt(1))) {
                            throw new ParseException("value of Cell[" + rowCell + "][" + colCell + "] is not a digit", 0);
                        }

                        //Gets the content of the cell. For example [76] -> 76
                        String valueString = s[i].substring(1, s[i].length() - 1);
                        try {
                            value = Integer.parseInt(valueString);        
                        } catch (Exception e) {
                            throw new ParseException("Expected an integer, instead got " + valueString + ".", 0);
                        }
                        ++i;
                        
                        /*
                         * Checks if value is < 0. If true, throws a ParseException.
                         */
                        if (value < 0) {
                            throw new ParseException("value of Cell[" + rowCell + "][" + colCell + "] < 0", 0);
                        }

                        /*
                         * Checks if value is > sizeBoard. If true, throws a ParseException
                         */
                        if (value > sizeBoard) {
                            throw new ParseException("value of Cell[" + rowCell + "][" + colCell + "] > " + sizeBoard, 0);
                        }
                    } 
                    /*
                    * adds a cell with position [rowCell][colCell], value value and partition j + 1.
                    * as all cells are stored in an arrayList,
                    * the position of a cell will be (rowCell - 1)*sizeBoard + colCell - 1
                    * the minus one is because the first rows and columns starting at index 1!
                    */
                    Cell c = new Cell(value, j);
                    cellsBoard.set((sizeBoard*(rowCell - 1) + colCell - 1), c);

                    /*
                     * Checks if this Cell has been set before. If true, throws a ParseException.
                     */
                    if (visitedCell.get((sizeBoard*(rowCell - 1) + colCell - 1)) == false) {
                        visitedCell.set((sizeBoard*(rowCell - 1) + colCell - 1), true);
                    }
                    else throw new ParseException("Duplicated Cell[" + rowCell + "][" + colCell + "]", 0);
                }
                /*
                 * Creates and adds a Partition(size, result, operation) to partitions.
                */
                Partition p = new Partition(numCellsOfPartition, result, operation);
                partitionsBoard.add (p);
            }       
            if (i < s.length) throw new ParseException("Input bigger than size of board", 0);
            
            for (boolean visited : visitedCell) {
                if (!visited) throw new ParseException("Input smaller than size of board", 0);
            }

            return new Board(sizeBoard, cellsBoard, partitionsBoard);
        }
        catch (Exception e) {    
            if (e.toString().contains("java.lang.ArrayIndexOutOfBoundsException:") )
                throw new ParseException("[ParseException] Error when reading file: number of cells does not correspond to board size", 0);
            throw new ParseException("[ParseException] Error when reading file: " + e + "\n", 0);
        }
    }

    /**
     * Returns the value of a Cell given its position [x][y].
     * The first Cell of the Board (first row, first col) is (1, 1)!
     * @param x is the row of the cell.
     * @param y is the column of the cell.
     * @return returns the value of the Cell [x][y] and Optional.empty() if position is invalid.
     */
    public Optional<Integer> getValue(int x, int y) {
        try {
            return cells.get((x - 1) * size + (y - 1)).getValue();
        }
        catch (Exception e) {
            return Optional.empty();
        }
        
    }

    /**
     * Sets the value of the cell of position [x][y]. 
     * The first Cell of the Board (first row, first col) is (1, 1)!
     * @param x is the row of the cell.
     * @param y is the column of the cell.
     * @param value is the new value of the cell.
     * @return if position of the Cell and value is valid returns true, false otherwise.
     */
    public boolean setValue(int x, int y, int value) {
        if (value > size || value < 0) return false;
        if (x <= 0 || x > size) return false;
        if (y <= 0 || y > size) return false;

        cells.get((x - 1) * size + (y - 1)).setValue(value);
        return true;
    }

    /**
     * Returns itself as BoardSolver
    */
    public BoardSolver asSolver() {
        return new BoardSolver(this);
    }

    /**
     * Converts a given number to its corresponding Operation.
     * @return returns the Operation corresponding to number.
     */
    public static Operations toOperations(int number) {
        switch (number) {
            case 0:
                return Operations.NoOp;
            case 1:
                return Operations.ADDITION;
            case 2:
                return Operations.SUBTRACTION;
            case 3:
                return Operations.PRODUCT;
            case 4:
                return Operations.DIVISION;
            case 5:
                return Operations.POWER;
            default:
                return Operations.MODULUS;
        }
    }


    /**
     * Converts the board into a String in PROP format. The order of the cells is from left to right, up to down. 
     * It's possible the String is not equal to the file that Board read in fromFile, but it's still means
     * the same.
     * @return returns the String with the contents in PROP format.
     */
    public String toPropFormat() {

        /*
         * The main strategy is to create two dimensional ArrayList to save the cells by
         * partitions and its position. This is necessary because we need to print the
         * number of cells of each partition (which we don't know) and the partitions
         * in order, and they can have cells of different rows/columns.
         * Once we have all of them structured in partitions, we only do append into a
         * StringBuilder, which we will .toString() and return.
         */

        //file is a StringBuilder which will do append char by char.
        StringBuilder file = new StringBuilder();

        //first line is N R. Size and number of partitions.
        String firstLine = this.size + " " + this.partitions.size() + " \n";
        file.append(firstLine);

        /*
         * Creates a two dimensional ArrayList that represent the Cells and the position 
         * structured by partitions.
         * positionCells is necessary to save the position [row][col] of Cells, because
         * a partition could have Cells of any row.
         */
        ArrayList<ArrayList<Cell>> cellsOfPartitions = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<String>> positionCells = new ArrayList<ArrayList<String>>();
        //Initialize to the number of partitions of this board.
        for (int i = 0; i < partitions.size(); ++i) {
            cellsOfPartitions.add(new ArrayList<Cell>());
            positionCells.add(new ArrayList<String>());
        }

        //Saves every Cell and its position according to it's corresponding partition.
        for (int i = 0; i < cells.size(); ++i) {
            cellsOfPartitions.get(cells.get(i).getPartition()).add(cells.get(i));
            int row = i/size;
            int col = i - row*size;
            positionCells.get(cells.get(i).getPartition()).add((row + 1) + " " + (col + 1));
        }
        
        /*
         * Reads the partitions (each one is a line in the String) and appends:
         * 1. the corresponding operation
         * 2. the result
         * 3. the number of cells, known thanks to saving the Cells by partitions before
         * 4. all of its cells
         *    4.1. the position saved in positionCells, known due to the same strategy as the #cells
         *    4.2. its content if its value is not empty or 0
         */
        for (int partition = 0; partition < partitions.size(); ++partition) {
            Operations op = partitions.get(partition).getOperations();
            int operation;
            switch(op) {
                case NoOp:
                    operation = 0;
                    break;
                case ADDITION:
                    operation = 1;
                    break;
                case SUBTRACTION:
                    operation = 2;
                    break;
                case PRODUCT:
                    operation = 3;
                    break;
                case DIVISION:
                    operation = 4;
                    break;
                case POWER:
                    operation = 5;
                    break;
                default:
                    operation = 6;
                    break;
            }
            file.append(operation + " ");
            file.append(partitions.get(partition).result + " ");
            file.append(cellsOfPartitions.get(partition).size() + " ");

            for (int i = 0; i < cellsOfPartitions.get(partition).size(); ++i) {
                //Appends the position
                file.append(positionCells.get(partition).get(i));

                //If we are at the end, do not append " "
                if (!((partition == partitions.size() - 1) && (i == cellsOfPartitions.get(partition).size() - 1))) {
                    file.append(" ");
                }

                //If a Cell has value not empty and different to 0, append [value]
                if (!cellsOfPartitions.get(partition).get(i).getValue().isEmpty() && 
                cellsOfPartitions.get(partition).get(i).getValue().get() != 0) {

                    //If we are the end, but there's content inside the cell, DO append the space we didn't before
                    if ((partition == partitions.size() - 1) && (i == cellsOfPartitions.get(partition).size() - 1)) {
                        file.append(" ");
                    }
                    file.append("[");
                    file.append(cellsOfPartitions.get(partition).get(i).getValue().get());
                    file.append("]");
                    //If we are at the end, do not append " "
                    if (!((partition == partitions.size() - 1) && (i == cellsOfPartitions.get(partition).size() - 1))) {
                        file.append(" ");
                    }
                }
            }
            //If we are at the end, do not append "\n"
            if (!(partition == partitions.size() - 1)) file.append("\n");
        }
        return file.toString();
    }

    /**
     * Clones this instance.
     * @return clone of the Completion.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Board (this);
    }

    /**
     * Equals of this class.
     * @return returns true if this instance is equal to obj, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Board other = (Board) obj;
        return 
            this.size == other.size &&
            this.cells.equals(other.cells) &&
            this.partitions.equals(other.partitions);
    }
        
    /**
     * Returns the hashCode of Board.
     * @return hashcode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash (
            this.size,
            this.cells,
            this.partitions
        );
    }

    /**
     * Returns the class in Json.
     * @return string with Json of this instance.
     */
    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
