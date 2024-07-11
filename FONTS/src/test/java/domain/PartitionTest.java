package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.operations.Operations;

public class PartitionTest {

    @Test
    public void testDefaultConstructor() {
        int size = 1;
        int result = 8;
        Operations operations = Operations.NoOp;
        Partition partition = new Partition(size, result, operations);
        assertEquals(size, partition.getSize());
        assertEquals(result, partition.getResult());
        assertEquals(operations, partition.getOperations());
    }

    @Test
    public void testClone() {
        int size = 2;
        int result = 3;
        Operations operations = Operations.ADDITION;
        Partition partition = new Partition(size, result, operations);
        Partition clonePartition = (Partition) partition.clone();
        assertNotSame(partition, clonePartition);
        assertEquals(partition.getOperations(), clonePartition.getOperations());
        assertEquals(partition.getResult(),clonePartition.getResult());
        assertEquals(partition.getSize(), clonePartition.getSize());
    }

    @Test
    public void testGetResult() {
        Partition partition = new Partition(2,2,Operations.ADDITION);
        assertEquals(2, partition.getResult());
    }

    @Test
    public void testGetSize() {
        Partition partition = new Partition(2,2,Operations.ADDITION);
        assertEquals(2, partition.getSize());
    }

    @Test
    public void testGetOperations() {
        Partition partition = new Partition(2,2,Operations.ADDITION);
        assertEquals(Operations.ADDITION, partition.getOperations());
    }


    @Test
    public void testSetResult() {
        Partition partition = new Partition(1,1,Operations.NoOp);
        int newResult = 2;
        partition.setResult(newResult);
        assertEquals(newResult, partition.getResult());
    }

    @Test
    public void testSetSize() {
        Partition partition = new Partition(1,1,Operations.NoOp);
        int newSize = 2;
        partition.setSize(newSize);
        assertEquals(newSize, partition.getSize());
    }

    @Test
    public void testSetOperations() {
        Partition partition = new Partition(1,3,Operations.NoOp);
        Operations newOperations = Operations.ADDITION;
        partition.setOperations(newOperations);
        assertEquals(newOperations, partition.getOperations());
    }

    @Test
    public void testOperateValid() {
        Partition partition = new Partition(2,3,Operations.ADDITION);
        int[] numbers = {1,2};
        assertEquals(3,partition.operate(numbers));
    }

    // Array
    @Test
    public void testOperateInvalid() {
        Partition partition = new Partition(3, 3, Operations.ADDITION);
        int[] numbers = {1,2};
        assertThrows(IllegalArgumentException.class, () -> partition.operate(numbers));
    }
    

    @Test
    public void testIsSolved() {
        Partition partition = new Partition(1,1,Operations.NoOp);
        int[] numbers = {1};
        assertTrue(partition.isSolved(numbers));
    }

    @Test
    public void testIsNotSolved() {
        Partition partition = new Partition(2,3,Operations.ADDITION);
        int[] numbers = {1};
        assertFalse(partition.isSolved(numbers));
    }
}

