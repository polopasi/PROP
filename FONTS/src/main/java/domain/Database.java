package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import helpers.InOut;

/**
 * In-memory object database.
 * <p>
 * Manages objects in a centralized way.
 * <p>
 * Needs specific opaque ids to access them and always returns clones or
 * immutable elements.
 * 
 * @author Liam Garriga
 */
public final class Database {
    HashMap<String, User> users;
    HashMap<String, Game> games;
    ArrayList<Level> levels;

    public Database() {
        this.users = new HashMap<>();
        this.games = new HashMap<>();
        this.levels = new ArrayList<>();
    }

    /** @return An immutable view on the <code>Users</code> in the database. */
    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(this.users.values());
    }
    
    /** @return An immutable view on the <code>Levels</code> in the database. */
    public List<Level> getLevels() {
        return Collections.unmodifiableList(this.levels);
    }
    
    /** @return An immutable view on the <code>Games</code> in the database. */
    public Collection<Game> getGames() {
        return Collections.unmodifiableCollection(this.games.values());
    }

    /**
     * @param name Identifier of the User
     * @return A clone of the <code>User</code> with the specified name if it exists or <code>Optional.empty</code> if it doesn't.
     */
    public Optional<User> getUser(String name) {
        final User user = this.users.get(name);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(new User(user));
    }

    /**
     * @param id Identifier of the Level
     * @return A clone of the <code>Level</code> with the specified id if it exists or <code>Optional.empty</code> if it doesn't.
     */
    public Optional<Level> getLevel(int id) {
        if (id >= this.levels.size()) return Optional.empty();
        return Optional.of(new Level(this.levels.get(id)));
    }
    
    /**
     * @param user Username of the user that has a pending game
     * @return A clone of the <code>Game</code> saved by the user with the specified name, or <code>Optional.empty</code> if it doesn't exist.
     */
    public Optional<Game> getGame(String user) {
        final Game game = this.games.get(user);
        if (game == null) {
            return Optional.empty();
        }
        return Optional.of(new Game(game));
    }

    /**
     * @return A List over each Level in the database ordered by the size of their
     *         Board.
     */
    public List<List<Level>> getLevelsBySize() {
        HashMap<Integer, ArrayList<Level>> levels = new HashMap<>();
        for (Level level : this.levels) {
            int size = level.getSize();
            levels.putIfAbsent(size, new ArrayList<>());
            levels.get(size).add(new Level(level));
        }
        return levels
                .entrySet()
                .stream()
                .sorted(HashMap.Entry.comparingByKey())
                .map((entry) -> entry.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Adds a new User with the specified name.
     * <p>
     * Will replace it if it already exists.
     * 
     * @param name Name of the User to add.
     */
    public User addUser(String name) {
        return this.users.put(name, new User(name));
    }

    /**
     * Adds a new Level with the specified creator and board.
     * 
     * If a user with name <code>creator</code> does not exist, the level will still
     * be added.
     * 
     * @param board   Board of the level.
     * @param creator Name of the creator of the level.
     */
    public Level addLevel(Board board, String creator) {
        this.levels.add(new Level(this.levels.size(), board, creator));
        return this.levels.get(this.levels.size() - 1);
    }
    
    /**
     * Adds a new Game with the specified player, level ID and Board state.
     * <p>
     * Will replace it if it already exists.
     * 
     * @param player Name of the User that played the game.
     * @param level ID of the level the User is playing.
     * @param boardState State of the current Board. Will be cloned to avoid overwriting.
     * 
     * @return The newly added Game.
     */
    public Game addGame(String player, int level, Board boardState) {
        boardState = new Board(boardState);
        this.games.put(player, new Game(level, boardState));
        return this.games.get(player);
    }

    /**
     * Updates the provided User in the database.
     * 
     * @param name Name of the User to update.
     * @param user User object to replace the existing one.
     * @return <code>true</code> if the User was updated, <code>false</code> if the User with the specified name does not exist.
     */
    public boolean updateUser(String name, User user) {
        if (!this.users.containsKey(name)) {
            return false;
        }
        this.users.remove(name);
        this.users.put(user.getName(), user);
        return true;
    }

    /**
     * Replaces the Level identified by <code>id</code> with the provided Level
     * object.
     * 
     * @param id    id of the Level to update.
     * @param level Level object to replace the existing one.
     */
    public void updateLevel(int id, Level level) throws IndexOutOfBoundsException {
        level.setId(id);
        this.levels.set(id, level);
    }

    /**
     * Updates the provided Game in the database.
     * 
     * @param game Game object to replace the existing one.
     * @param player Name of the User that played the specified Game.
     * @return <code>true</code> if the Game was updated, <code>false</code> if the Game with the specified name does not exist.
     */
    public boolean updateGame(String player, Game game) {
        if (!this.games.containsKey(player)) {
            return false;
        }
        this.games.put(player, game);
        return true;
    }

    /**
     * @return The current state of the database as a serializable String.
     */
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * <p>
     * Builds a Dabase from the specified <code>from</code> String.
     * <p>
     * The expected format is the one returned by the {@link #serialize()} method,
     * other formats will return <code>Optional.empty()</code>.
     * 
     * @param from The String to convert to a Database.
     * @return A correct Database object.
     */
    public static Optional<Database> deserialize(String from) {
        try {
            Gson gson = new Gson();
            Database database = gson.fromJson(from, Database.class);
            return Optional.of(database);
        } catch (Exception e) {
            e.printStackTrace();
            InOut inout = new InOut();
            inout.printErr(e);
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
