package userInterface.dataChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import userInterface.dataChooser.checkBoxTree.CheckBoxTree;

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
     * Reference to the tree of profiles
     * 
     * @param frame
     */
    private CheckBoxTree tree;

    /**
     * Saves the reference to the tree so that it can be used upon click
     * 
     * @param tree reference to the tree to use
     */
    public ButtonController(DataChooser frame, CheckBoxTree tree) {
        this.dialog = frame;
        this.tree = tree;
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
                dialog.setValidatedPaths(this.tree.getCheckedPaths());
                // hides the data chooser frame
                dialog.setVisible(false);
                // and then kills it
                dialog.dispose();
                break;
        }
    }
}
