package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.util.ArrayList;
import org.junit.Test;

public class GameTest {

    @Test
    public void testConstructorAndGetters() {
        int testLevel = 2;
        ArrayList<Cell> testCells = new ArrayList<>();
        ArrayList<Partition> testPartitions = new ArrayList<>();

        Game game = new Game(testLevel, new Board(3, testCells, testPartitions));

        assertEquals(0, game.getTime());
        assertEquals(testLevel, game.getLevel());
        assertEquals(3, game.getBoard().getSize());
        assertEquals(testCells, game.getBoard().getCells());
        assertEquals(testPartitions, game.getBoard().getPartitions());
    }

    @Test
    public void testSetters() {
        Game game = new Game(1, new Board(2, new ArrayList<>(), new ArrayList<>()));

        game.setTime(100);
        game.setLevel(3);

        ArrayList<Cell> newCells = new ArrayList<>();
        ArrayList<Partition> newPartitions = new ArrayList<>();
        Board newBoard = new Board(4, newCells, newPartitions);
        game.setBoard(newBoard);

        assertEquals(100, game.getTime());
        assertEquals(3, game.getLevel());
        assertEquals(4, game.getBoard().getSize());
        assertEquals(newCells, game.getBoard().getCells());
        assertEquals(newPartitions, game.getBoard().getPartitions());
    }

    @Test
    public void testClone() {
        ArrayList<Cell> testCells = new ArrayList<>();
        ArrayList<Partition> testPartitions = new ArrayList<>();
        Game game = new Game(2, new Board(3, testCells, testPartitions));
        Game clonedGame = (Game) game.clone();

        assertNotSame(game, clonedGame);
        assertEquals(game.getTime(), clonedGame.getTime());
        assertEquals(game.getLevel(), clonedGame.getLevel());
        assertEquals(game.getBoard().getSize(), clonedGame.getBoard().getSize());
        assertEquals(game.getBoard().getCells(), clonedGame.getBoard().getCells());
        assertEquals(game.getBoard().getPartitions(), clonedGame.getBoard().getPartitions());
    }
}
