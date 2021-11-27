package userInterface.dataChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controls the button implemented in the data chooser panel
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class ButtonController implements ActionListener {
    /**
     * Reference to the frame of the button so that it can close it upon selection
     * of the profiles
     */
    private DataChooser dialog;

    /**
     * Saves the reference to the tree so that it can be used upon click
     * 
     * @param tree reference to the tree to use
     */
    public ButtonController(DataChooser frame) {
        this.dialog = frame;
    }

    /**
     * Upon click on the Validate button saves the selected profiles and closes the
     * frame
     * 
     * @param e button click event
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            // when the Validate button is clicked
            case "Validate & Leave":
                // hides the data chooser frame
                dialog.setVisible(false);
                // and then kills it
                dialog.dispose();
                break;
        }
    }
}
