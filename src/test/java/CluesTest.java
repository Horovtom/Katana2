import org.junit.Test;

import java.io.OutputStreamWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class CluesTest {

    /**
     * Returns a Clues object which looks like this:
     * <p>
     * <p>
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
    public void getCluesOutwards() throws Exception {
        //TODO: COMPLETE

    }

    @Test
    public void getClueInwards() throws Exception {
        //TODO: COMPLETE

    }

    @Test
    public void getClue() throws Exception {
        //TODO: COMPLETE

    }

    @Test
    public void getClueOutwards() throws Exception {
        //TODO: COMPLETE

    }

    @Test
    public void setCurrCol() throws Exception {

        //TODO: COMPLETE
    }

    @Test
    public void setIterDirection() throws Exception {
        //TODO: COMPLETE

    }

    @Test
    public void getCluesInwards() throws Exception {
        //TODO: COMPLETE

    }

//    //Test Clues consist of (horizontally):
//    // 0   0   0
//    // 1   2   3
//    // 1   0   2
//    // 0   1   2
//    // 1   2   0
//    Clues testClues;
//
//    @org.junit.Before
//    public void setUp() throws Exception {
//        testClues = new Clues(5, 3);
//        int[] array = new int[]{0, 0, 0};
//        testClues.setCluesInwards(0, array);
//        array = new int[]{3, 2, 1};
//        testClues.setCluesInwards(1, array);
//        array = new int[]{2, 0, 1};
//        testClues.setCluesInwards(2, array);
//        array = new int[]{2, 1, 0};
//        testClues.setCluesInwards(3, array);
//        array = new int[]{0, 2, 1};
//        testClues.setCluesInwards(4, array);
//    }
//
//    @org.junit.Test
//    public void checkOrder() throws Exception {
//        //Set clues:
//        //
//        // --|-----
//        // 21|-----
//        // 12|-----
//
//        // -     -     |-----
//        // [0, 0][0, 1]|-----
//        // [1, 0][1, 1]|-----
//        Clues clues = new Clues(2, 2);
//
//
//        // By indexes:
//        // ------ Set up
//        clues.setClueInwards(0, 0, 2);
//        clues.setClueInwards(0, 1, 1);
//        clues.setClueInwards(1, 0, 1);
//        clues.setClueInwards(1, 1, 2);
//
//        // ------ Check
//        //TODO: COMPLETE
//
//    }
//
//    /**
//     * Meant to be used with checkOrder()
//     */
//    private void checkByIndexes(Clues clues) throws Exception {
//        assertEquals(1, clues.getClueInwards(0, 1));
//        assertEquals(2, clues.getClueInwards(0, 0));
//        assertEquals(2, clues.getClueInwards(1, 1));
//        assertEquals(1, clues.getClueInwards(1, 0));
//    }
//
//    /**
//     * Meant to be used with checkOrder()
//     */
//    private void checkByLoop(Clues clues) throws Exception {
//        int[] array = new int[2];
//        clues.setCurrCol(0);
//        int counter = 0;
//        for (int i :
//                clues) {
//            array[counter] = i;
//            counter++;
//        }
//        assertEquals(2, array[0]);
//        assertEquals(1, array[1]);
//
//        clues.setCurrCol(1);
//        counter = 0;
//        for (int i :
//                clues) {
//            array[counter] = i;
//            counter++;
//        }
//
//        assertEquals(1, array[0]);
//        assertEquals(2, array[1]);
//    }
//
//    @org.junit.Test
//    public void clearClues() throws Exception {
//        setUp();
//        assertEquals(1, testClues.getClueInwards(1, 0));
//        testClues.clearClues();
//        assertEquals(0, testClues.getClueInwards(1, 0));
//        assertEquals(0, testClues.getClueInwards(2, 0));
//        assertEquals(0, testClues.getClueInwards(4, 2));
//    }
//
//    @org.junit.Test
//    public void setClueReversed() throws Exception {
//        setUp();
//        assertEquals(3, testClues.getClueOutwards(1, 0));
//        testClues.setClueOutwards(1, 0, 6);
//        assertEquals(6, testClues.getClueOutwards(1, 0));
//        testClues.setClueOutwards(1, 1, 10);
//        assertEquals(10, testClues.getClueInwards(1, 1));
//        testClues.setClueOutwards(1, 2, 7);
//        assertEquals(7, testClues.getClueOutwards(1, 2));
//
//    }
//
//    @org.junit.Test
//    public void setClue() throws Exception {
//        setUp();
//        testClues.setClueInwards(4, 0, 5);
//        testClues.setClueInwards(4, 1, 4);
//        testClues.setClueInwards(4, 2, 10);
//        assertEquals(5, testClues.getClueInwards(4, 0));
//        assertEquals(4, testClues.getClueInwards(4, 1));
//        assertEquals(10, testClues.getClueOutwards(4, 0));
//    }
//
//    @org.junit.Test
//    public void getClues() throws Exception {
//        setUp();
//        int[] clues = testClues.getCluesOutwards(1);
//        assertEquals(3, clues[2]);
//        assertEquals(2, clues[1]);
//        assertEquals(1, clues[0]);
//    }
//
//    @org.junit.Test
//    public void getCluesReversed() throws Exception {
//        setUp();
//        int[] clues = testClues.getCluesInwards(1);
//        assertEquals(1, clues[2]);
//        assertEquals(2, clues[1]);
//        assertEquals(3, clues[0]);
//    }
//
//    @org.junit.Test
//    public void testIterable() throws Exception {
//        setUp();
//        testClues.setCurrCol(1);
//        int i = 1;
//        for (int number :
//                testClues) {
//            assertEquals(i, number);
//
//            i++;
//        }
//    }
//
//    @Test
//    public void align() throws Exception {
//        setUp();
//        testClues.align();
//        assertEquals(0, testClues.getClueInwards(0, 0));
//        assertEquals(0, testClues.getClueInwards(0, 1));
//        assertEquals(0, testClues.getClueInwards(0, 2));
//
//        assertEquals(2, testClues.getClueInwards(1, 1));
//        assertEquals(3, testClues.getClueInwards(1, 2));
//
//        assertEquals(1, testClues.getClueInwards(2, 0));
//        assertEquals(2, testClues.getClueInwards(2, 1));
//
//        assertEquals(1, testClues.getClueInwards(3, 0));
//        assertEquals(2, testClues.getClueInwards(3, 1));
//
//        assertEquals(1, testClues.getClueInwards(4, 0));
//        assertEquals(2, testClues.getClueInwards(4, 1));
//    }
}