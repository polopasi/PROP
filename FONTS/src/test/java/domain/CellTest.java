package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class CellTest {

    @Test
    public void testDefaultConstructor() {
        Cell cell = new Cell();
        assertEquals(Optional.empty(), cell.getValue());
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testConstructorWithValueAndPartition() {
        int value = 10;
        int partition = 2;
        Cell cell = new Cell(value, partition);
        assertEquals(Optional.of(value), cell.getValue());
        assertFalse(cell.isEmpty());
        assertEquals(partition, cell.getPartition());
    }

    @Test
    public void testClone() {
        int value = 2;
        int partition = 3;
        Cell cell = new Cell(value, partition);
        Cell clonedCell = new Cell(cell);

        assertNotEquals(cell, clonedCell);
        assertEquals(cell.getPartition(), clonedCell.getPartition());
        assertEquals(cell.getValue(), clonedCell.getValue());
    }

    @Test
    public void testSetValue() {
        Cell cell = new Cell();
        int newValue = 25;
        cell.setValue(newValue);
        assertEquals(Optional.of(newValue), cell.getValue());
        assertFalse(cell.isEmpty());
    }

    @Test
    public void testClear() {
        Cell cell = new Cell(5, 0);
        cell.clear();
        assertEquals(Optional.empty(), cell.getValue());
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        Cell emptyCell = new Cell();
        assertTrue(emptyCell.isEmpty());

        Cell nonEmptyCell = new Cell(10, 0);
        assertFalse(nonEmptyCell.isEmpty());
    }

    @Test
    public void testGetValueEmptyCell() {
        Cell emptyCell = new Cell();
        assertEquals(Optional.empty(), emptyCell.getValue());
    }

    @Test
    public void testGetValueNonEmptyCell() {
        Cell nonEmptyCell = new Cell(20, 0);
        assertEquals(Optional.of(20), nonEmptyCell.getValue());
    }

    @Test
    public void testGetPartition() {
        int partition = 3;
        Cell cell = new Cell(0, partition);
        assertEquals(partition, cell.getPartition());
    }
}