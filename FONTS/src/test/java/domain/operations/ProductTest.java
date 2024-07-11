package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductTest {

    @Test
    public void testEmptyArray() {
        Product product = new Product();
        int[] numbers = new int[0];
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 3;
        assertFalse(product.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testSingleElementArray() {
        Product product = new Product();
        int[] numbers = new int[]{1};
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 3;
        assertFalse(product.isCorrect(partitionSize, boardSize, result));
    }

    @Test
    public void testValidProduct() {
        Product product = new Product();
        int[] numbers = new int[]{2, 3, 4};
        int partitionSize = numbers.length;
        int boardSize = 9;
        int result = 24;
        assertTrue(product.isCorrect(partitionSize, boardSize, result));
        assertEquals(result, product.operate(numbers));
    }

    @Test 
    public void testInvalidProduct() {
        Product product = new Product();
        int partitionSize = 2;
        int boardSize = 3;
        int expectedResult = 10;
        assertFalse(product.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 1;
        boardSize = 8;
        expectedResult = 3;
        assertFalse(product.isCorrect(partitionSize, boardSize, expectedResult));
        partitionSize = 3;
        boardSize = 8;
        expectedResult = 0;
        assertFalse(product.isCorrect(partitionSize, boardSize, expectedResult));
    }
}
