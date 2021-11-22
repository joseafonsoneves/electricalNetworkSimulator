package userInterface.dataChooser.checkBoxTree;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * This class is a renderer of leaves for a tree based on a check box. Basically
 * it has all the functionalities of a check box but also may be render as a
 * leaf in a tree
 */
public class CheckBoxRenderer extends JCheckBox implements TreeCellRenderer {
    // Saves a reference to the map of nodes so that it can consult it to know
    // wether or not they are selected
    HashMap<TreePath, Boolean> nodesState;

    // Constructor of the class that takes the reference to the map to be able to
    // use when returning the check box to the renderer
    public CheckBoxRenderer(HashMap<TreePath, Boolean> nodesState) {
        this.nodesState = nodesState;
    }

    /**
     * Since it will be basically a check box we only need to implement how it is
     * rendered on the tree and we do it through the interface created
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        TreePath tp = new TreePath(node.getPath());

        // a checkbox is selected if the corresponding node is selected
        this.setSelected(nodesState.get(tp));
        // gets the name of the profile to put in front of the check box
        this.setText(node.toString());
        // no check box is to have an opaque background
        this.setOpaque(false);

        // it returns itself (a checkbox) as leaf of the tree
        return this;
    }
}
