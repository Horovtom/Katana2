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
}
