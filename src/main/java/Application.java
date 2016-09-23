/**
 * Created by Hermes235 on 23.9.2016.
 */
public class Application {
    Window window = null;
    Game game = null;

    public Application() {
        window = new Window(this);
    }

    public void createGame(int width, int height, int columnClues, int rowClues){
        game = new Game(this, width, height, columnClues, rowClues);
    }

    /**
     * @return whether there is a loaded game in the application´s memory
     */
    public boolean isGameRunning(){
        return game == null;
    }

    /**
     * Called when this application should be closed.
     */
    public void close() {
        System.exit(0);
    }

    public void saveGame(String path){
        //TODO: Implement saveGame
    }

    public void loadGame(String path){
        //TODO: Implement loadGame
    }

    /**
     * @return the maximum height of column clues
     */
    public int getColumnCluesMaxHeight() {
        if (!isGameRunning()) return 0;
        return game.getColumnCluesMaxHeight();
    }

    /**
     * @return the maximum height of row clues
     */
    public int getRowCluesMaxHeight(){
        if (!isGameRunning()) return 0;
        return game.getRowCluesMaxHeight();
    }

    /**
     * @return the width of GRID of Cels ({@linkplain CellState})
     */
    public int getGridWidth(){
        if (!isGameRunning()) return 0;
        return game.getGridWidth();
    }

    /**
     * @return the height of GRID of Cels ({@linkplain CellState})
     */
    public int getGridHeight(){
        if (!isGameRunning()) return 0;
        return game.getGridHeight();
    }


}
