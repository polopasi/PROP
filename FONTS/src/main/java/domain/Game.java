package domain;

import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents one Game of KenKen.<p>
 * 
 * Keeps track of the time since the game started, the Level that's being played and the current state of the Board.
 * @author Mikel Torres Martínez
 */

public class Game {
    /**
     * Time since start of Game
    */
    int time;

    /**
     * Level ID
    */
    int level;

    /**
     * Game State
    */
    Board board;

    // Constructors

    /**
     * Creates a new `Game` instance with the specified level and board state.
     *
     * @param level the level ID associated with the game.
     * @param board the initial game board state as a {@link Board} object.
    */
    public Game(int level, Board board) {
        this.level = level;
        this.board = board;
    }

    /**
     * Creates a deep copy of a given `Game` instance.
     *
     * @param clone the `Game` instance to be copied.
    */
    public Game(Game clone) {
        this.time = clone.time;
        this.level = clone.level;
        this.board = new Board(clone.board);
    }

    @Override
    public Object clone() {
        // Create a new 'Game' object by calling the 'clone' constructor
        return new Game(this);
    }

    // Getters
    /**
     * Gets the time elapsed since the start of the game in seconds.
     *
     * @return the time elapsed since the start of the game in seconds.
    */
    public int getTime() {
        return this.time;
    }

    /**
     * Gets the level ID associated with the current game.
     *
     * @return the level ID associated with the current game.
    */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets the current game board state as a {@link Board} object.
     *
     * @return the current game board state as a {@link Board} object.
    */
    public Board getBoard() {
        return this.board;
    }

    // Setters

    /**
     * Sets the time elapsed since the start of the game in seconds.
     *
     * @param newTime the new time elapsed since the start of the game in seconds.
    */
    public void setTime(int newTime) {
        this.time = newTime;
    }

    /**
     * Sets the level ID associated with the current game.
     *
     * @param newLevel the new level ID associated with the current game.
    */
    public void setLevel(int newLevel) {
        this.level = newLevel;
    }

    /**
     * Sets the current game board state.
     *
     * @param newBoard the new game board state as a {@link Board} object.
    */
    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }

    @Override
    public boolean equals(Object obj) {
        // If pointers are equal, then the Objects are too
        if (this == obj) {
            return true;
        }
        // Ensures Object is not null
        if (obj == null) {
            return false;
        }
        // Ensures the following cast is correct
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        // Cast 'Object' to 'Example' to access the fields
        final Game other = (Game) obj;
        return 
            this.time == other.time &&
            this.level == other.level &&
            this.board.equals(other.board);
    }
    
    // Must override, because we don't know if we will be using it in a HashMap/HashSet
    @Override
    public int hashCode() {
        // The hash of all the attributes
        return Objects.hash(
            this.time,
            this.level,
            this.board
        );
    }
    
    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}