package domain;

import static domain.TestUtils.assertListEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Martínez.
 */
public class BoardSolverTest {
    @Test
    public void isSolved() {
        boolean expected;
        boolean actual;
        String testFile = "";
        
        //Case 0: Empty board.
        expected = true;
        actual = new Board().asSolver().isSolved();
        assertEquals(expected, actual);

        //Case 1: Basic non-solved board.
        testFile = "3 5 \n" +
        "1 3 2 1 1 1 2 \n" +
        "1 4 2 1 3 2 3 \n" +
        "1 5 2 2 1 2 2 \n" +
        "0 3 1 3 1 \n" +
        "1 3 2 3 2 3 3";
        expected = false;
        try {
            actual = Board.fromFile(testFile).asSolver().isSolved();
            assertEquals(expected, actual);
        }
        catch(ParseException e) {Assert.fail(e.toString());}

        //Case 2: Basic board with only 1 cell left to solve.
        testFile = "3 5 \n" +
        "1 3 2 1 1 1 2 [2]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        expected = false;
        try {
            actual = Board.fromFile(testFile).asSolver().isSolved();
            assertEquals(expected, actual);
        }
        catch(ParseException e) {Assert.fail(e.toString());}

        //Case 3: Basic solved board.
        testFile = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        expected = true;
        try {
            actual = Board.fromFile(testFile).asSolver().isSolved();
            assertEquals(expected, actual);
        }
        catch(ParseException e) {Assert.fail(e.toString());}

        //Case 4: Basic board with all of its partitions solved but has repeated values in its cells/columns
        testFile = "3 5 \n" +
        "1 3 2 1 1 [2] 1 2 [1]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        expected = false;
        try {
            actual = Board.fromFile(testFile).asSolver().isSolved();
            assertEquals(expected, actual);
        }
        catch(ParseException e) {Assert.fail(e.toString());}
    }

    @Test
    public void isCorrect() {
        boolean expected;
        boolean actual;
        String testFile = "";
        //Case 0: Empty board.
        expected = true;
        actual = new Board().asSolver().isCorrect();
        assertEquals(expected, actual);

        //Case 1: Board with a three-cell-division.
        testFile = "3 4 \n" +
        "1 5 2 1 1 1 2 [3] \n" +
        "4 2 3 1 3 [2] 2 3 2 2 \n" +
        "2 1 2 2 1 3 1 [1]\n" +
        "2 1 2 3 2 3 3 [0]";
        expected = false;
        try {
            actual = Board.fromFile(testFile).asSolver().isCorrect();
            assertEquals(expected, actual);
        }
        catch(ParseException e) {Assert.fail(e.toString());}
    }

    @Test
    public void solve() {
        //Initial parameters
        String testFile = "";
        String testFileSolved = "";

        //Case 0: Empty board.
        // Board expected = new Board();
        // Board actual = new Board().asSolver().solve().get();
        // assertEquals(expected.toPropFormat(), actual.toPropFormat());

        //Case 1: Basic board.
        testFile = "3 5 \n" +
        "1 3 2 1 1 1 2 \n" +
        "1 4 2 1 3 2 3 \n" +
        "1 5 2 2 1 2 2 \n" +
        "0 3 1 3 1 \n" +
        "1 3 2 3 2 3 3";
        
        testFileSolved = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        try {
            Board expected = Board.fromFile(testFileSolved);
            Board actual = Board.fromFile(testFile).asSolver().solve().get();
            assertEquals(expected.toPropFormat(), actual.toPropFormat());
        }
        catch (ParseException e){Assert.fail(e.toString());}

        //Case 1
        testFile = "3 5 \n" +
        "1 3 2 1 1 1 2 \n" +
        "1 4 2 1 3 2 3 \n" +
        "1 5 2 2 1 2 2 \n" +
        "0 3 1 3 1 \n" +
        "1 3 2 3 2 3 3";
    }
    
