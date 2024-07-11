//Package
package domain;

//Imports
import java.util.ArrayList;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;

/**
 * Implements a level inside of the project's database.
 * @author Alexander Martínez
 */
public class Level {

    // Attributes
    int id;
    Board board;
    ArrayList<Completion> ranking;
    String creator;

    // Constructors (In order to create an instance of Level, you must provide a
    // board and a creator).

    /** Creates an instance of Level.
     * @param board Is the Level's board.
     * @param creator Is the Creator's username.
     */
    public Level(int id, Board board, String creator) {
        this.id = id;
        this.board = board;
        this.ranking = new ArrayList<>();
        this.creator = creator;
    }

    /**
     * Creates a clone of the Object instance.
     * @param clone Is the Object's instance that wants to be cloned.
     */
    public Level(Level clone) {
        this.id = clone.id;
        this.board = new Board(clone.board);
        this.ranking = new ArrayList<>(clone.ranking);
        this.creator = new String(clone.creator);
    }

    // Getters and Setters

    public int getId() {
        return this.id;
    }

    /**
     * @return the size of the board.
     */
    public int getSize() {
        return this.board.getSize();
    }

    /**
     * @return the ranking of the level.
     */
    public List<Completion> getRanking() {
        List<Completion> list = this.ranking.stream().sorted().collect(Collectors.toList());
        return Collections.unmodifiableList(list);
    }

    /**
     * @return the level's board.
     */
    public Board getBoard() {
        return new Board(this.board);
    }
    
    /**
     * @return the level's creator.
     */
    public String getCreator() {
        return new String(this.creator);
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets newBoard as the new level's board.
     * @param newBoard
     */
    public void updateBoard(Board newBoard) {
        this.board = newBoard;
    }

    // Class methods

    /**
     * Pushes newCompletion to the level's ranking (newCompletion becomes the last element of the ranking).
     * @param newCompletion
     */
    public void addCompletion(Completion newCompletion) {
        this.ranking.add(newCompletion);
    }

    /**
     * Clone constructor. Creates a new Level object.
     */
    @Override
    public Object clone() {
        return new Level(this);
    }

    /**
     * Level class Object Comparator. Compares if the CONTENT of obj is equal to the local Level instance.
     * @param obj Can be any Object.
     */
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
        final Level other = (Level) obj;
        return
            this.id == other.id &&
            this.board.equals(other.board) &&
            this.creator.equals(other.creator) &&
            this.ranking.equals(other.ranking);
    }
    
    // Must override, because we don't know if we will be using it in a HashMap/HashSet.
    /**
     * Level class hashCode implementation.
     */
    @Override
    public int hashCode() {
        // The hash of all the attributes.
        return Objects.hash(
            this.board,
            this.creator,
            this.ranking
        );
    }
    
    /**
     * Level class toString implementation.
     */
    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}