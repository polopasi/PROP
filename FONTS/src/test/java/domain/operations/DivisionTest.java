package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DivisionTest {

    @Test
    public void testEmptyPartition() {
        Division division = new Division();
        int partitionSize = 0;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(division.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testSingleElementPartition() {
        Division division = new Division();
        int partitionSize = 1;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(division.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testValidDivision() {
        Division division = new Division();
        int partitionSize = 2;
        int boardSize = 4;
        int expectedResult = 2;
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        int[] numbers = {4,2};
        assertEquals(expectedResult, division.operate(numbers));
        partitionSize = 2;
        boardSize = 9;
        expectedResult = 3;
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        numbers[0] = 9;
        numbers[1] = 3;
        assertEquals(expectedResult, division.operate(numbers));
        partitionSize = 2;
        boardSize = 9;
        expectedResult = 4;
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        numbers[0] = 8;
        numbers[1] = 2;
        assertEquals(expectedResult, division.operate(numbers));
    }

    @Test 
    public void testInvalidDivision() {
        Division division = new Division();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 7;
        assertFalse(division.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 2;
        boardSize = 8;
        expectedResult = 9;
        assertFalse(division.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 3;
        boardSize = 8;
        expectedResult = 9;
        assertFalse(division.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testRemainderDivision() {
        Division division = new Division();
        int partitionSize = 2;
        int boardSize = 10;
        int expectedResult = 3;
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        int[] numbers = {10,3};
        assertEquals(-1, division.operate(numbers));
    }

    @Test 
    public void testOperateDivision() {
        Division division = new Division();
        int partitionSize = 2;
        int boardSize = 9;
        int expectedResult = 4;
        int[] numbers = {4,1};
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        assertEquals(expectedResult, division.operate(numbers));
        numbers[0] = 3;
        numbers[1] = 1;
        expectedResult = 3;
        assertTrue(division.isCorrect(partitionSize, boardSize, expectedResult));
        assertEquals(expectedResult, division.operate(numbers));
    }


}