    @Test
    public void solve7() {
        String expectedFile = "7 20\n"+
        "1 6 3 1 1 [1] 1 2 [2] 1 3 [3] \n"+
        "0 4 1 1 4 [4] \n"+
        "3 150 3 1 5 [5] 2 4 [5] 2 5 [6] \n"+
        "1 13 2 1 6 [6] 2 6 [7] \n"+
        "6 0 2 1 7 [7] 2 7 [1] \n"+
        "1 16 5 2 1 [2] 2 2 [3] 2 3 [4] 3 1 [3] 3 2 [4] \n"+
        "1 11 2 3 3 [5] 3 4 [6] \n"+
        "2 6 2 3 5 [7] 3 6 [1] \n"+
        "0 2 1 3 7 [2] \n"+
        "1 9 2 4 1 [4] 4 2 [5] \n"+
        "0 6 1 4 3 [6] \n"+
        "2 6 2 4 4 [7] 4 5 [1] \n"+
        "5 8 2 4 6 [2] 4 7 [3] \n"+
        "1 24 4 5 1 [5] 5 2 [6] 6 1 [6] 6 2 [7] \n"+
        "1 9 3 5 5 [2] 5 6 [3] 5 7 [4] \n"+
        "5 7 2 7 1 [7] 7 2 [1] \n"+
        "0 2 1 7 3 [2] \n"+
        "1 23 7 5 3 [7] 5 4 [1] 6 3 [1] 6 4 [2] 6 5 [3] 6 6 [4] 6 7 [5] \n"+
        "5 81 2 7 4 [3] 7 5 [4] \n"+
        "1 11 2 7 6 [5] 7 7 [6]";

        String testFile = "7 20\n"+
        "1 6 3 1 1 1 2 1 3 \n"+
        "0 4 1 1 4 \n"+
        "3 150 3 1 5 2 5 2 4\n"+
        "1 13 2 1 6 2 6\n"+
        "6 0 2 1 7 2 7\n"+
        "1 16 5 2 1 2 2 2 3 3 1 3 2\n"+
        "1 11 2 3 3 3 4\n"+
        "2 6 2 3 5 3 6\n"+
        "0 2 1 3 7\n"+
        "1 9 2 4 1 4 2\n"+
        "0 6 1 4 3\n"+
        "2 6 2 4 4 4 5\n"+
        "5 8 2 4 6 4 7\n"+
        "1 24 4 5 1 5 2 6 1 6 2\n"+
        "1 9 3 5 5 5 6 5 7\n"+
        "5 7 2 7 1 7 2\n"+
        "0 2 1 7 3\n"+
        "1 23 7 5 3 5 4 6 3 6 4 6 5 6 6 6 7\n"+
        "5 81 2 7 4 7 5\n"+
        "1 11 2 7 6 7 7\n";
        try {
            Board expected = Board.fromFile(expectedFile);
            Board actual = Board.fromFile(testFile).asSolver().solve().get();
            assertEquals(expected.toPropFormat(), actual.toPropFormat());
        }
        catch (ParseException e){Assert.fail(e.toString());}
    }

