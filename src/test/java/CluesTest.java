import org.junit.Test;

import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class CluesTest {
    private static final Logger LOGGER = Logger.getLogger(CluesTest.class.getName());

    /**
     * Returns a Clues object which looks like this:
     *
     * 213|
     * 11-|
     * 2-1|
     * --1|
     * ---|
     */
    private Clues getBasicClues() {
        Clues clues = new Clues(5, 3);

        int[] array = new int[3];
        array[0] = 2;
        array[1] = 1;
        array[2] = 3;
        clues.setCluesInwards(0, array);

        array[0] = 1;
        array[1] = 1;
        array[2] = 0;
        clues.setCluesInwards(1, array);

        array[0] = 2;
        array[1] = 0;
        array[2] = 1;
        clues.setCluesInwards(2, array);

        array[0] = 0;
        array[1] = 0;
        array[2] = 1;
        clues.setCluesInwards(3, array);

        array[0] = 0;
        array[1] = 0;
        array[2] = 0;
        clues.setCluesInwards(4, array);

        return clues;
    }

    @Test
    public void testBasicOperation() throws Exception {
        Clues clues = getBasicClues();
        assertEquals("Input array direction does not work!", 2, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals("Input array column set does not work!", 0, clues.getClue(IODirection.INWARDS, 4, 0));
    }

    @Test
    public void setClue() throws Exception {
        //Sets clues to be:
        //***
        //--|

        Clues clues = new Clues(1, 2);

        //***
        //1-|
        clues.setClue(IODirection.INWARDS, 0, 0, 1);
        assertEquals("/Clues.setClue(INWARDS) does not set the value/ or /Clues.getClue(INWARDS) does not read the value right/", 1, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals("Clues.setClue(INWARDS) did write to cell it should not have written to", 0, clues.getClue(IODirection.INWARDS, 0, 1));
        assertEquals("Clues.getClue(OUTWARDS) does not skip empty spaces", 0, clues.getClue(IODirection.OUTWARDS, 0, 1));

        //***
        //13|
        clues.setClue(IODirection.OUTWARDS, 0, 0, 3);
        assertEquals("/Clues.setClue(OUTWARDS) does not set the value/ or /Clues.getClue(OUTWARDS) does not read the value right/", 3, clues.getClue(IODirection.OUTWARDS, 0, 0));

    }

    @Test
    public void setCluesOutwards() throws Exception {
        int[] array = new int[2];
        array[0] = 2;
        array[1] = 3;

        Clues clues = new Clues(1, 2);
        //***
        //32|
        clues.setCluesOutwards(0, array);

        assertEquals(3, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals(2, clues.getClue(IODirection.INWARDS, 0, 1));
        assertEquals(2, clues.getClue(IODirection.OUTWARDS, 0, 0));
    }

    @Test
    public void setCluesInwards() throws Exception {
        int[] array = new int[2];
        array[0] = 2;
        array[1] = 3;

        Clues clues = new Clues(1, 2);
        //***
        //23|
        clues.setCluesInwards(0, array);

        assertEquals(2, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals(3, clues.getClue(IODirection.INWARDS, 0, 1));
        assertEquals(3, clues.getClue(IODirection.OUTWARDS, 0, 0));
    }

    @Test
    public void align() throws Exception {
        /*
        //****
        * 213|
        * 11-|
        * 2-1|
        * --1|
        * ---|
        */

        Clues clues = getBasicClues();
        clues.align();

        assertEquals("Clues.align() did shift even the *** case!", 2, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals("Clues.align() did shift even the *** case!", 1, clues.getClue(IODirection.INWARDS, 0, 1));
        assertEquals("Clues.align() did shift even the *** case!", 3, clues.getClue(IODirection.INWARDS, 0, 2));

        assertEquals("Clues.align() did not shift the **- case", 1, clues.getTrueClue(IODirection.OUTWARDS, 1, 0));
        assertEquals("Clues.align() did not delete the shifted value of **- case", 0, clues.getTrueClue(IODirection.INWARDS, 1, 0));

        assertEquals("Clues.align() did not shift the *-* case", 2, clues.getTrueClue(IODirection.INWARDS, 2, 1));
        assertEquals("Clues.align() shifted the 1 in 2-1 case", 1, clues.getTrueClue(IODirection.OUTWARDS, 2, 0));
        assertEquals("Clues.align() did not delete the 2 in 2-1 case", 0, clues.getTrueClue(IODirection.INWARDS, 2, 0));

    }

    @Test
    public void getMaxIndex() throws Exception {
        int[] array = new int[3];

        Clues clues = new Clues(2, 2);
        assertEquals("MaxIndex is not set when Clues is initialized", 2, clues.getMaxIndex());
        clues.setCluesInwards(1, array);
        assertEquals("MaxIndex is not refreshing when appending new array!", 3, clues.getMaxIndex());
        array = new int[5];
        clues.setCluesInwards(0, array);
        assertEquals("MaxIndex is not refreshing when appending new array!", 5, clues.getMaxIndex());

    }

    @Test
    public void getColumns() throws Exception {
        Clues clues = new Clues(1, 3);
        assertEquals(1, clues.getColumns());
        clues = new Clues(23, 3);
        assertEquals(23, clues.getColumns());
    }

    @Test
    public void getIndexes() throws Exception {
        Clues clues = new Clues(1, 3);
        assertEquals(3, clues.getIndexes());
        clues = new Clues(2, 5);
        assertEquals(5, clues.getIndexes());
        int[] array = new int[7];
        clues.setCluesInwards(0, array);
        assertEquals(7, clues.getIndexes());
    }

    @Test
    public void getIndexes1() throws Exception {
        Clues clues = new Clues(5, 10);
        for (int i = 1; i < 5; i++) {
            assertEquals(10, clues.getIndexes(i));
        }

        int[] array = new int[12];
        clues.setCluesInwards(2, array);
        assertEquals(12, clues.getIndexes(2));
        array = new int[21];
        assertEquals(10, clues.getIndexes(0));
        clues.setCluesInwards(4, array);
        assertEquals(21, clues.getIndexes(4));
    }

    @Test
    public void clearClues() throws Exception {
        Clues clues = new Clues(2, 2);
        clues.setClue(IODirection.INWARDS, 0, 0, 5);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 4);
        assertEquals(5, clues.getClue(IODirection.INWARDS, 0, 0));
        clues.clearClues();
        clues.setCurrCol(0);
        for (int i :
                clues) {
            assertEquals(0, i);
        }

        clues.setClue(IODirection.OUTWARDS, 1, 1, 2);
        assertEquals(2, clues.getClue(IODirection.INWARDS, 1, 0));
        clues.clearClues();
        assertEquals(0, clues.getClue(IODirection.INWARDS, 1, 0));
    }

    @Test
    public void getCluesInwards() throws Exception {
        Clues clues = new Clues(2, 3);
        int[] array = new int[3];
        array[0] = 2;
        array[2] = 3;

        //****
        //2-3|
        //3-2|
        clues.setCluesInwards(0, array);
        clues.setCluesOutwards(1, array);

        int[] got = clues.getCluesInwards(0);
        int[] got2 = clues.getCluesInwards(1);

        assertFalse("Wrong direction of setCluesInwards() or setCluesOutwards()", got[0] == got2[0]);
        assertEquals(2, got2[2]);
        assertEquals(3, got2.length);
        assertEquals(0, got2[1]);
        assertEquals(3, got2[0]);
        assertEquals(3, got[2]);
    }

    @Test
    public void getCluesOutwards() throws Exception {
        Clues clues = new Clues(2, 3);
        int[] array = new int[3];
        array[0] = 2;
        array[2] = 3;

        //****
        //2-3|
        //---|
        clues.setCluesInwards(0, array);

        //****
        //2-3|
        //3-2|
        clues.setCluesOutwards(1, array);

        int[] got = clues.getCluesOutwards(0);
        int[] got2 = clues.getCluesOutwards(1);

        assertFalse("Wrong direction of setCluesInwards() or setCluesOutwards()", got[0] == got2[0]);

        assertEquals(2, got[2]);
        assertEquals(3, got.length);
        assertEquals(0, got[1]);
        assertEquals(3, got[0]);
        assertEquals(3, got2[2]);

    }

    @Test
    public void getClueInwards() throws Exception {
        Clues clues = getBasicClues();

        assertTrue("Clues.getClueInwards() is wrong direction!", clues.getClueInwards(0, 0) == 2);
        assertEquals(2, clues.getClueInwards(0, 0));
        assertEquals(1, clues.getClueInwards(0, 1));
        assertEquals(3, clues.getClueInwards(0, 2));

        assertEquals(1, clues.getClueInwards(1, 0));
        assertEquals(1, clues.getClueInwards(1, 1));
        assertEquals(0, clues.getClueInwards(1, 2));

        assertEquals("Skipping empty spaces is not working! ", 1, clues.getClueInwards(3, 0));

        assertEquals("Skipping empty spaces is not working!", 1, clues.getClueInwards(2, 1));
        assertEquals("Skipping empty spaces is showing shifted values at their original location", 0, clues.getClueInwards(2, 2));

        LOGGER.info("****Four Severe logs should be logged here:");
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClueInwards(5, 0));
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClueInwards(-1, 0));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClueInwards(0, 10));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClueInwards(0, -1));

    }

    @Test
    public void getClueOutwards() throws Exception {
        Clues clues = getBasicClues();

        assertTrue("Clues.getClueOutwards() is wrong direction!", clues.getClueOutwards(0, 0) == 3);
        assertEquals(3, clues.getClueOutwards(0, 0));
        assertEquals(2, clues.getClueOutwards(0, 2));

        assertEquals(1, clues.getClueOutwards(1, 1));
        assertEquals("Skipping empty spaces is not working", 1, clues.getClueOutwards(1, 0));

        assertEquals("Skipping empty spaces is showing shifted clues at their original location!", 0, clues.getClueOutwards(1, 2));

        LOGGER.info("****Four Severe logs should be logged here:");
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClueOutwards(5, 0));
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClueOutwards(-1, 0));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClueOutwards(0, 10));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClueOutwards(0, -1));
    }

    @Test
    public void getClue() throws Exception {
        Clues clues = getBasicClues();
        assertEquals(2, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals(3, clues.getClue(IODirection.OUTWARDS, 0, 0));
    }

    @Test
    public void checkIterator() throws Exception {
        Clues clues = getBasicClues();
        clues.setCurrCol(0);
        assertEquals(0, clues.getCurrCol());
        checkIterAgainstArray(clues, clues.getCluesInwards(0));

        clues.setCurrCol(1);
        checkIterAgainstArray(clues, clues.getCluesInwards(1));

        clues.setCurrCol(0);
        clues.setIterDirection(IODirection.OUTWARDS);
        checkIterAgainstArray(clues, clues.getCluesOutwards(0));

        clues.setCurrCol(1);
        clues.setIterDirection(IODirection.OUTWARDS);
        int[] array = new int[3];
        array[0] = array[1] = 1;
        array[2] = 0;
        checkIterAgainstArray(clues, array);

        array = new int[3];
        clues.setCurrCol(4);
        checkIterAgainstArray(clues, array);

        clues.setIterDirection(IODirection.OUTWARDS);
        checkIterAgainstArray(clues, array);

        clues.setCurrCol(3);
        clues.setIterDirection(IODirection.INWARDS);
        int counter = 0;
        for (int clue :
                clues) {
            assertEquals(0, counter);
            counter++;
            assertEquals(1, clue);
        }

        counter = 0;
        clues.setIterDirection(IODirection.OUTWARDS);
        for (int clue :
                clues) {
            assertEquals(0, counter);
            counter++;
            assertEquals(1, clue);
        }
    }

    private void checkIterAgainstArray(Clues clues, int[] array) {
        int counter = 0;
        for (int clue : clues) {
            assertEquals(array[counter], clue);
            counter++;
        }
    }
}