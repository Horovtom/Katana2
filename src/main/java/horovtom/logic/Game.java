package horovtom.logic;

/**
 * horovtom.graphics/horovtom.Game wrapper
 */
public class Game {
    Grid grid;
    Application application;

    public Game(Application application, int width, int height, int columnClues, int rowClues) {
        grid = new Grid(width, height, columnClues, rowClues);
        this.application = application;
    }

    /**
     * @return the width of GRID of Cels ({@linkplain CellState})
     */
    public int getGridWidth(){
        return grid.getGridWidth();
    }

    /**
     * @return the height of GRID of Cels ({@linkplain CellState})
     */
    public int getGridHeight(){
        return grid.getGridHeight();
    }

    /**
     * @return the maximum height of column clues
     */
    public int getColumnCluesMaxHeight() {
        return grid.getColumnCluesMaxHeight();
    }

    /**
     * @return the maximum height of row clues
     */
    public int getRowCluesMaxHeight(){
        return grid.getRowCluesMaxHeight();
    }

    public CellState getGrid(int x, int y) {
        return grid.getCell(y, x);
    }

    /**
     * @return clue on specified position indexed OUTWARDS (skipping).
     */
    public int getColumnClue(int column, int index) {
        return grid.getClue(ClueType.COLUMN,IODirection.OUTWARDS, column, index);
    }

    /**
     * @return clue on speicified position indexed OUTWARDS (skipping)
     */
    public int getRowClue(int row, int index){
        return grid.getClue(ClueType.ROW, IODirection.OUTWARDS, row, index);
    }
}