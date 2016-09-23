

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
}