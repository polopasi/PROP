package domain;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import domain.operations.Operations;

public class BoardGeneratorTest {
    
    @Test
    public void generate() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < 8*8; ++i) {
            cells.add(new Cell(0 , -1));
        }
        Board testBoard = new Board(8, cells, new ArrayList<>());
        Board generateBoard = new BoardGenerator(testBoard)
                                  .generate()
                                  .orElse(new Board());
        assertNotEquals(new Board(), generateBoard);
    }

    @Test
    public void generateWithLimits2() {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < 2*2; ++i) {
            cells.add(new Cell(0 , -1));
        }
        ArrayList<Operations> ops = new ArrayList<Operations>();
        ops.add(Operations.ADDITION);
        ops.add(Operations.SUBTRACTION);
        ops.add(Operations.POWER);
        ops.add(Operations.PRODUCT);
        ops.add(Operations.DIVISION);
        ops.add(Operations.MODULUS);
        Board testBoard = new Board(2, cells, new ArrayList<>());
        Board generateBoard = new BoardGenerator(testBoard)
                                  .generateWithLimits(1, 4, 25, ops)
                                  .orElse(new Board());
        assertNotEquals(new Board(), generateBoard);
    }
}
