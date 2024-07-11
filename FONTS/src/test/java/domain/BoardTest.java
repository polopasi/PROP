package domain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.text.ParseException;
import java.util.Optional;

import org.junit.Test;

public class BoardTest {

    @Test
    public void getValue() {
        Board b = new Board();
        String file = "3 5 \n" +
        "1 5 2 1 1 1 2 [3] \n" +
        "4 3 2 1 3 [2] 2 3 \n" +
        "2 1 2 2 1 3 1 [1]\n" +
        "0 1 1 2 2 \n" +
        "2 1 2 3 2 3 3 [0]";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        assertEquals(Optional.of(3), b.getValue(1, 2));
        assertEquals(Optional.of(2), b.getValue(1, 3));
        assertEquals(Optional.empty(), b.getValue(2, 2));
        assertEquals(Optional.empty(), b.getValue(3, 3));
        assertEquals(Optional.of(1), b.getValue(3, 1));
        assertEquals(Optional.empty(), b.getValue(0, 1));
        assertEquals(Optional.empty(), b.getValue(-666, 4));
    }

    @Test
    public void setValue() {
        Board b = new Board();
        String file = "3 5 \n" +
        "1 5 2 1 1 1 2 \n" +
        "4 3 2 1 3 2 3 \n" +
        "2 1 2 2 1 3 1 \n" +
        "0 1 1 2 2 \n" +
        "2 1 2 3 2 3 3";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        assertEquals(Optional.empty(), b.getValue(2, 3));

        b.setValue(2, 3, 3);
        assertEquals(Optional.of(3), b.getValue(2, 3));

        b.setValue(2, 3, 0);
        assertEquals(Optional.empty(), b.getValue(2, 3));

        b.setValue(2, 3, 1);
        b.setValue(2, 3, 2);
        b.setValue(1, 1, 3);
        b.setValue(3, 3, 0);
        b.setValue(2, 2, b.getValue(1, 1).get());
        assertEquals(Optional.of(2), b.getValue(2, 3));
        assertEquals(Optional.of(3), b.getValue(1, 1));
        assertEquals(Optional.empty(), b.getValue(3, 3));
        assertEquals(Optional.of(3), b.getValue(2, 2));
        assertFalse(b.setValue(4, 4, 5));

        //If value is invalid (bigger than size or smaller than 0), do not set
        b.setValue(1, 1, 69);
        assertEquals(Optional.of(3), b.getValue(1, 1));
        b.setValue(1, 1, -2);
        assertEquals(Optional.of(3), b.getValue(1, 1));

    }

