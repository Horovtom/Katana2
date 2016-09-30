import horovtom.CellState;
import horovtom.ClueType;
import horovtom.Grid;
import horovtom.IODirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lactosis on 14.9.16.
 */
public class GridTest {

    Grid grid;

    /**
     * <table rules="all">
     *   <tr>
     *       <th>0:</th> <th>1:</th> <th>2:</th> <th>3:</th> <th>4:</th> <th>5:</th> <th>6:</th>
     *   </tr>
     *   <tr>
     *      <td></td> <td></td> <td>1</td> <td></td> <td></td> <td></td> <td></td>
     *   </tr>
     *   <tr>
     *      <td></td> <td></td> <td>2</td> <td>3</td> <td>2</td> <td></td> <td></td>
     *   </tr>
     *   <tr>
     *      <td>1</td> <td>2</td> <td bgcolor="#000000"></td> <td bgcolor="#000000"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     *   </tr>
     *   <tr>
     *      <td></td> <td>3</td> <td>•</td> <td>•</td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     *   </tr>
     *   <tr>
     *      <td></td> <td>2</td> <td>X</td> <td>X</td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     *   </tr>
     *   <tr>
     *      <td></td> <td></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     *   </tr>
     *   <tr>
     *      <td></td> <td>1</td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td> <td bgcolor="#FFFFFF"></td>
     *   </tr>
     * </table>
     */
    private Grid getBasicGrid() {
        Grid grid = new Grid(5, 5, 2, 2);

        //Set grid
        grid.setCell(0, 0, CellState.BLACK);
        grid.setCell(0, 1, CellState.BLACK);
        grid.setCell(1, 1, CellState.DOT);
        grid.setCell(1, 0, CellState.DOT);
        grid.setCell(2, 0, CellState.CROSS);
        grid.setCell(2, 1, CellState.CROSS);

        //Set horovtom.Clues
        //-------Cols
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 0, 2);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 1, 1);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 1, 0, 3);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 2, 0, 2);

        //--------Rows
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 0, 0, 2);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 0, 1, 1);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 1, 0, 3);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 2, 0, 2);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 4, 0, 1);
        return grid;
    }

    @Test
    public void getClue() throws Exception {
        grid = getBasicGrid();
        assertEquals(1, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 0, 1));
        assertEquals(2, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 0, 0));
        assertEquals(3, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 1, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 1, 1));
        assertEquals(2, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 2, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 3, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, 4, 0));

        assertEquals(1, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 0, 1));
        assertEquals(2, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 0, 0));
        assertEquals(3, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 1, 0));
        assertEquals(0, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 1, 1));
        assertEquals(2, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 2, 0));
        assertEquals(0, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 3, 0));
        assertEquals(1, grid.getClue(ClueType.ROW,IODirection.OUTWARDS, 4, 0));
    }


    @Test
    public void getClueColumn() throws Exception {
        grid = getBasicGrid();
        assertEquals(0, grid.getClueColumn(4, 0));
    }

    @Test
    public void getClueRow() throws Exception {
        grid = getBasicGrid();
        assertEquals(1, grid.getClueRow(4, 0));
    }


    @Test
    public void getCluesIO() throws Exception {
        grid = getBasicGrid();
        assertEquals(1, grid.getClue(ClueType.COLUMN, IODirection.INWARDS, 0, 0));
        assertEquals(2, grid.getClue(ClueType.COLUMN, IODirection.INWARDS, 0, 1));
        assertEquals(3, grid.getClue(ClueType.COLUMN, IODirection.INWARDS, 1, 0));
        assertEquals(2, grid.getClue(ClueType.COLUMN, IODirection.INWARDS, 2, 0));
        assertEquals(1, grid.getClue(ClueType.ROW, IODirection.OUTWARDS, 4, 0));
    }

    @Test
    public void getClues() throws Exception {
        grid = getBasicGrid();
        int[] array = new int[2];
        int counter = 0;
        grid.getClues(ClueType.COLUMN).setCurrCol(0);
        for (int i :
                grid.getClues(ClueType.COLUMN)) {
            array[counter] = i;
            counter++;
        }
        assertEquals(2, counter);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);

        grid.getClues(ClueType.ROW).setCurrCol(4);
        counter = 0;

        for (int i :
                grid.getClues(ClueType.ROW)) {
            array[counter] = i;
            counter++;
        }
        assertEquals(1, counter);
        assertEquals(1, array[0]);
    }

    @Test
    public void setClue() throws Exception {
        Grid grid = new Grid(2, 2, 2, 3);

        grid.setClue(ClueType.COLUMN, IODirection.INWARDS, 0, 0, 2);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 0, 3);

        assertEquals(3, grid.getClue(ClueType.COLUMN, IODirection.INWARDS, 0, 1));
        assertEquals(2, grid.getClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 1));

        grid.setClue(ClueType.ROW, IODirection.INWARDS, 0, 0, 1);
        grid.setClue(ClueType.ROW, IODirection.INWARDS, 0, 0, 2);
        assertEquals(2, grid.getClue(ClueType.ROW, IODirection.INWARDS, 0, 0));
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 0, 2, 1);
        assertEquals(1, grid.getClue(ClueType.ROW, IODirection.OUTWARDS, 0, 0));
    }

    @Test
    public void setCell() throws Exception {
        Grid grid = new Grid(2, 2);
        grid.setCell(0, 0, CellState.BLACK);
        assertEquals(CellState.BLACK, grid.getCell(0, 0));
        grid.setCell(0, 1, CellState.DOT);
        assertEquals(CellState.DOT, grid.getCell(0, 1));
    }

    @Test
    public void eraseGrid() throws Exception {
        Grid grid = getBasicGrid();
        assertEquals(CellState.BLACK, grid.getCell(0, 0));
        grid.eraseGrid();
        assertEquals(CellState.WHITE, grid.getCell(0, 0));
    }

    /**
     * Uses:
     * <table summary="">
     * <tr>
     * <td></td><td></td><td></td><td></td><td>1</td><td>1</td>
     * </tr>
     * <tr>
     * <td></td><td></td><td>4</td><td>1</td><td>1</td><td>1</td>
     * </tr>
     * <tr>
     * <td></td><td>2</td><td bgcolor="#000000"></td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#FFFFFF"></td>
     * </tr>
     * <tr>
     * <td>1</td><td>2</td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td>
     * </tr>
     * <tr>
     * <td></td><td>1</td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#FFFFFF"></td>
     * </tr>
     * <tr>
     * <td>1</td><td>2</td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td>
     * </tr>
     * </table>
     *
     * @throws Exception
     */
    @Test
    public void isCompleted1() throws Exception {
        Grid grid = new Grid(4, 4, 2, 2);
        //  |--11
        //  |4111
        //--|----
        //-2|bbww
        //12|bwbb
        //-1|bwww
        //12|bwbb
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 0, 4);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 1, 0, 1);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 2, 0, 1);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 2, 1, 1);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 3, 0, 1);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 3, 1, 1);

        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 0, 0, 2);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 1, 0, 2);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 1, 1, 1);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 2, 0, 1);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 3, 0, 2);
        grid.setClue(ClueType.ROW, IODirection.OUTWARDS, 3, 1, 1);

        assertFalse(grid.isCompleted());
        //0:
        grid.setCell(0, 0, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(0, 1, CellState.BLACK);
        assertFalse(grid.isCompleted());
        //1:
        grid.setCell(1, 0, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 2, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 3, CellState.BLACK);
        assertFalse(grid.isCompleted());
        //2:
        grid.setCell(2, 0, CellState.BLACK);
        assertFalse(grid.isCompleted());

        //3:
        grid.setCell(3, 0, CellState.BLACK);

        assertFalse(grid.isCompleted());
        grid.setCell(3, 2, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(3, 3, CellState.BLACK);

        assertTrue(grid.isCompleted());
        grid.setCell(3, 3, CellState.WHITE);
        assertFalse(grid.isCompleted());

        grid.setCell(3, 2, CellState.WHITE);
        assertFalse(grid.isCompleted());
    }

    /**
     * Uses:
     * <table>
     * <tr>
     * <td></td><td></td><td></td><td>2</td><td>2</td><td>3</td><td>1</td><td>2</td>
     * </tr>
     * <tr>
     * <td>1</td><td>1</td><td>1</td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#000000"></td>
     * </tr>
     * <tr>
     * <td></td><td></td><td>5</td><td bgcolor="#000000"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td>
     * </tr>
     * <tr>
     * <td></td><td></td><td>2</td><td bgcolor="#FFFFFF"></td><td bgcolor="#000000"></td><td bgcolor="#000000"></td><td bgcolor="#FFFFFF"></td><td bgcolor="#FFFFFF"></td>
     * </tr>
     * </table>
     */
    @Test
    public void isCompleted2() {
        Grid grid = new Grid(5, 3, 1, 1);
        //ROWS:
        assertTrue(grid.isCompleted());
        grid.appendClue(ClueType.ROW, IODirection.INWARDS, 0, 1);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.ROW, IODirection.INWARDS, 0, 1);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.ROW, IODirection.INWARDS, 0, 1);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.ROW, IODirection.INWARDS, 1, 5);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.ROW, IODirection.INWARDS, 2, 2);
        assertFalse(grid.isCompleted());

        //COLS:
        grid.appendClue(ClueType.COLUMN, IODirection.INWARDS, 0, 2);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.COLUMN, IODirection.INWARDS, 1, 2);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.COLUMN, IODirection.INWARDS, 2, 3);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.COLUMN, IODirection.INWARDS, 3, 1);
        assertFalse(grid.isCompleted());
        grid.appendClue(ClueType.COLUMN, IODirection.INWARDS, 4, 2);
        assertFalse(grid.isCompleted());

        //GRID:
        grid.setCell(0, 0, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(0, 4, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 0, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 4, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 3, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 1, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(2, 1, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(2, 2, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(1, 2, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(0, 2, CellState.BLACK);
        assertTrue(grid.isCompleted());
        grid.setCell(0, 1, CellState.CROSS);
        assertTrue(grid.isCompleted());
        grid.setCell(2, 4, CellState.BLACK);
        assertFalse(grid.isCompleted());
        grid.setCell(2, 4, CellState.DOT);
        assertTrue(grid.isCompleted());

    }
}