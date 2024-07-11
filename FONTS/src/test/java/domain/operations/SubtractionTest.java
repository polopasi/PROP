package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubtractionTest {

    @Test
    public void testEmptyArray() {
        Subtraction subtraction = new Subtraction();
        int[] numbers = new int[0];
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 2;
        assertFalse(subtraction.isCorrect(partitionSize, boardSize, result));
    }


    @Test
    public void testSingleElementArray() {
        Subtraction subtraction = new Subtraction();
        int[] numbers = new int[]{1};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 2;
        assertFalse(subtraction.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testValidSubtraction() {
        Subtraction subtraction = new Subtraction();
        int[] numbers = new int[]{10, 2, 3};
        int partitionSize = numbers.length;
        int boardSize = 10;
        int result = 5;
        assertTrue(subtraction.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, subtraction.operate(numbers));
    }

    @Test
    public void testInvalidSubtraction() {
        Subtraction subtraction = new Subtraction();
        int partitionSize = 2;
        int boardSize = 9;
        int result = 10;
        assertFalse(subtraction.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testNegativeResult() {
        Subtraction subtraction = new Subtraction();
        int[] numbers = new int[]{2, 5};
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = -3;
        assertFalse(subtraction.isCorrect(partitionSize, boardSize, result));
    }

}
