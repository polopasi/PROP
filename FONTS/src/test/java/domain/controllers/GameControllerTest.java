package domain.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.io.IOException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import domain.Board;
import domain.Completion;
import domain.Database;
import domain.Game;
import helpers.InOut;

import static domain.TestUtils.*;

public class GameControllerTest {
    @Test
    public void startNewGame() {
        final Database database = new Database();
        final GameController gameController = new GameController(database);
        
        final String user0 = "Me";
        // Level does not exist
        assertThrows(Exception.class, () -> gameController.startNewGame(user0, 0));
        
        // Start Game on level with id 0
        database.addLevel(new Board(), user0);
        final Game game0 = new Game(0, new Board());
        try {
            assertEquals(game0, gameController.startNewGame(user0, 0));
            assertEquals(Optional.of(game0), database.getGame(user0));
        } catch (Exception e) { Assert.fail(e.toString()); }

        // Start Game with another user
        final String user1 = "They";
        try {
            assertEquals(game0, gameController.startNewGame(user1, 0));
            assertEquals(Optional.of(game0), database.getGame(user1));
            assertSortedEquals(new Game[] { game0, game0 }, database.getGames());
        } catch (Exception e) { Assert.fail(e.toString()); }

        // Start another Game with the same user (will replace it)
        database.addLevel(new Board(), "Random name");
        final Game game1 = new Game(1, new Board());
        try {
            assertEquals(game1, gameController.startNewGame(user0, 1));
            assertEquals(Optional.of(game1), database.getGame(user0));
            assertSortedEquals(new Game[] { game0, game1 }, database.getGames());
        } catch (Exception e) { Assert.fail(e.toString()); }
    }
    
    @Test
    public void saveHasContinueGame() {
        // Test saveGame, continueGame and hasGameSaved

        final Database database = new Database();
        final GameController gameController = new GameController(database);
        
        final String user0 = "Me";
        
        // Game does not exist, does nothing
        gameController.saveGame(user0, null);
        assertTrue(database.getGames().isEmpty());

        // User does not have a saved Game
        assertThrows(Exception.class, () -> gameController.continueGame(user0));

        database.addLevel(new Board(), "Creator");
        database.addLevel(new Board(), "Creator2");
        
        // Normal save and continue
        try {
            final Game game0 = gameController.startNewGame(user0, 0);
            gameController.saveGame(user0, game0);
            assertEquals(game0, gameController.continueGame(user0));
        } catch (Exception e) { Assert.fail(e.toString()); }
        
        // Another
        final String user1 = "I'm out of names";
        try {
            final Game game1 = gameController.startNewGame(user1, 1);
            gameController.saveGame(user1, game1);
            assertEquals(game1, gameController.continueGame(user1));
            assertNotEquals(game1, gameController.continueGame(user0));
        } catch (Exception e) { Assert.fail(e.toString()); }
        
        assertTrue(gameController.hasGameSaved(user0));
        assertTrue(gameController.hasGameSaved(user1));
        assertFalse(gameController.hasGameSaved("New user"));
    }
    
    @Test
    public void setAsCompleted() {
        Database database = new Database();
        final GameController gameController = new GameController(database);
        database.addLevel(new Board(), "Creator");
        assertTrue(database.getLevel(0).get().getRanking().isEmpty());
        
        final String user0 = "Me";
        try {
            final Game game0 = gameController.startNewGame(user0, 0);
            game0.setTime(2);
            gameController.setAsCompleted(game0, user0);
            
            final var ranking = database.getLevel(0).get().getRanking();
            assertSortedEquals(new Completion[] { new Completion(game0.getTime(), user0) }, ranking);
        } catch (Exception e) { Assert.fail(e.toString()); }

        final String user1 = "You";
        try {
            final Game game1 = gameController.startNewGame(user1, 0);
            game1.setTime(1);
            gameController.setAsCompleted(game1, user1);
            
            final var ranking = database.getLevel(0).get().getRanking();
            assertSortedEquals(new Completion[] { new Completion(game1.getTime(), user1), new Completion(2, user0) }, ranking);
        } catch (Exception e) { Assert.fail(e.toString()); }
    }
}

