import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hermes235 on 23.9.2016.
 */
public class Window {

    private JFrame frame;
    private Application application;
    private int cellSize = 1;
    private int totalWidth=0, totalHeight = 0;
    private float scale = 1;

    public Window(Application application) {
        this.application = application;
        frame = new JFrame("Katana");
        frame.setSize(1024,860);
        frame.setName("Katana");
        frame.requestFocus();
        Component mouseClick = new MouseComponent(this);
        frame.addMouseListener((MouseListener) mouseClick);
        //Temp:
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setJMenuBar(createMenuBar());
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu menuGame = new JMenu("Game");
        menubar.add(menuGame);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuGame.add(menuExit);

        class ExitAction implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                exitClicked();
            }
        }

        menuExit.addActionListener(new ExitAction());

        return menubar;
    }

    /**
     * Called when user attempts to close the Frame
     */
    private void exitClicked() {
        if (application.isGameRunning()){
            //TODO: Add save dialog

            application.close();
        } else {
            application.close();
        }
    }

    /**
     * @return width of the frame
     */
    public int getWidth(){
        return frame.getWidth();
    }

    /**
     * @return height of the frame
     */
    public int getHeight(){
        return frame.getHeight();
    }



    private class MouseComponent extends JComponent implements MouseListener {
        public MouseComponent(Window window) {
            super();
        }


        public void mouseClicked(MouseEvent arg0){
            System.out.println("Click! " + arg0.getX() + ", " + arg0.getY());

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Draws the grid on the window
     */
    public void draw(){
        if (!application.isGameRunning()) return;

        //region TODO: if performance problems occur, try to optionalize this section
        if (!application.isGameRunning()) return;

            Vect2D<Integer> borderOffset = new Vect2D<Integer>(100, 100);

            //Calculate cellSizeFloat
            int gridWidth = application.getGridWidth();
            int gridHeight = application.getGridHeight();
            int columnHeight = application.getColumnCluesMaxHeight();
            int rowWidth = application.getRowCluesMaxHeight();

            totalWidth = gridWidth + rowWidth;
            totalHeight = gridHeight + columnHeight;

            int windowWidth = frame.getWidth();
            int windowHeight = frame.getHeight();

            int widthPixelsFree = windowWidth - 2 * borderOffset.getX();
            int heightPixelsFree = windowHeight - 2 * borderOffset.getY();

            float cellSizeWidth = widthPixelsFree / totalWidth;
            float cellSizeHeight = heightPixelsFree / totalHeight;

        float cellSizeFloat = Math.min(cellSizeWidth, cellSizeHeight);
            cellSizeFloat *= scale;
            cellSize = (int) Math.floor(cellSizeFloat);

        //endregion

        for (int y = 0; y < totalHeight; y++) {
            int drawY = borderOffset.getY() + y * cellSize;
            for (int x = 0; x < totalWidth; x++) {
                int drawX = borderOffset.getX() + x * cellSize;
                if (x > rowWidth && y > columnHeight){
                    //Draw grid
                    int gridX = x - rowWidth;
                    int gridY = y - columnHeight;

                    CellState cell = application.getGrid(gridX, gridY);

                    switch (cell){
                        case BLACK:
                            drawBlack(drawX, drawY, cellSize);
                            break;
                        case WHITE:
                            drawWhite(drawX, drawY, cellSize);
                            break;
                        case DOT:
                            drawDot(drawX, drawY    , cellSize);
                            break;
                        case CROSS:
                            drawCross(drawX, drawY, cellSize);
                            break;
                    }

                } else if (x <= rowWidth && y > columnHeight){
                    //Draw row clues
                    int row = y - columnHeight;
                    drawNumber(drawX, drawY, cellSize, application.getRowClue(row, rowWidth - x));

                } else if (x > rowWidth && y <= columnHeight){
                    //Draw column clues
                    drawNumber(drawX, drawY, cellSize, application.getColumnClue(x - rowWidth, columnHeight - y));
                    
                }
            }
        }



    }

    /**
     * Draws specified number to the specified position
     */
    private void drawNumber(int x, int y, int size, int number){
        //TODO: COMPLETE
    }

    /**
     * Draws black square of specified size to the specified position
     */
    private void drawBlack(int x, int y, int size){
       //TODO: COMPLETE
    }

    /**
     * Draws cross of specified size to the specified position
     */
    private void drawCross(int x, int y, int size){
        //TODO: COMPLETE
    }

    /**
     * Draws cross of specified size to the specified position
     */
    private void drawDot(int x, int y, int size){
        //TODO: COMPLETE
    }

    /**
     * Draws cross of specified size to the specified position
     */
    private void drawWhite(int x, int y, int size){
        //TODO: COMPLETE
    }

}