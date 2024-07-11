package domain.controllers;

import domain.Database;
import domain.Game;
import domain.Level;
import domain.Board;
import domain.Completion;

/**
 * Manages Starting, Continuing, and Saving KenKen games.
 * <p>
 * 
 * Does <b>not</b> manage persistence.
 * 
 * @author Liam Garriga
 */
public class GameController {
    Database database;

    public GameController(Database database) {
        this.database = database;
    }
    
    /**
     * @return <code>true</code> if the User with the specified name has a pending
     *         Game.
     */
    public boolean hasGameSaved(String user) {
        return this.database.getGame(user).isPresent();
    }
    
    /**
     * Creates a new Game with the specified username and level and returns it.
     * <p>
     * If the User already had a pending Game, it will be replaced.
     * <p>
     * 
     * @param user  Name of the User.
     * @param levelID ID of the Level the User will play.
     * @return Returns the created Game.
     * @throws Exception If a Level with the ID does not exist.
     */
    public Game startNewGame(String user, int levelID) throws Exception {
        final Board state = this.database
                .getLevel(levelID)
                .orElseThrow(() -> new Exception("The level with id '" + levelID + "' does not exist"))
                .getBoard();
        return new Game(this.database.addGame(user, levelID, state));
    }

    /**
     * @param user Name of the User with a pending Game.
     * @return The User's pending Game
     * @throws Exception If the specified User does not have a pending Game.
     */
    public Game continueGame(String user) throws Exception {
        Game game = this.database.getGame(user)
                .orElseThrow(() -> new Exception("The user with name '" + user + "' does not have a Game to continue"));
        return new Game(game);
    }
    
    /**
     * Saves the Game to be continued on the future.
     * @param game Game to be saved.
     * @param user Name of the player of the specified Game.
     */
    public void saveGame(String user, Game game) {
        this.database.updateGame(user, game);
    }
    
    /**
     * Adds the Game into the ranking of the Level.
     * @param game Completed Game.
     * @param user Name of the player of the specified Game.
     */
    public void setAsCompleted(Game game, String user) {
        final int levelId = game.getLevel();
        final Level level = database.getLevel(levelId).get();
        final Completion completion = new Completion(game.getTime(), user);
        level.addCompletion(completion);
        database.updateLevel(levelId, level);
        for (final var c : game.getBoard().getCells()) c.clear();
        saveGame(user, game);
    }
}
