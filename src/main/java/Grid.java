/**
 * Created by Hermes235 on 9.9.2016.
 */
public class Grid {
    Clues columns, rows;
    private CellState[][] grid;

    public Grid(int width, int height) {
        grid = new CellState[width][height];
        columns = new Clues(width, 2);
        rows = new Clues(height, 2);
    }

    public Grid(int width, int height, int columnClues, int rowClues) {
        grid = new CellState[width][height];
        columns = new Clues(width, columnClues);
        rows = new Clues(height, rowClues);
    }


}