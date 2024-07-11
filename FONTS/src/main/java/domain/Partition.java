package domain;

import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.operations.Operation;
import domain.operations.Operations;
import helpers.InOut;


/**
 * Represents a partition within a KenKen board.
 * A partition defines a region of cells and specifies an operation to be performed on the numbers within those cells. 
 * This class also holds the expected result of the operation.
 * @author Mikel Torres Martínez
 */
public class Partition {

    //Attributes

    /**
     * Size of the Partition
     */
    int size;

    /**
     * Expected result of the operation
     */
    int result;

    /**
     * Type of operation
     */
    Operations operations;


    //Constructors

    /** Creates an instance of Partition.
     * 
     * @param size Partition's size.
     * @param result Partition's result.
     * @param operations Partition's operation.
     */
    public Partition(int size, int result, Operations operations) {
        this.size = size;
        this.result = result;
        this.operations = operations;
    }
    

    /**
     * Creates a deep copy of a given `Partition` instance.
     *
     * @param clone the `Partition` instance to be copied.
     */
    public Partition(Partition clone) {
        this.size = clone.size;
        this.result = clone.result;
        this.operations = clone.operations;
    }


    //Getters

    /**
     * @return the result attribute of the Partition.
     */
    public int getResult() {
        return Integer.valueOf(this.result);
    }

    /**
     * @return the size of the Partition.
     */
    public int getSize() {
        return Integer.valueOf(this.size);
    }

    /**
     * @return the operations of the Partition.
     */
    public Operations getOperations() {
        return this.operations;
    }

    //Setters

    /**
     * Sets newResult as the Partition's result.
     * @param newResult Is the result of the partition
     */
    public void setResult(int newResult) {
        this.result = newResult;
    }

     /**
     * Sets newSize as the Partition's size.
     * @param newSize Is the size of the partition
     */
    public void setSize(int newSize) {
        this.size = newSize;
    }

    /**
     * Sets newOperations as the Partition's operations.
     * @param newOperations Is the operation of the partition
     */
    public void setOperations(Operations newOperations) {
        this.operations = newOperations;
    }

    //Class methods

    /**
     * Performs an operation on a number array, throws error if size mismatch.
     * 
     * @param numbers The array of numbers to operate on.
     * @throws IllegalArgumentException If array size doesn't match with the Partition size.
     * @return The result of the operation. 
    */
    public int operate(int[] numbers) throws IllegalArgumentException {
        if (numbers.length == this.size) {
            Operation operation = Operation.fromEnum(operations);
            return operation.operate(numbers);
        } else {
            throw new IllegalArgumentException("Solution numbers size does not match partition size.");
        }
    }

    /**
     * Checks if `numbers` represent a solved partition.
     *
     * @param numbers The array of numbers to be checked.
     * @return true if solved, false otherwise.
     */
    public boolean isSolved(int[] numbers) {
        if (numbers.length == 0) {
            return true;
        }
        if (this.operations == Operations.NoOp && this.result == 0) {
            return true;
        }
        if (numbers.length != this.size) {
            return false;
        }
        for (int n : numbers) {
            if (n == 0) { return false; }
        }

        Operation operation = Operation.fromEnum(this.operations);
        int resultPartition = operation.operate(numbers);

        return resultPartition == this.result;
    }

    /**
     * Checks if achieving the result with the given operation and board size is possible.
     *
     * @return True if achieving the result is possible, false otherwise.
     */
    public boolean isCorrect(int boardSize) {
        return this.size == 0 || Operation.fromEnum(operations).isCorrect(this.size, boardSize, this.result);
    }


    // other methods

    /**
     * Clones this instance.
     * @return clone of the Partition.
     */
    @Override
    protected Object clone() {
        return new Partition(this);
    }

    @Override
    public int hashCode() {
        // The hash of all the attributes
        return Objects.hash(
                this.size,
                this.result,
                this.operations
        );
    }

    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}