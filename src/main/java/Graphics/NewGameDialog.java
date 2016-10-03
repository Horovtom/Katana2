package Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lactosis on 29.9.16.
 */
public class NewGameDialog {
    private JPanel panelMain;
    private JTextArea widthTextArea;
    private JTextField txtWidth;
    private JTextArea heightTextArea;
    private JTextField txtHeight;
    private JTextArea columnCluesTextArea;
    private JTextArea rowCluesTextArea;
    private JTextField txtColClues;
    private JTextField txtRowClues;
    private JButton btnCreate;
    private JButton btnCancel;

    public NewGameDialog() {
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Create create
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Create back
            }
        });
    }
}
