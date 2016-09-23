/**
 * Graphics/Game wrapper
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
}