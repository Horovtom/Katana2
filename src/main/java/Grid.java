import java.util.logging.Logger;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class Grid {
    private static final Logger LOGGER = Logger.getLogger(Grid.class.getName());

    private final Clues columns, rows;
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

    /**
     * Returns the clue at specified position
     * INWARDS
     */
    public int getClue(ClueType type, int column, int index){
        return type == ClueType.COLUMN ? columns.getClue(IODirection.INWARDS,column, index) : rows.getClue(IODirection.INWARDS,column, index);
    }

    /**
     * Returns the clue at specified position
     */
    public int getClue(ClueType type, IODirection dir, int column, int index){
        return type == ClueType.COLUMN ? columns.getClue(dir, column, index) : rows.getClue(dir, column, index);
    }


    public int getClueColumn(int column, int index){
        return getClue(ClueType.COLUMN, column, index);
    }

    public int getClueRow(int column, int index){
        return getClue(ClueType.ROW, column, index);
    }

    public Clues getClues(ClueType type){
        return type == ClueType.COLUMN ? columns : rows;
    }

    /**
     * Sets clue in specified ClueType in INWARDS direction
     */
    public void setClue(ClueType type, int column, int index, int value){
        Clues clue = (type == ClueType.ROW ? rows: columns);
        clue.setClue(IODirection.INWARDS, column, index, value);
    }

    /**
     * Sets clue in specified ClueType and Direction
     */
    public void setClue(ClueType type, IODirection dir, int column, int index, int value){
        Clues clue = (type == ClueType.ROW ? rows: columns);
        clue.setClue(dir, column, index, value);
    }

    public void setClueColumn(int column, int index, int value){
        setClue(ClueType.COLUMN,column, index, value);
    }

    public void setClueRow(int column, int index, int value){
        setClue(ClueType.ROW, column, index,value);
    }


    public void setCell(int row, int column, CellState type){
        LOGGER.fine("Setting cell on: " + row + ", "  + column + " from " + grid[row][column] + " to " + type);
        grid[row][column] = type    ;
    }

    /**
     * Returns the current CellState of specified cell on the grid
     */
    public CellState getCell(int row, int column){
        return grid[row][column];
    }

    /**
     * Erases all Cells
     */
    public void eraseGrid(){
        LOGGER.info("Setting the whole grid to " + CellState.WHITE + "!");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = CellState.WHITE;
            }
        }
    }

    /**
     * Resets everything to blank state
     */
    public void resetGrid(){
        eraseGrid();
        rows.clearClues();
        columns.clearClues();
    }

    public boolean isCompleted(){


    }
}