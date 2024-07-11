package domain.controllers;

import domain.Board;
import domain.Database;
import domain.Level;
import helpers.InOut;

import java.text.ParseException;
import java.util.List;
import java.io.IOException;

/**
 * Manages the Levels of the application.<p>
 * 
 * Has the ability to Import, Export and Create new Levels.<p>
 * 
 * <i>The Export and Create functionalities are not yet implemented.</i>
 * 
 * @author Liam Garriga
 */
public class LevelController {
    Database database;

    public LevelController(Database database) {
        this.database = database;
    }
    
    /**
     * Imports a Level from a file in the specified path and saves it into the Database.<p>
     * 
     * Expects the PROP format: https://www.cs.upc.edu/prop/data/uploads/formatokenken.pdf<p>
     * 
     * If the path does not exist, or the parsing fails, an exception will be thrown.
     * 
     * @param path Path where the level is.
     * @param creator Username of the level's creator.
     * 
     * @throws Exception
     */
    public Level importLevel(String path, String creator) throws Exception {
        try {
            String file = InOut.readToString(path);
            Board board = Board.fromFile(file);
            if (!board.asSolver().isCorrect()) {
                throw new Exception("The selected level '" + path + "' is incorrect, it cannot be imported.");
            }
            if (board.asSolver().solve().isEmpty()) {
                throw new Exception("The selected level '" + path + "' has no solution, it cannot be imported.");
            }
            return this.database.addLevel(board, creator);
        } catch (ParseException p) {
            throw new Exception("The level '" + path + "' has a wrong format.\n" + p.getMessage());
        } catch (IOException e) {
            throw new IOException("File could not be read: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new Exception("The level '" + path + "' has a wrong format.\n" + e.getMessage());
        } 
        catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * @return All the Levels ordered by difficulty (size of the Board).
     */
    public List<List<Level>> getLevelsBySize() {
        return this.database.getLevelsBySize();
    }
}
