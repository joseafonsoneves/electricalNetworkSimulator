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
    /** Reference to the tree which it uses to get the wanted profiles */
    private CheckBoxTree tree;
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
    public ButtonController(CheckBoxTree tree, DataChooser frame) {
        this.tree = tree;
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
                // passes to the dialog the checked paths in the tree
                dialog.setCheckedPaths(this.tree.getCheckedPaths());
                // hides the data chooser frame
                dialog.setVisible(false);
                // and then kills it
                dialog.dispose();
                break;
        }
    }
}
