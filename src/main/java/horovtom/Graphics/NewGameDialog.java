package horovtom.Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lactosis on 29.9.16.
 */
public class NewGameDialog extends JFrame {
    private final Window windowReference;
    private JPanel panelMain;
    private JTextArea widthTextArea;
    private JTextArea heightTextArea;
    private JTextArea columnCluesTextArea;
    private JTextArea rowCluesTextArea;
    private JButton btnCreate;
    private JButton btnCancel;
    private JFormattedTextField txtWidth;
    private JFormattedTextField txtHeight;
    private JFormattedTextField txtColumnClues;
    private JFormattedTextField txtRowClues;

    public NewGameDialog(final Window windowReference) {
        super("New Game");
        setContentPane(panelMain);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        requestFocus();
        setResizable(false);

        this.windowReference = windowReference;
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isFormatRight()) return;
                windowReference.createNewGame(Integer.parseInt(txtWidth.getText()),
                        Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtColumnClues.getText()),
                        Integer.parseInt(txtRowClues.getText()));

                windowReference.draw();
                closeThisWindow();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeThisWindow();
            }
        });

    }

    public void closeThisWindow(){
        setVisible(false);
        dispose();
    }

    @Deprecated
    public NewGameDialog() {
        super("New Game");
        setContentPane(panelMain);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        requestFocus();
        setResizable(false);

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isFormatRight()) return;
                windowReference.createNewGame(Integer.parseInt(txtWidth.getText()),
                        Integer.parseInt(txtHeight.getText()),
                        Integer.parseInt(txtColumnClues.getText()),
                        Integer.parseInt(txtRowClues.getText()));
                windowReference.draw();
                closeThisWindow();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeThisWindow();
            }
        });
        this.windowReference = null;
    }

    public JPanel getPanel(){return panelMain;}

    /**
     * Deletes if any text is non-numeric.
     * @return false if it deleted anything
     */
    private boolean isFormatRight() {
        return !(deleteNonNumbers(txtWidth) ||
                deleteNonNumbers(txtColumnClues) ||
                deleteNonNumbers(txtHeight) ||
                deleteNonNumbers(txtRowClues))
                && isTextInRange(txtWidth, 5, 100)
                && isTextInRange(txtHeight, 5, 100)
                && isTextInRange(txtColumnClues, 0, 20)
                && isTextInRange(txtRowClues, 0, 20);

    }

    private boolean isTextInRange(JFormattedTextField field, int minim, int maxim) {
        String text = field.getText();
        int value = Integer.parseInt(text);
        if (value < minim){
            field.setText(String.valueOf(minim));
            return false;
        } else if (value > maxim){
            field.setText(String.valueOf(maxim));
            return false;
        }
        return true;
    }

    /**
     * Deletes if text is non-numeric.
     * @return true if it deleted anything
     */
    private boolean deleteNonNumbers(JFormattedTextField field){
        if (field.getText().length() > 0){
            if (!field.getText().matches("[0-9]+")){
                field.setText("0");
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Karel");
        frame.setVisible(true);
        NewGameDialog dialog = new NewGameDialog();

    }
}
