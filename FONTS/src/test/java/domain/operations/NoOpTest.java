package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoOpTest {


    @Test
    public void testEmptyArray() {
        NoOp noOp = new NoOp();
        int partitionSize = 0;
        int boardSize = 3;
        int result = 2;
        assertFalse(noOp.isCorrect(partitionSize, boardSize, result));
    }


    @Test
    public void testSingleElement() {
        NoOp noOp = new NoOp();
        int[] numbers = new int[]{5};
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 5;
        assertTrue(noOp.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, noOp.operate(numbers));
    }

    @Test
    public void testElementNotValid() {
        NoOp noOp = new NoOp();
        int[] numbers = {5};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 5;
        assertFalse(noOp.isCorrect(partitionSize, boardSize, result));

    }


    @Test
    public void testMultipleElements() {
        NoOp noOp = new NoOp();
        int[] numbers = new int[]{3, 4, 5};
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 3;
        assertFalse(noOp.isCorrect(partitionSize, boardSize, result));
    }
}
