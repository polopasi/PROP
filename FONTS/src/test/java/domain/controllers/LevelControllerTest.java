package domain.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import domain.Database;
import domain.Level;

public class LevelControllerTest {
    @Test
    public void importLevel() {
        final Database database = new Database();
        final LevelController levelC = new LevelController(database);
        
        // Normal import
        try {
            final Level imported = levelC.importLevel(getBoardPath("works.txt"), "Me");
            assertEquals("Me", imported.getCreator());
            assertEquals(0, imported.getId());
            assertEquals(2, imported.getSize());
            assertEquals(new ArrayList<>(), imported.getRanking());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        // Import of file that does not exist
        assertThrows(IOException.class, () -> levelC.importLevel(getBoardPath("doesnotexist"), "Me"));

        // Import of incorrect Board (Operation with only one number)
        assertThrows(Exception.class, () -> levelC.importLevel(getBoardPath("incorrect0.txt"), "Me"));

        // Import of unsolvable Board
        assertThrows(Exception.class, () -> levelC.importLevel(getBoardPath("incorrect1.txt"), "Me"));
        
        // Import Board with incorrect format
        assertThrows(Exception.class, () -> levelC.importLevel(getBoardPath("incorrect2.txt"), "Me"));
    }

    public void getLevelsBySize() {
        // getLevelsBySize is defined as a very thin wrapper of Database#getLevelsBySize.
        // As such, no testing of this method is needed, the tests in Database have enough coverage.
    }


    private String getBoardPath(String fileName) {
        return "src/test/java/domain/controllers/LevelControllerTestBoards/" + fileName;
    }
}
