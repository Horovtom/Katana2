import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class CluesTest {


    //Test Clues consist of (horizontally):
    // 0   0   0
    // 1   2   3
    // 1   0   2
    // 0   1   2
    // 1   2   0
    Clues testClues;

    @org.junit.Before
    public void setUp() throws Exception {
        testClues = new Clues(5, 3);
        int[] array = new int[]{0, 0, 0};
        testClues.setCluesReversed(0, array);
        array = new int[]{3, 2, 1};
        testClues.setCluesReversed(1, array);
        array = new int[]{2, 0, 1};
        testClues.setCluesReversed(2, array);
        array = new int[]{2, 1, 0};
        testClues.setCluesReversed(3, array);
        array = new int[]{0, 2, 1};
        testClues.setCluesReversed(4, array);
    }

    @org.junit.Test
    public void clearClues() throws Exception {
        setUp();
        assertEquals(1, testClues.getClue(1, 0));
        testClues.clearClues();
        assertEquals(0, testClues.getClue(1, 0));
        assertEquals(0, testClues.getClue(2, 0));
        assertEquals(0, testClues.getClue(4, 2));
    }

    @org.junit.Test
    public void setClueReversed() throws Exception {
        setUp();
        assertEquals(3, testClues.getClueReversed(1, 0));
        testClues.setClueReversed(1, 0, 6);
        assertEquals(6, testClues.getClueReversed(1, 0));
        testClues.setClueReversed(1, 1, 10);
        assertEquals(10, testClues.getClue(1, 1));
        testClues.setClueReversed(1, 2, 7);
        assertEquals(7, testClues.getClueReversed(1, 2));

    }

    @org.junit.Test
    public void setClue() throws Exception {
        setUp();
        testClues.setClue(4, 0, 5);
        testClues.setClue(4, 1, 4);
        testClues.setClue(4, 2, 10);
        assertEquals(5, testClues.getClue(4, 0));
        assertEquals(4, testClues.getClue(4, 1));
        assertEquals(10, testClues.getClueReversed(4, 0));
    }

    @org.junit.Test
    public void getClues() throws Exception {
        setUp();
        int[] clues = testClues.getClues(1);
        assertEquals(3, clues[2]);
        assertEquals(2, clues[1]);
        assertEquals(1, clues[0]);
    }

    @org.junit.Test
    public void getCluesReversed() throws Exception {
        setUp();
        int[] clues = testClues.getCluesReversed(1);
        assertEquals(1, clues[2]);
        assertEquals(2, clues[1]);
        assertEquals(3, clues[0]);
    }

    @org.junit.Test
    public void testIterable() throws Exception {
        setUp();
        testClues.setCurrCol(1);
        int i = 1;
        for (int number :
                testClues) {
            assertEquals(i, number);

            i++;
        }
    }

    @Test
    public void align() throws Exception {
        setUp();
        testClues.align();
        assertEquals(0, testClues.getClue(0, 0));
        assertEquals(0, testClues.getClue(0, 1));
        assertEquals(0, testClues.getClue(0, 2));

        assertEquals(2, testClues.getClue(1, 1));
        assertEquals(3, testClues.getClue(1, 2));

        assertEquals(1, testClues.getClue(2, 0));
        assertEquals(2, testClues.getClue(2, 1));

        assertEquals(1, testClues.getClue(3, 0));
        assertEquals(2, testClues.getClue(3, 1));

        assertEquals(1, testClues.getClue(4, 0));
        assertEquals(2, testClues.getClue(4, 1));
    }
}