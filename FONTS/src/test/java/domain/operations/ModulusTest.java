package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModulusTest {

    @Test
    public void testEmptyPartition() {
        Modulus modulus = new Modulus();
        int partitionSize = 0;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testSingleElementPartition() {
        Modulus modulus = new Modulus();
        int partitionSize = 1;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testValidModulus() {
        Modulus modulus = new Modulus();
        int partitionSize = 2;
        int boardSize = 4;
        int expectedResult = 2;
        assertTrue(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        int[] numbers = {2,4};
        assertEquals(expectedResult, modulus.operate(numbers));
        partitionSize = 2;
        boardSize = 9;
        expectedResult = 3;
        assertTrue(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        numbers[0] = 9;
        numbers[1] = 6;
        assertEquals(expectedResult, modulus.operate(numbers));
        partitionSize = 2;
        boardSize = 3;
        expectedResult = 0;
        assertTrue(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        numbers[0] = 3;
        numbers[1] = 1;
        assertEquals(expectedResult, modulus.operate(numbers));
    }

    @Test 
    public void testInvalidModulus() {
        Modulus modulus = new Modulus();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 7;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 2;
        boardSize = 8;
        expectedResult = 100;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 3;
        boardSize = 8;
        expectedResult = 2;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 2;
        boardSize = 3;
        expectedResult = 4;
        assertFalse(modulus.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testOperateModulus() {
        Modulus modulus = new Modulus();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 0;
        int[] numbers = {4,1};
        assertTrue(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        assertEquals(expectedResult, modulus.operate(numbers));
        numbers[0] = 1;
        numbers[1] = 3;
        expectedResult = 1;
        assertTrue(modulus.isCorrect(partitionSize, boardSize, expectedResult));
        assertEquals(expectedResult, modulus.operate(numbers));
    }


}