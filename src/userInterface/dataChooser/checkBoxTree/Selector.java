package userInterface.dataChooser.checkBoxTree;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;

/**
 * This class allows to define how the mouse is used to select elements of the
 * tree
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Selector implements MouseListener {
    /**
     * It receives a reference to the tree so that it can mark the selection in it
     */
    CheckBoxTree tree;
    /** Stores how many uncheckable levels there are in the beginning of the tree */
    int uncheckableLevelsNb;

    // Just creates an object of this class passing it a reference to the tree
    public Selector(CheckBoxTree tree, int uncheckableLevelsNb) {
        this.tree = tree;
        this.uncheckableLevelsNb = uncheckableLevelsNb;
    }

    // Specifies what happens upon a mouse click
    public void mouseClicked(MouseEvent arg0) {
        // gets the location of the click on the tree
        TreePath tp = tree.getPathForLocation(arg0.getX(), arg0.getY());
        // if the click was not on a valid element of the tree ignores it
        if (tp == null) {
            return;
        }
        // if the selected level is above the uncheckable levels
        // since we do not consider the root node in uncheckableLevelsNb but the method
        // getPathCount considers it we have to add 1
        if (tp.getPathCount() > this.uncheckableLevelsNb + 1) {
            // we want to toggle the state of the node so we will impose it to be the
            // opposite of what it is now
            this.tree.checkNode(tp, !tree.nodesState.get(tp));
        }
    }

    // These are the other methods the MouseEvent interface forces us to implement
    // but we do not want to specify any action for them

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }
}
