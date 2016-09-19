import org.junit.Test;

import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.Assert.*;

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
        clues.setClues(IODirection.INWARDS,0, array);

        array[0] = 1;
        array[1] = 1;
        array[2] = 0;
        clues.setClues(IODirection.INWARDS,1, array);

        array[0] = 2;
        array[1] = 0;
        array[2] = 1;
        clues.setClues(IODirection.INWARDS,2, array);

        array[0] = 0;
        array[1] = 0;
        array[2] = 1;
        clues.setClues(IODirection.INWARDS,3, array);

        array[0] = 0;
        array[1] = 0;
        array[2] = 0;
        clues.setClues(IODirection.INWARDS,4, array);

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
        clues.setClues(IODirection.OUTWARDS, 0, array);

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
        clues.setClues(IODirection.INWARDS,0, array);

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
        clues.setClues(IODirection.INWARDS,1, array);
        assertEquals("MaxIndex is not refreshing when appending new array!", 3, clues.getMaxIndex());
        array = new int[5];
        clues.setClues(IODirection.INWARDS,0, array);
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
        clues.setClues(IODirection.INWARDS,0, array);
        assertEquals(7, clues.getIndexes());
    }

    @Test
    public void getIndexes1() throws Exception {
        Clues clues = new Clues(5, 10);
        for (int i = 1; i < 5; i++) {
            assertEquals(10, clues.getIndexes(i));
        }

        int[] array = new int[12];
        clues.setClues(IODirection.INWARDS,2, array);
        assertEquals(12, clues.getIndexes(2));
        array = new int[21];
        assertEquals(10, clues.getIndexes(0));
        clues.setClues(IODirection.INWARDS,4, array);
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
        clues.setClues(IODirection.INWARDS,0, array);
        clues.setClues(IODirection.OUTWARDS,1, array);

        int[] got = clues.getClues(IODirection.INWARDS, 0);
        int[] got2 = clues.getClues(IODirection.INWARDS,1);

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
        clues.setClues(IODirection.INWARDS, 0, array);

        //****
        //2-3|
        //3-2|
        clues.setClues(IODirection.OUTWARDS,1, array);

        int[] got = clues.getClues(IODirection.OUTWARDS ,0);
        int[] got2 = clues.getClues(IODirection.OUTWARDS ,1);

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

        assertTrue("Clues.getClueInwards() is wrong direction!", clues.getClue(IODirection.INWARDS,0, 0) == 2);
        assertEquals(2, clues.getClue(IODirection.INWARDS,0, 0));
        assertEquals(1, clues.getClue(IODirection.INWARDS,0, 1));
        assertEquals(3, clues.getClue(IODirection.INWARDS,0, 2));

        assertEquals(1, clues.getClue(IODirection.INWARDS,1, 0));
        assertEquals(1, clues.getClue(IODirection.INWARDS,1, 1));
        assertEquals(0, clues.getClue(IODirection.INWARDS,1, 2));

        assertEquals("Skipping empty spaces is not working! ", 1, clues.getClue(IODirection.INWARDS,3, 0));

        assertEquals("Skipping empty spaces is not working!", 1, clues.getClue(IODirection.INWARDS,2, 1));
        assertEquals("Skipping empty spaces is showing shifted values at their original location", 0, clues.getClue(IODirection.INWARDS,2, 2));

        LOGGER.info("****Four Severe logs should be logged here:");
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClue(IODirection.INWARDS,5, 0));
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClue(IODirection.INWARDS,-1, 0));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClue(IODirection.INWARDS,0, 10));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClue(IODirection.INWARDS,0, -1));

    }

    @Test
    public void getClueOutwards() throws Exception {
        Clues clues = getBasicClues();

        assertTrue("Clues.getClueOutwards() is wrong direction!", clues.getClue(IODirection.OUTWARDS,0, 0) == 3);
        assertEquals(3, clues.getClue(IODirection.OUTWARDS,0, 0));
        assertEquals(2, clues.getClue(IODirection.OUTWARDS,0, 2));

        assertEquals(1, clues.getClue(IODirection.OUTWARDS,1, 1));
        assertEquals("Skipping empty spaces is not working", 1, clues.getClue(IODirection.OUTWARDS,1, 0));

        assertEquals("Skipping empty spaces is showing shifted clues at their original location!", 0, clues.getClue(IODirection.OUTWARDS,1, 2));

        LOGGER.info("****Four Severe logs should be logged here:");
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClue(IODirection.OUTWARDS,5, 0));
        assertEquals("Errors not handled well (column out of range)", 0, clues.getClue(IODirection.OUTWARDS,-1, 0));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClue(IODirection.OUTWARDS,0, 10));
        assertEquals("Errors not handled well (index out of range)", 0, clues.getClue(IODirection.OUTWARDS,0, -1));
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
        checkIterAgainstArray(clues, clues.getClues(IODirection.INWARDS,0));

        clues.setCurrCol(1);
        checkIterAgainstArray(clues, clues.getClues(IODirection.INWARDS,1));

        clues.setCurrCol(0);
        clues.setIterDirection(IODirection.OUTWARDS);
        checkIterAgainstArray(clues, clues.getClues(IODirection.OUTWARDS,0));

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

    @Test
    public void append(){
        Clues clues = new Clues(4, 2);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 2);
        clues.setClue(IODirection.OUTWARDS, 1, 0, 1);
        clues.setClue(IODirection.OUTWARDS, 1, 1, 3);

        //0: -2
        //1: 31
        //2: --
        //3: --

        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 0, 0));
        assertEquals(3, clues.getClue(1, 0));

        //Start:
        //-----Step1:
        clues.append(0, 1);
        clues.append(1, 4);
        clues.append(IODirection.OUTWARDS, 2, 2);
        clues.append(IODirection.INWARDS, 3, 2);

        //0:  21
        //1: 314
        //2:   2
        //3:   2
        // 0:
        assertEquals(2, clues.getClue(0, 0));
        assertEquals(1, clues.getClue(0, 1));
        // 1:
        assertEquals(3, clues.getClue(1, 0));
        assertEquals(1, clues.getClue(1, 1));
        assertEquals(4, clues.getClue(1, 2));
        //2:
        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 2, 0));
        assertEquals(2, clues.getClue(0, 0));
        //3:
        assertEquals(0, clues.getTrueClue(IODirection.OUTWARDS, 3, 1));
        assertEquals(2, clues.getClue(3, 0));

        //------Step2:
        clues.append(IODirection.OUTWARDS, 0, 3);
        clues.append(IODirection.OUTWARDS, 2, 3);

        //0: 321
        //1: 314
        //2:  32
        //3:   2

        //0:
        assertEquals(3, clues.getClue(0, 0));
        assertEquals(2, clues.getClue(IODirection.OUTWARDS, 0, 1));
        assertEquals(1, clues.getClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(0, clues.getClue(IODirection.OUTWARDS, 0, 3));

        //2:
        assertEquals(3, clues.getClue(2, 0));
        assertEquals(2, clues.getClue(2, 1));
        assertEquals(0, clues.getClue(2, 2));
        assertEquals(0, clues.getClue(IODirection.OUTWARDS, 2, 2));
    }

    @Test
    public void shiftOutwardsMultipleComplicated(){
        Clues clues = new Clues(1, 5);
        clues.setClue(IODirection.INWARDS, 0, 1, 3);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 2);
        clues.setClue(IODirection.OUTWARDS, 0, 1, 1);
        // -3-12
        clues.shift(IODirection.OUTWARDS, 0, 1);
        // -312-

        assertEquals(0, clues.getTrueClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(2, clues.getTrueClue(IODirection.OUTWARDS, 0, 1));
        assertEquals(1, clues.getTrueClue(IODirection.OUTWARDS, 0, 2));
        assertEquals(3, clues.getTrueClue(IODirection.OUTWARDS, 0, 3));
        assertEquals(0, clues.getTrueClue(IODirection.OUTWARDS, 0, 0));
    }

    @Test
    public void shiftInwardsMultipleComplicated(){
        Clues clues = new Clues(1, 5);
        clues.setClue(IODirection.INWARDS, 0, 0, 2);
        clues.setClue(IODirection.INWARDS, 0, 1, 1);
        clues.setClue(IODirection.INWARDS, 0, 3, 3);
        // 21-3-
        clues.shift(IODirection.INWARDS, 0,1);
        // -213-

        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 0, 0));
        assertEquals(2, clues.getTrueClue(IODirection.INWARDS, 0, 1));
        assertEquals(1, clues.getTrueClue(IODirection.INWARDS, 0, 2));
        assertEquals(3, clues.getTrueClue(IODirection.INWARDS, 0, 3));
        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 0, 4));

    }

    @Test
    public void shiftInwardsMultipleBasic(){
        Clues clues = new Clues(1, 5);
        clues.setClue(IODirection.INWARDS, 0, 1, 2);
        clues.setClue(IODirection.INWARDS, 0, 2, 1);
        // -21--

        clues.shift(IODirection.INWARDS,0, 1);
        assertFalse(clues.trueClue(IODirection.INWARDS, 0, 1));
        assertTrue(clues.trueClue(IODirection.INWARDS, 0, 2));
        assertTrue(clues.trueClue(IODirection.INWARDS, 0, 3));

        assertEquals(2, clues.getClue(IODirection.INWARDS, 0, 0));
        assertEquals(1, clues.getClue(IODirection.INWARDS, 0, 1));
    }

    @Test
    public void shiftOutwardsMultipleBasic(){
        Clues clues = new Clues(1, 4);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 1);
        clues.setClue(IODirection.OUTWARDS, 0, 1, 2);
        // --21
        clues.shift(IODirection.OUTWARDS, 0, 2);
        // 21--

        assertFalse(clues.trueClue(IODirection.OUTWARDS, 0, 0));
        assertFalse(clues.trueClue(IODirection.OUTWARDS, 0, 1));
        assertTrue(clues.trueClue(IODirection.OUTWARDS, 0, 2));
        assertTrue(clues.trueClue(IODirection.OUTWARDS, 0, 3));
        assertEquals(2, clues.getClue(0, 0));
        assertEquals(1, clues.getClue(0, 1));
    }

    @Test
    public void shiftInwardsResize(){
        Clues clues = new Clues(1, 3);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 1);
        // --1
        clues.shift(IODirection.INWARDS, 0, 1);
        // ---1
        assertEquals(4, clues.getIndexes(0));
        assertEquals(1, clues.getClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(0, clues.getClue(IODirection.OUTWARDS, 0, 1));
        assertFalse(clues.clue(IODirection.OUTWARDS, 0, 1));
    }

    @Test
    public void shiftOutwardsResize(){
        Clues clues = new Clues(1, 3);
        clues.setClue(IODirection.INWARDS, 0, 0, 1);
        //  1--
        clues.shift(IODirection.OUTWARDS, 0, 1);
        // 1---

        assertEquals(4, clues.getIndexes(0));
        assertEquals(1, clues.getClue(IODirection.INWARDS, 0, 0));
        assertFalse(clues.clue(0, 1));
    }

    private Clues getWrapClues(){
        Clues clues = new Clues(1, 3);
        clues.setClue(0, 0, 1);
        clues.setClue(IODirection.OUTWARDS, 0, 0, 2);
        return clues;
    }

    @Test
    public void shiftInwardsWrap(){
        Clues clues = getWrapClues();
        // 1-2
        clues.shift(IODirection.INWARDS, 0, 2);
        // --12

        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 0, 0));
        assertEquals(0, clues.getTrueClue(IODirection.INWARDS, 0, 1));
        assertEquals(1, clues.getTrueClue(IODirection.INWARDS, 0, 2));
        assertEquals(2, clues.getTrueClue(IODirection.INWARDS, 0, 3));
        assertEquals(4, clues.getIndexes(0));
    }

    @Test
    public void shiftOutwardsWrap(){
        Clues clues = getWrapClues();
        //  1-2
        clues.shift(IODirection.OUTWARDS, 0, 2);
        // 12--

        assertEquals(0, clues.getTrueClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(0, clues.getTrueClue(IODirection.OUTWARDS, 0, 1));
        assertEquals(2, clues.getTrueClue(IODirection.OUTWARDS, 0, 2));
        assertEquals(1, clues.getTrueClue(IODirection.OUTWARDS, 0, 3));
        assertEquals(4, clues.getIndexes(0));

    }

    @Test
    public void shiftBasic(){
        Clues clues = new Clues(1, 5);

        clues.setClue(IODirection.OUTWARDS, 0, 0, 1);
        // ----1

        //Begin
        //-------OUTWARDS
        clues.shift(IODirection.OUTWARDS, 0, 1);
        // ---1-

        assertFalse(clues.trueClue(0, 0));
        assertEquals(1, clues.getClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(1, clues.getTrueClue(IODirection.OUTWARDS, 0, 1));
        assertFalse(clues.clue(0, 1));

        clues.shift(IODirection.OUTWARDS, 0, 3);
        // 1----
        for (int i = 0; i < 4; i++) {
            assertFalse("There was a value at: " + i, clues.trueClue(IODirection.OUTWARDS, 0, i));
        }
        assertTrue(clues.clue(0, 0));
        assertEquals(1, clues.getClue(IODirection.INWARDS, 0, 0));

        //-------INWARDS
        clues.shift(IODirection.INWARDS, 0, 3);
        // ---1-

        assertFalse(clues.trueClue(0, 0));
        assertEquals(1, clues.getClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(1, clues.getTrueClue(IODirection.OUTWARDS, 0, 1));
        assertFalse(clues.clue(0, 1));

        clues.shift(IODirection.INWARDS, 0, 1);
        // ----1

        assertTrue(clues.trueClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(1, clues.getTrueClue(IODirection.OUTWARDS, 0, 0));
        assertEquals(0, clues.getClue(IODirection.OUTWARDS, 0, 1));
    }
}