package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import static domain.TestUtils.assertSortedEquals;
import static domain.TestUtils.assertListEquals;

public class DatabaseTest {
    /**
     * Test Get, Add and Update User.
     */
    @Test
    public void getAddUpdateUser() {
        Database db = new Database();

        assertTrue(db.getUsers().isEmpty());
        assertTrue(db.getUser("Alberto").isEmpty());
        assertFalse(db.updateUser("Alberto", null));

        final var name0 = "Alberto";
        final var user0 = new User(name0);

        db.addUser(name0);
        assertEquals(db.getUser(name0), Optional.of(user0));
        assertSortedEquals(new User[] { user0 }, db.getUsers());

        final var name1 = "Pedro";
        final var user1 = new User(name1);

        db.addUser(name1);
        assertEquals(db.getUser(name1), Optional.of(user1));
        assertSortedEquals(
                new User[] {
                        user0,
                        user1
                },
                db.getUsers());

        db.addUser(name1);
        assertSortedEquals(
                new User[] {
                        user0,
                        user1
                },
                db.getUsers());

        final var name2 = "():;==";
        final var user2 = new User(name2);

        db.addUser(name2);
        assertEquals(db.getUser(name2), Optional.of(user2));
        assertSortedEquals(
                new User[] {
                    user0,
                    user1,
                    user2,
                },
                db.getUsers());

        user2.setName("Eduarda");
        assertTrue(db.updateUser(name2, user2));
        assertEquals(db.getUser("Eduarda"), Optional.of(user2));
        assertSortedEquals(
                new User[] {
                        user0,
                        user1,
                        user2,
                },
                db.getUsers());
    }

    /**
     * Test Get, GetLevelsBySize, Add and Update Level.
     */
    @Test
    public void getAddUpdateLevel() {
        Database db = new Database();

        assertTrue(db.getLevel(0).isEmpty());
        assertTrue(db.getLevels().isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> db.updateLevel(0, new Level(0, new Board(), "")));

        final var id0 = db.getLevels().size();
        final var board0 = new Board();
        final var creator0 = "Potatastic";
        final var level0 = new Level(id0, board0, creator0);

        db.addLevel(board0, creator0);
        assertEquals(Optional.of(level0), db.getLevel(id0));
        assertListEquals(new Level[] { level0 }, db.getLevels());

        final var id1 = db.getLevels().size();
        final var level1 = new Level(id1, board0, creator0);

        db.addLevel(board0, creator0);
        assertNotEquals(Optional.of(level0), db.getLevel(id1));
        assertEquals(Optional.of(level1), db.getLevel(id1));
        assertListEquals(new Level[] { level0, level1 }, db.getLevels());
        assertListEquals(new Level[][] { new Level[] { level0, level1 } }, db.getLevelsBySize());

        final var id2 = db.getLevels().size();
        final var board2 = new Board(2, new ArrayList<>(), new ArrayList<>());
        final var creator2 = "Manolo";
        final var level2 = new Level(id2, board2, creator2);

        db.addLevel(board2, creator2);
        assertNotEquals(Optional.of(level0), db.getLevel(id2));
        assertNotEquals(Optional.of(level1), db.getLevel(id2));
        assertEquals(Optional.of(level2), db.getLevel(id2));
        assertListEquals(new Level[] { level0, level1, level2 }, db.getLevels());
        assertListEquals(new Level[][] { new Level[] { level0, level1 }, new Level[] { level2 } },
                db.getLevelsBySize());

        final Level newLevel0 = new Level(0, level2.getBoard(), level2.getCreator());
        db.updateLevel(id0, newLevel0);
        assertNotEquals(Optional.of(level0), db.getLevel(id0));
        assertNotEquals(Optional.of(level0), db.getLevel(id0));
        assertNotEquals(Optional.of(level2), db.getLevel(id0));
        assertEquals(Optional.of(newLevel0), db.getLevel(id0));
        assertListEquals(new Level[] { newLevel0, level1, level2 }, db.getLevels());
        assertListEquals(new Level[][] { new Level[] { level1 }, new Level[] { newLevel0, level2 } },
                db.getLevelsBySize());
    }
    
    @Test
    public void getAddUpdateGame() {
        Database db = new Database();

        assertTrue(db.getGames().isEmpty());
        assertEquals(Optional.empty(), db.getGame("Pedro"));
        assertFalse(db.updateGame("Pedro", null));

        final int level0 = 0;
        final Board board0 = new Board();
        final Game game0 = new Game(level0, board0);
        final String player0 = "Pedro";
        // Add new Game
        db.addGame(player0, level0, board0);
        assertEquals(Optional.of(game0), db.getGame(player0));
        assertSortedEquals(new Game[] { game0 }, db.getGames());

        final int level1 = 1;
        final Board board1 = new Board();
        final Game game1 = new Game(level1, board1);
        final String player1 = "Fulgencio";
        // Add new Game
        db.addGame(player1, level1, board1);
        assertEquals(Optional.of(game1), db.getGame(player1));
        assertSortedEquals(new Game[] { game0, game1 }, db.getGames());
        
        // Update time of Game of Player1
        Game newGame1 = db.getGame(player1).get();
        newGame1.setTime(5);
        db.updateGame(player1, newGame1);
        newGame1 = new Game(level1, board1);
        newGame1.setTime(5);
        assertEquals(Optional.of(newGame1), db.getGame(player1));
        assertSortedEquals(new Game[] { game0, newGame1 }, db.getGames());
    
        final int level2 = 2;
        final Board board2 = new Board();
        final Game game2 = new Game(level2, board2);
        // Overwrite game of Player1
        db.addGame(player1, level2, board2);
        assertEquals(Optional.of(game2), db.getGame(player1));
        assertSortedEquals(new Game[] { game0, game2 }, db.getGames());
    }
}