    @Test
    public void solve8() {
        String expectedFile = "8 22 \n" + 
        "1 15 6 1 1 [1] 1 2 [2] 1 3 [3] 2 1 [2] 2 2 [3] 2 3 [4] \n" +
        "3 120 3 1 4 [4] 2 4 [5] 2 5 [6] \n" +
        "1 11 2 1 5 [5] 1 6 [6] \n" +
        "1 15 2 1 7 [7] 1 8 [8] \n" +
        "1 16 3 2 6 [7] 2 7 [8] 2 8 [1] \n" +
        "1 11 3 3 1 [3] 3 2 [4] 4 1 [4] \n" +
        "1 24 4 3 3 [5] 3 4 [6] 4 3 [6] 4 4 [7] \n" +
        "1 23 6 3 5 [7] 3 6 [8] 3 7 [1] 3 8 [2] 4 7 [2] 4 8 [3] \n" +
        "2 7 2 4 5 [8] 4 6 [1] \n" +
        "0 5 1 4 2 [5] \n" +
        "0 5 1 5 1 [5] \n" +
        "1 27 4 5 2 [6] 6 1 [6] 6 2 [7] 6 3 [8] \n" +
        "0 7 1 5 3 [7] \n" +
        "2 7 2 7 2 [8] 7 3 [1] \n" +
        "3 96 6 5 4 [8] 5 5 [1] 5 6 [2] 6 4 [1] 6 5 [2] 6 6 [3] \n" +
        "1 16 4 5 7 [3] 5 8 [4] 6 7 [4] 6 8 [5] \n" +
        "1 15 2 7 1 [7] 8 1 [8] \n" +
        "1 3 2 8 2 [1] 8 3 [2] \n" +
        "1 5 2 7 4 [2] 7 5 [3] \n" +
        "3 60 3 8 4 [3] 8 5 [4] 8 6 [5] \n" +
        "0 4 1 7 6 [4] \n" +
        "1 24 4 7 7 [5] 7 8 [6] 8 7 [6] 8 8 [7]";

        String testFile = "8 22 \n" + 
        "1 15 6 1 1 1 2 1 3 2 1 2 2 2 3 \n" +
        "3 120 3 1 4 2 4 2 5 \n" +
        "1 11 2 1 5 1 6 \n" +
        "1 15 2 1 7 1 8 \n" +
        "1 16 3 2 6 2 7 2 8 \n" +
        "1 11 3 3 1 3 2 4 1 \n" +
        "1 24 4 3 3 3 4 4 3 4 4 \n" +
        "1 23 6 3 5 3 6 3 7 3 8 4 7 4 8 \n" +
        "2 7 2 4 5 4 6 \n" +
        "0 5 1 4 2 \n" +
        "0 5 1 5 1 \n" +
        "1 27 4 5 2 6 1 6 2 6 3 \n" +
        "0 7 1 5 3 \n" +
        "2 7 2 7 2 7 3 \n" +
        "3 96 6 5 4 5 5 5 6 6 4 6 5 6 6 \n" +
        "1 16 4 5 7 5 8 6 7 6 8 \n" +
        "1 15 2 7 1 8 1 \n" +
        "1 3 2 8 2 8 3 \n" +
        "1 5 2 7 4 7 5 \n" +
        "3 60 3 8 4 8 5 8 6 \n" +
        "0 4 1 7 6 \n" +
        "1 24 4 7 7 7 8 8 7 8 8";
        try {
            Board expected = Board.fromFile(expectedFile);
            Board actual = Board.fromFile(testFile).asSolver().solve().get();
            assertEquals(expected.toPropFormat(), actual.toPropFormat());
        }
        catch (ParseException e){Assert.fail(e.toString());}
    }

