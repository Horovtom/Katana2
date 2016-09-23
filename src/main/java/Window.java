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

        Vect2D<Integer> borderOffset = new Vect2D<Integer>(100, 100);

        //Calculate cellSize
        int gridWidth = application.getGridWidth();
        int gridHeight = application.getGridHeight();
        int columnHeight = application.getColumnCluesMaxHeight();
        int rowHeight = application.getRowCluesMaxHeight();

        int totalTableWidth = gridWidth + rowHeight;
        int totalTableHeight = gridHeight + columnHeight;

        //TODO: COMPLETE (Continue here)

    }

}