    @Test
    public void toPropFormat() {
        Board b = new Board();
        String file = "3 5 \n" +
        "1 5 2 1 1 1 2 \n" +
        "4 3 2 1 3 2 3 \n" +
        "2 1 2 2 1 3 1 \n" +
        "0 1 1 2 2 \n" +
        "2 1 2 3 2 3 3";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        String expected = "3 5 \n" +
        "1 5 2 1 1 1 2 \n" +
        "4 3 2 1 3 2 3 \n" +
        "2 1 2 2 1 3 1 \n" + 
        "0 1 1 2 2 \n" +
        "2 1 2 3 2 3 3";
        assertEquals(expected, b.toPropFormat());

        b = new Board();
        file = "3 5 \n" +
        "1 5 2 1 2 [2] 1 1 [3]\n" +
        "4 3 2 1 3 [2] 2 3 [1]\n" +
        "2 1 2 2 1 [1] 3 1 [1]\n" +
        "0 1 1 2 2 [2]\n" +
        "2 1 2 3 2 [2] 3 3 [1]";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        expected = "3 5 \n" +
        "1 5 2 1 1 [3] 1 2 [2] \n" +
        "4 3 2 1 3 [2] 2 3 [1] \n" +
        "2 1 2 2 1 [1] 3 1 [1] \n" +
        "0 1 1 2 2 [2] \n" +
        "2 1 2 3 2 [2] 3 3 [1]";
        assertEquals(expected, b.toPropFormat());

        //Without " " at the end of the line.
        file = "3 5\n" +
        "3 12 3 1 1 1 2 2 2\n" +
        "2 2 2 1 3 2 3\n" +
        "0 1 1 2 1\n" +
        "4 3 2 3 1 3 2\n" +
        "0 2 1 3 3\n";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        expected = "3 5 \n" +
        "3 12 3 1 1 1 2 2 2 \n" +
        "2 2 2 1 3 2 3 \n" +
        "0 1 1 2 1 \n" +
        "4 3 2 3 1 3 2 \n" +
        "0 2 1 3 3";
        assertEquals(expected, b.toPropFormat());

        file = "4 9 \n" +
        "0 4 1 1 1 \n" +
        "1 3 2 1 2 1 3 \n" +
        "3 6 2 1 4 2 4 \n" +
        "4 2 2 2 1 3 1 \n" +
        "1 7 2 2 2 2 3 \n" +
        "1 4 2 3 2 3 3 \n" +
        "2 3 2 3 4 4 4 \n" +
        "2 1 2 4 1 4 2 \n" +
        "0 4 1 4 3\n";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        expected = "4 9 \n" +
        "0 4 1 1 1 \n" +
        "1 3 2 1 2 1 3 \n" +
        "3 6 2 1 4 2 4 \n" +
        "4 2 2 2 1 3 1 \n" +
        "1 7 2 2 2 2 3 \n" +
        "1 4 2 3 2 3 3 \n" +
        "2 3 2 3 4 4 4 \n" +
        "2 1 2 4 1 4 2 \n" +
        "0 4 1 4 3";
        assertEquals(expected, b.toPropFormat());

        //Very big kenken
        file = "7 23 \n" +
        "3 24 2 1 1 2 1 \n" +
        "1 6 2 1 2 1 3 \n" +
        "3 56 3 1 4 2 4 3 4 \n" +
        "2 1 2 1 5 2 5 \n" +
        "4 2 2 1 6 1 7 \n" +
        "1 16 3 2 2 3 1 3 2 \n" +
        "4 2 2 2 3 3 3 \n" +
        "1 12 2 2 6 2 7 \n" +
        "3 120 3 3 5 4 5 4 4 \n" +
        "4 3 2 3 6 3 7 \n" +
        "1 3 2 4 1 5 1 \n" +
        "1 5 2 4 2 4 3 \n" +
        "2 6 2 4 6 5 6 \n" +
        "3 30 2 4 7 5 7 \n" +
        "3 72 3 5 2 5 3 5 4 \n" +
        "2 6 2 5 5 6 5 \n" +
        "2 4 2 6 1 7 1 \n" +
        "2 1 2 6 2 7 2 \n" +
        "4 2 2 6 3 6 4 \n" +
        "2 6 2 7 3 7 4 \n" +
        "3 10 2 7 5 7 6 \n" +
        "0 4 1 7 7 \n" +
        "4 2 2 6 6 6 7 ";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}
        expected = "7 23 \n" +
        "3 24 2 1 1 2 1 \n" +
        "1 6 2 1 2 1 3 \n" +
        "3 56 3 1 4 2 4 3 4 \n" +
        "2 1 2 1 5 2 5 \n" +
        "4 2 2 1 6 1 7 \n" +
        "1 16 3 2 2 3 1 3 2 \n" +
        "4 2 2 2 3 3 3 \n" +
        "1 12 2 2 6 2 7 \n" +
        "3 120 3 3 5 4 4 4 5 \n" +
        "4 3 2 3 6 3 7 \n" +
        "1 3 2 4 1 5 1 \n" +
        "1 5 2 4 2 4 3 \n" +
        "2 6 2 4 6 5 6 \n" +
        "3 30 2 4 7 5 7 \n" +
        "3 72 3 5 2 5 3 5 4 \n" +
        "2 6 2 5 5 6 5 \n" +
        "2 4 2 6 1 7 1 \n" +
        "2 1 2 6 2 7 2 \n" +
        "4 2 2 6 3 6 4 \n" +
        "2 6 2 7 3 7 4 \n" +
        "3 10 2 7 5 7 6 \n" +
        "0 4 1 7 7 \n" +
        "4 2 2 6 6 6 7";
        assertEquals(expected, b.toPropFormat());
    }

    @Test
    public void multipleToPropFormat() {
        String file = "3 5\n" +
        "3 12 3 1 1 1 2 2 2 [3]\n" +
        "2 2 2 1 3 [1] 2 3\n" +
        "0 1 1 2 1\n" +
        "4 3 2 3 1 3 2\n" +
        "0 2 1 3 3\n";
        Board b = new Board();
        String output = "0";
        try {
            b = Board.fromFile(file);
            output = Board.fromFile(b.toPropFormat()).toPropFormat();
        }
        catch (ParseException e) {}
        String expected = "3 5 \n" +
        "3 12 3 1 1 1 2 2 2 [3] \n" +
        "2 2 2 1 3 [1] 2 3 \n" +
        "0 1 1 2 1 \n" +
        "4 3 2 3 1 3 2 \n" +
        "0 2 1 3 3";
        assertEquals(expected, output);
    }

    @Test
    public void toStringTest() {
        Board b = new Board();
        String file = "2 2 \n" +
        "1 3 2 1 1 [1] 1 2 [2] \n" +
        "1 3 2 2 1 [2] 2 2 [1]";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {

        }

        String expected = "{\n" +
            "  \"size\": 2,\n" +
            "  \"cells\": " +"["+"\n" +
            "    {\n" +
            "      \"value\": 1,\n" + 
            "      \"partition\": 0\n" +
            "    },\n"+
            "    {\n" +
            "      \"value\": 2,\n" +
            "      \"partition\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 2,\n" +
            "      \"partition\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 1,\n" + 
            "      \"partition\": 1\n" +
            "    }\n" +
            "  ],\n" +
            "  \"partitions\": [\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
        assertEquals(expected, b.toString());

        file = "2 1 \n" +
               "1 6 4 2 1 [1] 1 2 [1] 1 1 [2] 2 2 [2]"; 
        try {
            b = Board.fromFile(file);
        } catch (ParseException e) {}
        expected = "{\n" +
            "  \"size\": 2,\n" +
            "  \"cells\": " +"["+"\n" +
            "    {\n" +
            "      \"value\": 2,\n" + 
            "      \"partition\": 0\n" +
            "    },\n"+
            "    {\n" +
            "      \"value\": 1,\n" +
            "      \"partition\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 1,\n" +
            "      \"partition\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 2,\n" + 
            "      \"partition\": 0\n" +
            "    }\n" +
            "  ],\n" +
            "  \"partitions\": [\n" +
            "    {\n" +
            "      \"size\": 4,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";       
        assertEquals(expected, b.toString());

        file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
        try {
            b = Board.fromFile(file);
        } catch (ParseException e) {}
        expected = "{\n" +
            "  \"size\": 6,\n" +
            "  \"cells\": " +"["+"\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 0\n" +
            "    },\n"+
            "    {\n" +
            "      \"value\": 0,\n" +
            "      \"partition\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" +
            "      \"partition\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 2\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 4\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 4\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 2\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 8\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 9\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 11\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 11\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 8\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 9\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 9\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 12\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 13\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 13\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 13\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 14\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 14\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 12\n" +
            "    }\n" +
            "  ],\n" +
            "  \"partitions\": [\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 11,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 2,\n" +
            "      \"operations\": \"DIVISION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 20,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 4,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"SUBTRACTION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"DIVISION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 4,\n" +
            "      \"result\": 240,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 3,\n" +
            "      \"result\": 7,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 30,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 9,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 3,\n" +
            "      \"result\": 8,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 2,\n" +
            "      \"operations\": \"DIVISION\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";       
        assertEquals(expected, b.toString());
    }

    @Test
    public void toStringTest2() {
        Board b = new Board();
        String file = "4 9 \n" +
        "0 4 1 1 1 \n" +
        "1 3 2 1 2 1 3 \n" +
        "3 6 2 1 4 2 4 \n" +
        "4 2 2 2 1 3 1 \n" +
        "1 7 2 2 2 2 3 \n" +
        "1 4 2 3 2 3 3 \n" +
        "2 3 2 3 4 4 4 \n" +
        "2 1 2 4 1 4 2 \n" +
        "0 4 1 4 3";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}

        String expected = "{\n" +
            "  \"size\": 4,\n" +
            "  \"cells\": " +"["+"\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 0\n" +
            "    },\n"+
            "    {\n" +
            "      \"value\": 0,\n" +
            "      \"partition\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" +
            "      \"partition\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 2\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 4\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 4\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 2\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 3\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 8\n" +
            "    },\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 6\n" +
            "    }\n" +
            "  ],\n" +
            "  \"partitions\": [\n" +
            "    {\n" +
            "      \"size\": 1,\n" +
            "      \"result\": 4,\n" +
            "      \"operations\": \"NoOp\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 6,\n" +
            "      \"operations\": \"PRODUCT\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 2,\n" +
            "      \"operations\": \"DIVISION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 7,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 4,\n" +
            "      \"operations\": \"ADDITION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 3,\n" +
            "      \"operations\": \"SUBTRACTION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 2,\n" +
            "      \"result\": 1,\n" +
            "      \"operations\": \"SUBTRACTION\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"size\": 1,\n" +
            "      \"result\": 4,\n" +
            "      \"operations\": \"NoOp\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
        assertEquals(expected, b.toString());
    }

    @Test
    public void smallestBoard() {
        Board b = new Board();
        String file = "1 1 \n" +
        "0 1 1 1 1";
        try {
            b = Board.fromFile(file);
        }
        catch (ParseException e) {}

        String expected = "{\n" +
            "  \"size\": 1,\n" +
            "  \"cells\": " +"["+"\n" +
            "    {\n" +
            "      \"value\": 0,\n" + 
            "      \"partition\": 0\n" +
            "    }\n" +
            "  ],\n" +
            "  \"partitions\": [\n" +
            "    {\n" +
            "      \"size\": 1,\n" +
            "      \"result\": 1,\n" +
            "      \"operations\": \"NoOp\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
        assertEquals(expected, b.toString());
    }

    
    @Test
    public void testExceptions() {
        //Empty file
        assertThrows(ParseException.class, () -> {
            String file = "";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //0
        assertThrows(ParseException.class, () -> {
            String file = "0";
            Board b = Board.fromFile(file);
        });

        //0 0
        assertThrows(ParseException.class, () -> {
            String file = "0 0";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Size and #partitions defined, but nothing after
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //#partitions is 0 and nothing is given after
        assertThrows(ParseException.class, () -> {
            String file = "6 0 \n";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //input patata
        assertThrows(ParseException.class, () -> {
            String file = "patata\n";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Input bigger than size of Board.
        assertThrows(ParseException.class, () -> {
            String file = "2 1 \n" +
            "1 6 4 1 1 [1] 1 2 [2] 2 1 [2] 2 2 [1] 2 1"; //5 cells are given instead of 4
            Board b = new Board();
            b = Board.fromFile(file);
        });


        //Value of one Cell is invalid (1)
        assertThrows(ParseException.class, () -> {
            String file = "2 1 \n" +
            "1 6 4 1 1 [-1] 1 2 [2] 2 1 [2] 2 2 [1]"; //-1 is not a valid value
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Value of one Cell is invalid (2)
        assertThrows(ParseException.class, () -> {
            String file = "2 1 \n" +
            "1 6 4 1 1 [3] 1 2 [2] 2 1 [2] 2 2 [1]"; //3 is not a valid value
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Incorrect operation (1) (for example 7)
        assertThrows(ParseException.class, () -> {
            String file = "3 5 \n" +
            "7 5 2 1 1 1 2 \n" +
            "4 3 2 1 3 2 3 \n" +
            "2 1 2 2 1 3 1 \n" +
            "0 1 1 2 2 \n" +
            "2 1 2 3 2 3 3";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Incorrect operation (2) (for example -1)
        assertThrows(ParseException.class, () -> {
            String file = "3 5 \n" +
            "-1 5 2 1 1 1 2 \n" +
            "4 3 2 1 3 2 3 \n" +
            "2 1 2 2 1 3 1 \n" +
            "0 1 1 2 2 \n" +
            "2 1 2 3 2 3 3";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Incorrect format, #cells in a partition is bigger than N
        assertThrows(ParseException.class, () -> {
            String file = "3 5 \n" +
            "1 5 4 1 1 1 2 \n" +    //#cells in partition is 4
            "4 3 2 1 3 2 3 \n" +
            "2 1 2 2 1 3 1 \n" +
            "0 1 1 2 2 \n" +
            "2 1 2 3 2 3 3";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //One extra and incorrect Cell is given
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 5 2 \n" + //One extra cell is given
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Values of a Cell are given with other char and not [].
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 [5] 2 1 {5]\n" + //Instead of [2], we got {2]
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Invalid position
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 0 1 1 3 \n" +    //Instead of [1][2], it's [0][1] ==> Invalid position
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });
        
        //Some random numbers are not given correctly.
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 twd 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 . 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 @ 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //#partitions is 0 and cells are given
        assertThrows(ParseException.class, () -> {
            String file = "2 0 \n" +
            "1 6 4 1 1 [1] 1 2 [2] 2 1 [2] 2 2 [1]"; 
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //A cell has negative values
        assertThrows(ParseException.class, () -> {
            String file = "2 1 \n" +
            "1 6 4 1 1 [-1] 1 2 [2] 2 1 [2] 2 2 [1]";
            Board b = new Board();
            b = Board.fromFile(file);
        });
        
        //Same cell appears two times (1)
        assertThrows(ParseException.class, () -> {
            String file = "2 1 \n" +
            "1 6 4 1 1 [1] 1 2 [2] 2 1 [2] 1 2";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Same cell appears two times (2)
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 2 6 1 1 \n" +  //Duplicated [1][1]
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 4 5";    //Duplicated [4][5]
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Error in format
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 2 6 3 6 \n" + //One number is missing
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Error in format 2
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 [2] [2]\n" + //Cell[3][6] has two values.
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Error in format 3
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6\n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +
            "4 2 2 6 4";            //Last Cell is missing
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Error in format 4
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 [2]\n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 3 2 4 1 4 2 \n" +
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3";    //Last partition is missing
            Board b = new Board();
            b = Board.fromFile(file);
        });

        //Error in format 5
        assertThrows(ParseException.class, () -> {
            String file = "6 15 \n" +
            "1 11 2 1 1 2 1 \n" + 
            "4 2 2 1 2 1 3 \n" +
            "3 20 2 1 4 2 4 \n" +
            "3 6 4 1 5 1 6 2 6 3 6 \n" +
            "2 3 2 2 2 2 3 \n" +
            "4 3 2 2 5 3 5 \n" +
            "3 240 4 3 1 [q] 3 2 4 1 4 2 \n" +  //Some value is q.
            "3 6 2 3 3 3 4 \n" +
            "3 6 2 4 3 5 3 \n" +
            "1 7 3 4 4 5 4 5 5 \n" + 
            "3 30 2 4 5 4 6 \n" +
            "3 6 2 5 1 5 2 \n" +
            "1 9 2 5 6 6 6 \n" +
            "1 8 3 6 1 6 2 6 3 \n" +    
            "4 2 2 6 4 6 5";
            Board b = new Board();
            b = Board.fromFile(file);
        });
    }
}