    @Test
    public void getValidated() {
        //Case 0: Empty board.
        var expected = new boolean[]{};
        var actual = new Board().asSolver().getValidated();
        assertArrayEquals(expected, actual);

        //Case 1: Basic valid board:
        String testFile = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidated();
            expected = new boolean[]{true, true, true,
                                     true, true, true,
                                     true, true, true};
            assertArrayEquals(actual, expected);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 2: Basic invalid board:
        testFile = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [1] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [2]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidated();
            expected = new boolean[]{false, false, false,
                                     false, false, false,
                                     true, true, true};
            assertArrayEquals(actual, expected);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 2: Big valid board:
        testFile = "8 22 \n" + 
        "1 15 6 1 1 [1] 1 2 [2] 1 3 [3] 2 1 [2] 2 2 [3] 2 3 [4] \n" +
        "3 120 3 1 4 [4] 2 4 [5] 2 5 [6] \n" +
        "1 11 2 1 5 [5] 1 6 [6] \n" +
        "1 15 2 1 7 [7] 1 8 [8] \n" +
        "1 16 3 2 6 [7] 2 7 [8] 2 8 [1] \n" +
        "1 11 3 3 1 [3] 3 2 [4] 4 1 [4] \n" +
        "1 24 4 3 3 [5] 3 4 [6] 4 3 [6] 4 4 [7] \n" +
        "1 23 6 3 5 [7] 3 6 [8] 3 7 [1] 3 8 [2] 4 7 [2] 4 8 [3] \n" +
        "2 7 2 4 5 [8] 4 6 [1] \n" +
        "0 5 1 4 2 [5] \n" +
        "0 5 1 5 1 [5] \n" +
        "1 27 4 5 2 [6] 6 1 [6] 6 2 [7] 6 3 [8] \n" +
        "0 7 1 5 3 [7] \n" +
        "2 7 2 7 2 [8] 7 3 [1] \n" +
        "3 96 6 5 4 [8] 5 5 [1] 5 6 [2] 6 4 [1] 6 5 [2] 6 6 [3] \n" +
        "1 16 4 5 7 [3] 5 8 [4] 6 7 [4] 6 8 [5] \n" +
        "1 15 2 7 1 [7] 8 1 [8] \n" +
        "1 3 2 8 2 [1] 8 3 [2] \n" +
        "1 5 2 7 4 [2] 7 5 [3] \n" +
        "3 60 3 8 4 [3] 8 5 [4] 8 6 [5] \n" +
        "0 4 1 7 6 [4] \n" +
        "1 24 4 7 7 [5] 7 8 [6] 8 7 [6] 8 8 [7]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidated();
            expected = new boolean[64];
            Arrays.fill(expected, true);
            assertArrayEquals(expected, actual);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 3: Big invalid board.
        testFile = "8 22 \n" + 
        "1 15 6 1 1 [1] 1 2 [2] 1 3 [3] 2 1 [2] 2 2 [3] 2 3 [4] \n" +
        "3 120 3 1 4 [4] 2 4 [5] 2 5 [6] \n" +
        "1 11 2 1 5 [5] 1 6 [6] \n" +
        "1 15 2 1 7 [7] 1 8 [8] \n" +
        "1 16 3 2 6 [7] 2 7 [1] 2 8 [8] \n" +
        "1 11 3 3 1 [3] 3 2 [4] 4 1 [4] \n" +
        "1 24 4 3 3 [5] 3 4 [6] 4 3 [6] 4 4 [7] \n" +
        "1 23 6 3 5 [7] 3 6 [8] 3 7 [1] 3 8 [2] 4 7 [2] 4 8 [3] \n" +
        "2 7 2 4 5 [8] 4 6 [1] \n" +
        "0 5 1 4 2 [5] \n" +
        "0 5 1 5 1 [5] \n" +
        "1 27 4 5 2 [6] 6 1 [6] 6 2 [7] 6 3 [8] \n" +
        "0 7 1 5 3 [7] \n" +
        "2 7 2 7 2 [8] 7 3 [1] \n" +
        "3 96 6 5 4 [8] 5 5 [1] 5 6 [2] 6 4 [1] 6 5 [2] 6 6 [3] \n" +
        "1 16 4 5 7 [3] 5 8 [4] 6 7 [4] 6 8 [5] \n" +
        "1 15 2 7 1 [7] 8 1 [8] \n" +
        "1 3 2 8 2 [1] 8 3 [2] \n" +
        "1 5 2 7 4 [2] 7 5 [3] \n" +
        "3 60 3 8 4 [3] 8 5 [4] 8 6 [5] \n" +
        "0 4 1 7 6 [4] \n" +
        "1 24 4 7 7 [5] 7 8 [6] 8 7 [6] 8 8 [7]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidated();
            expected = new boolean[] {
                true, true, true, true, true, true, true, false,
                true, true, true, true, true, true, false, false,
                true, true, true, true, true, true, false, true,
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true
            };
            assertArrayEquals(expected, actual);
        }
        catch (ParseException e) {Assert.fail(e.toString());}
    }

    @Test
    public void getValidatedPartitions() {
        //Case 0: Empty board.
        var expected = new boolean[]{};
        var actual = new Board().asSolver().getValidated();
        assertArrayEquals(expected, actual);

        //Case 1: Basic valid board:
        String testFile = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [3] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [3]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidatedPartitions();
            expected = new boolean[]{
                true, true, true, true, true
            };
            assertArrayEquals(actual, expected);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 2: Basic invalid board:
        testFile = "3 5 \n" +
        "1 3 2 1 1 [1] 1 2 [2]\n" +
        "1 4 2 1 3 [1] 2 3 [1]\n" +
        "1 5 2 2 1 [2] 2 2 [2]\n" +
        "0 3 1 3 1 [3]\n" +
        "1 3 2 3 2 [1] 3 3 [2]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidatedPartitions();
            expected = new boolean[]{
                false, false, false, true, true
            };
            assertArrayEquals(actual, expected);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 2: Big valid board:
        testFile = "8 22 \n" + 
        "1 15 6 1 1 [1] 1 2 [2] 1 3 [3] 2 1 [2] 2 2 [3] 2 3 [4] \n" +
        "3 120 3 1 4 [4] 2 4 [5] 2 5 [6] \n" +
        "1 11 2 1 5 [5] 1 6 [6] \n" +
        "1 15 2 1 7 [7] 1 8 [8] \n" +
        "1 16 3 2 6 [7] 2 7 [8] 2 8 [1] \n" +
        "1 11 3 3 1 [3] 3 2 [4] 4 1 [4] \n" +
        "1 24 4 3 3 [5] 3 4 [6] 4 3 [6] 4 4 [7] \n" +
        "1 23 6 3 5 [7] 3 6 [8] 3 7 [1] 3 8 [2] 4 7 [2] 4 8 [3] \n" +
        "2 7 2 4 5 [8] 4 6 [1] \n" +
        "0 5 1 4 2 [5] \n" +
        "0 5 1 5 1 [5] \n" +
        "1 27 4 5 2 [6] 6 1 [6] 6 2 [7] 6 3 [8] \n" +
        "0 7 1 5 3 [7] \n" +
        "2 7 2 7 2 [8] 7 3 [1] \n" +
        "3 96 6 5 4 [8] 5 5 [1] 5 6 [2] 6 4 [1] 6 5 [2] 6 6 [3] \n" +
        "1 16 4 5 7 [3] 5 8 [4] 6 7 [4] 6 8 [5] \n" +
        "1 15 2 7 1 [7] 8 1 [8] \n" +
        "1 3 2 8 2 [1] 8 3 [2] \n" +
        "1 5 2 7 4 [2] 7 5 [3] \n" +
        "3 60 3 8 4 [3] 8 5 [4] 8 6 [5] \n" +
        "0 4 1 7 6 [4] \n" +
        "1 24 4 7 7 [5] 7 8 [6] 8 7 [6] 8 8 [7]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidated();
            expected = new boolean[64];
            Arrays.fill(expected, true);
            assertArrayEquals(expected, actual);
        }
        catch (ParseException e) {Assert.fail(e.toString());}

        //Case 3: Big invalid board.
        testFile = "8 22 \n" + 
        "1 15 6 1 1 [1] 1 2 [2] 1 3 [3] 2 1 [2] 2 2 [3] 2 3 [4] \n" +
        "3 120 3 1 4 [4] 2 4 [5] 2 5 [6] \n" +
        "1 11 2 1 5 [5] 1 6 [6] \n" +
        "1 15 2 1 7 [7] 1 8 [8] \n" +
        "1 16 3 2 6 [7] 2 7 [1] 2 8 [8] \n" +
        "1 11 3 3 1 [3] 3 2 [4] 4 1 [4] \n" +
        "1 24 4 3 3 [5] 3 4 [6] 4 3 [6] 4 4 [7] \n" +
        "1 23 6 3 5 [7] 3 6 [8] 3 7 [1] 3 8 [2] 4 7 [2] 4 8 [3] \n" +
        "2 7 2 4 5 [8] 4 6 [1] \n" +
        "0 5 1 4 2 [5] \n" +
        "0 5 1 5 1 [5] \n" +
        "1 27 4 5 2 [6] 6 1 [6] 6 2 [7] 6 3 [8] \n" +
        "0 7 1 5 3 [7] \n" +
        "2 7 2 7 2 [8] 7 3 [1] \n" +
        "3 96 6 5 4 [8] 5 5 [1] 5 6 [2] 6 4 [1] 6 5 [2] 6 6 [3] \n" +
        "1 16 4 5 7 [3] 5 8 [4] 6 7 [4] 6 8 [5] \n" +
        "1 15 2 7 1 [7] 8 1 [8] \n" +
        "1 3 2 8 2 [1] 8 3 [2] \n" +
        "1 5 2 7 4 [2] 7 5 [3] \n" +
        "3 60 3 8 4 [3] 8 5 [4] 8 6 [5] \n" +
        "0 4 1 7 6 [4] \n" +
        "1 24 4 7 7 [5] 7 8 [6] 8 7 [6] 8 8 [7]";
        try {
            actual = Board.fromFile(testFile).asSolver().getValidatedPartitions();
            expected = new boolean[] {
                true, true, true, false, false, true, true, false,
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true
            };
            assertArrayEquals(expected, actual);
        }
        catch (ParseException e) {Assert.fail(e.toString());}
    }
}
