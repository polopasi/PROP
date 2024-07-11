package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdditionTest {

    @Test
    public void testEmptyPartition() {
        Addition addition = new Addition();
        int partitionSize = 0;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testSingleElementPartition() {
        Addition addition = new Addition();
        int partitionSize = 1;
        int boardSize = 3;
        int expectedResult = 4;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testValidAddition() {
        Addition addition = new Addition();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 4;
        assertTrue(addition.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 4;
        boardSize = 9;
        expectedResult = 6;
        assertTrue(addition.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 2;
        boardSize = 3;
        expectedResult = 6;
        assertTrue(addition.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testInvalidAddition() {
        Addition addition = new Addition();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 7;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 4;
        boardSize = 3;
        expectedResult = 2;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 4;
        boardSize = 8;
        expectedResult = 100;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 4;
        boardSize = 8;
        expectedResult = 3;
        assertFalse(addition.isCorrect(partitionSize, boardSize, expectedResult));
    }

    @Test 
    public void testOperateAddition() {
        Addition addition = new Addition();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 4;
        int[] numbers = {3,1};
        assertTrue(addition.isCorrect(partitionSize, boardSize, expectedResult));
        assertEquals(expectedResult, addition.operate(numbers));
        numbers[0] = 2;
        numbers[1] = 3;
        expectedResult = 5;
        assertEquals(expectedResult, addition.operate(numbers));
    }


}