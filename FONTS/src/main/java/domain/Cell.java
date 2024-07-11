package domain;

import java.util.Optional;


/**
 * This class represents a cell within a Partition. 
 * Each cell holds an integer value and a partition identifier.
 *
 * @author Mikel Torres Martinez
 */
public class Cell {

    // Attributes

    /**
     * The integer value stored within the cell.
     */
    private int value;

    /**
     * The identifier for the partition this cell belongs to.
     */
    private int partition;

    // Constructors

    /**
     * Default constructor that initializes the cell with a value of 0.
     */
    public Cell() {
        this.value = 0;
        this.partition = -1;
    }

    /**
     * Constructor that initializes the cell with a specific value and an specific partition.
     *
     * @param value The initial value for the cell.
     * @param partition The initial partition for the cell.
     */
    public Cell(int value, int partition) {
        this.value = value;
        this.partition = partition;
    }

    public Cell(Cell clone) {
        this.value = clone.value;
        this.partition = clone.partition;
    }

    // Setters

    /**
     * Sets the value stored within the cell.
     * 
     * @param newValue The new value for the cell.
     */
    public void setValue(int newValue) {
        this.value = newValue;
    }

    /**
     * Clears the cell by setting its value to 0.
     */
    public void clear() {
        this.value = 0;
    }

    // Getters

    /**
     * Checks if the cell is empty (value is 0).
     * 
     * @return True if the cell is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.value == 0;
    }

    /**
     * Returns an Optional containing the cell's value.
     * If the cell is empty (value is 0), an empty Optional object is returned.
     * 
     * @return An Optional object containing the cell's value or empty Optional if the cell is empty.
     */
    public Optional<Integer> getValue() {
        if (value == 0) {
            return Optional.empty();
        }
        return Optional.of(this.value);
    }

    /**
     * Retrieves the partition identifier associated with the cell.
     * 
     * @return The partition identifier for this cell.
     */
    public int getPartition() {
        return this.partition;
    }
    
    public void setPartition(int newPartition) {
        this.partition = newPartition;
    }
}