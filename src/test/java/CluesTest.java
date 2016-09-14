import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class CluesTest {

    //TODO: Fix the ordering? Is it -> or <- !!! FUCK ME!
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
    public void checkOrder() throws Exception {
        //Set clues:
        //
        // --|-----
        // 21|-----
        // 12|-----

        // -     -     |-----
        // [0, 0][0, 1]|-----
        // [1, 0][1, 1]|-----
        Clues clues = new Clues(2, 2);


        // By indexes:
        // ------ Set up
        clues.setClueInwards(0, 0, 2);
        clues.setClueInwards(0, 1, 1);
        clues.setClueInwards(1, 0, 1);
        clues.setClueInwards(1, 1, 2);

        // ------ Check
        //TODO: COMPLETE

    }

    /**
     * Meant to be used with checkOrder()
     */
    private void checkByIndexes(Clues clues) throws Exception {
        assertEquals(1, clues.getClueInwards(0, 1));
        assertEquals(2, clues.getClueInwards(0, 0));
        assertEquals(2, clues.getClueInwards(1, 1));
        assertEquals(1, clues.getClueInwards(1, 0));
    }

    /**
     * Meant to be used with checkOrder()
     */
    private void checkByLoop(Clues clues) throws Exception {
        int[] array = new int[2];
        clues.setCurrCol(0);
        int counter = 0;
        for (int i :
                clues) {
            array[counter] = i;
            counter++;
        }
        assertEquals(2, array[0]);
        assertEquals(1, array[1]);

        clues.setCurrCol(1);
        counter = 0;
        for (int i :
                clues) {
            array[counter] = i;
            counter++;
        }

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
    }

    @org.junit.Test
    public void clearClues() throws Exception {
        setUp();
        assertEquals(1, testClues.getClueInwards(1, 0));
        testClues.clearClues();
        assertEquals(0, testClues.getClueInwards(1, 0));
        assertEquals(0, testClues.getClueInwards(2, 0));
        assertEquals(0, testClues.getClueInwards(4, 2));
    }

    @org.junit.Test
    public void setClueReversed() throws Exception {
        setUp();
        assertEquals(3, testClues.getClueOutwards(1, 0));
        testClues.setClueOutwards(1, 0, 6);
        assertEquals(6, testClues.getClueOutwards(1, 0));
        testClues.setClueOutwards(1, 1, 10);
        assertEquals(10, testClues.getClueInwards(1, 1));
        testClues.setClueOutwards(1, 2, 7);
        assertEquals(7, testClues.getClueOutwards(1, 2));

    }

    @org.junit.Test
    public void setClue() throws Exception {
        setUp();
        testClues.setClueInwards(4, 0, 5);
        testClues.setClueInwards(4, 1, 4);
        testClues.setClueInwards(4, 2, 10);
        assertEquals(5, testClues.getClueInwards(4, 0));
        assertEquals(4, testClues.getClueInwards(4, 1));
        assertEquals(10, testClues.getClueOutwards(4, 0));
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
        assertEquals(0, testClues.getClueInwards(0, 0));
        assertEquals(0, testClues.getClueInwards(0, 1));
        assertEquals(0, testClues.getClueInwards(0, 2));

        assertEquals(2, testClues.getClueInwards(1, 1));
        assertEquals(3, testClues.getClueInwards(1, 2));

        assertEquals(1, testClues.getClueInwards(2, 0));
        assertEquals(2, testClues.getClueInwards(2, 1));

        assertEquals(1, testClues.getClueInwards(3, 0));
        assertEquals(2, testClues.getClueInwards(3, 1));

        assertEquals(1, testClues.getClueInwards(4, 0));
        assertEquals(2, testClues.getClueInwards(4, 1));
    }
}