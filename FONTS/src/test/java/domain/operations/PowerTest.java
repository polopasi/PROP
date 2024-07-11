package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PowerTest {

    @Test
    public void testEmptyArray() {
        Power power = new Power();
        int[] numbers = new int[0];
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 4;
        assertFalse(power.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testSingleElementArray() {
        Power power = new Power();
        int[] numbers = new int[]{1};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 4;
        assertFalse(power.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testInvalidArrayLength() {
        Power power = new Power();
        int[] numbers = new int[]{3, 4, 5};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 4;
        assertFalse(power.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testValidPower() {
        Power power = new Power();
        int[] numbers = new int[]{2, 3};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 8;
        assertTrue(power.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, power.operate(numbers));
    }

    @Test
    public void testOneBase() {
        Power power = new Power();
        int[] numbers = new int[]{1, 10};
        int partitionSize = numbers.length;
        int boardSize = 10;
        int result = 1;
        assertTrue(power.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, power.operate(numbers));
    }

    @Test
    public void testOperatePower() {
        Power power = new Power();
        int[] numbers = new int[]{2,2};
        int partitionSize = numbers.length;
        int boardSize = 3;
        int result = 4;
        assertTrue(power.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, power.operate(numbers));
        numbers[0] = 3;
        numbers[1] = 2;
        partitionSize = numbers.length;
        boardSize = 3;
        result = 9;
        assertTrue(power.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, power.operate(numbers));
    }
}
