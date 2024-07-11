package domain.operations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperationsTest {
    @Test
    public void testAdditionValue() {
        assertEquals(Operations.ADDITION, Operations.valueOf("ADDITION"));
    }

    @Test
    public void testSubtractionValue() {
        assertEquals(Operations.SUBTRACTION, Operations.valueOf("SUBTRACTION"));
    }

    @Test
    public void testProductValue() {
        assertEquals(Operations.PRODUCT, Operations.valueOf("PRODUCT"));
    }

    @Test
    public void testDivisionValue() {
        assertEquals(Operations.DIVISION, Operations.valueOf("DIVISION"));
    }

    @Test
    public void testNoOperationValue() {
        assertEquals(Operations.NoOp, Operations.valueOf("NoOp"));
    }

    @Test
    public void testModulusValue() {
        assertEquals(Operations.MODULUS, Operations.valueOf("MODULUS"));
    }

    @Test
    public void testPowerValue() {
        assertEquals(Operations.POWER, Operations.valueOf("POWER"));
    }
}
