import com.sun.rowset.internal.Row;
import org.junit.Test;

import javax.swing.text.html.HTMLDocument;

import static org.junit.Assert.assertEquals;

/**
 * Created by lactosis on 14.9.16.
 */
public class GridTest {

    /**
     * Returns basic grid, which looks like this:
     * <p>
     * \\|1----
     * \\|232--
     * --|-----
     * 12|■■□□□
     * -3|··□□□
     * -2|××□□□
     * --|□□□□□
     * -1|□□□□□
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

        //Set Clues
        //-------Cols
        grid.setClue(ClueType.COLUMN, 0, 0, 2);
        grid.setClue(ClueType.COLUMN, 0, 1, 1);
        grid.setClue(ClueType.COLUMN, 1, 0, 3);
        grid.setClue(ClueType.COLUMN, 2, 0, 2);
        grid.setClue(ClueType.COLUMN, 2, 1, 0);

        //--------Rows
        grid.setClue(ClueType.ROW, 0, 0, 2);
        grid.setClue(ClueType.ROW, 0, 1, 1);
        grid.setClue(ClueType.ROW, 1, 0, 3);
        grid.setClue(ClueType.ROW, 2, 1, 2);
        grid.setClue(ClueType.ROW, 4, 0, 1);
        return grid;
    }

    Grid grid;

    @Test
    public void getClue() throws Exception {
        grid = getBasicGrid();
        assertEquals(1, grid.getClue(ClueType.COLUMN, 0, 1));
        assertEquals(2, grid.getClue(ClueType.COLUMN, 0, 0));
        assertEquals(3, grid.getClue(ClueType.COLUMN, 1, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN, 1, 1));
        assertEquals(2, grid.getClue(ClueType.COLUMN, 2, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN, 3, 0));
        assertEquals(0, grid.getClue(ClueType.COLUMN, 4, 0));

        assertEquals(1, grid.getClue(ClueType.ROW, 0, 1));
        assertEquals(2, grid.getClue(ClueType.ROW, 0, 0));
        assertEquals(3, grid.getClue(ClueType.ROW, 1, 0));
        assertEquals(0, grid.getClue(ClueType.ROW, 1, 1));
        assertEquals(2, grid.getClue(ClueType.ROW, 2, 0));
        assertEquals(0, grid.getClue(ClueType.ROW, 3, 0));
        assertEquals(1, grid.getClue(ClueType.ROW, 4, 0));
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


    //TODO: IMPLEMENT IT FOR THE IO TYPES
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
        Grid grid = new Grid(2,2,2,3);

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

    @Test
    public  void isCompleted() throws  Exception{
        Grid grid = new Grid(4, 4, 2,2);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 0, 0, 2);
        grid.setClue(ClueType.COLUMN, IODirection.OUTWARDS, 1, 0, 1);


    }

}