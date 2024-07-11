package domain;

import static domain.TestUtils.assertListEquals;
import static domain.TestUtils.assertSortedEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class LevelTest {

    @Test
    public void getRankingAndAddCompletion() {
        //Case 0: Empty Ranking.
        Level testLevel = new Level(0, null, null);
        Completion[] expected = new Completion[]{};
        assertListEquals(expected, testLevel.getRanking());

        //Case 1: 1 element ranking.
        testLevel = new Level(0, null, null);
        testLevel.addCompletion(new Completion(10, "Alex"));
        expected = new Completion[]{new Completion(10, "Alex")};
        assertListEquals(expected, testLevel.getRanking());

        //Case 1: Unsorted ranking.
        testLevel = new Level(0, null, null);
        testLevel.addCompletion(new Completion(100, "Alex"));
        testLevel.addCompletion(new Completion(10, "Liam"));
        testLevel.addCompletion(new Completion(50, "Mikel"));
        testLevel.addCompletion(new Completion(20, "Pol"));
        expected = new Completion[]{new Completion(100, "Alex"),
                                    new Completion(10, "Liam"),
                                    new Completion(50, "Mikel"),
                                    new Completion(20, "Pol")};
        assertSortedEquals(expected, testLevel.getRanking());
    }
}