package extension3.dataChooser.checkBoxTree;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * This class allows the creation of a tree based on the JTree with checkboxes
 * as leaves. There is some particular features like when you choose one parent
 * leaf the children ones are not selected. The created objects are Components
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class CheckBoxTree extends JTree {
    /** List of nodes in function of their tree path */
    HashMap<TreePath, Boolean> nodesState;
    /** List of every path that is checked */
    ArrayList<TreePath> checkedPaths;

    /**
     * Constructor of the tree. The real creation of the data is hidden within the
     * setModel method since it will be used by the JTree.
     * 
     * @param root receives the root to a node of a tree
     */
    public CheckBoxTree(DefaultMutableTreeNode root, int uncheckableLevelsNb) {
        super(root);
        // Disabling toggling by double-click because it could lead to users toggling
        // the group when selecting it
        this.setToggleClickCount(0);
        // Overriding cell renderer by new one defined above
        this.setCellRenderer(new CheckBoxRenderer(this.nodesState));
        // Create checking mechanism on mouse click and says how many of the first
        // levels are uncheckable
        this.addMouseListener(new Selector(this, uncheckableLevelsNb));
    }

    /**
     * This is a method of the JTree class that has to be rewritten to impose the
     * model we want for the tree
     * 
     * @param newModel model that will be used by the JTree class
     */
    public void setModel(TreeModel newModel) {
        // the model implemented by the tree class has to be called
        super.setModel(newModel);

        // creates the nodes and the checked paths
        nodesState = new HashMap<TreePath, Boolean>();
        checkedPaths = new ArrayList<TreePath>();

        // gets the root of the current model of the tree
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getModel().getRoot();
        if (node == null) {
            return;
        }
        // if the root is valid, adds the following nodes to the map of nodes
        addChildrenNodes(node);
    }

    /**
     * Adds a node and its children nodes of a node to the map of nodes
     * 
     * @param node node to be added to the map of nodes
     */
    private void addChildrenNodes(DefaultMutableTreeNode node) {
        // gets the corresponding tree path of the node
        TreePath tp = new TreePath(node.getPath());
        // each node is added non selected
        nodesState.put(tp, false);
        // for each child node
        for (int i = 0; i < node.getChildCount(); i++) {
            // it will be added and its children will be added as well
            addChildrenNodes((DefaultMutableTreeNode) tp.pathByAddingChild(node.getChildAt(i)).getLastPathComponent());
        }
    }

    /**
     * This method receives a path to an element on the tree and checks or unchecks
     * it
     * 
     * @param tp    path to the element on the tree
     * @param check boolean variable to check or not to check it
     */
    protected void checkNode(TreePath tp, boolean check) {
        // changes the state of the nodes
        nodesState.put(tp, check);
        // if it has been checked adds it to the list of selected paths
        if (check) {
            checkedPaths.add(tp);
        }
        // if it has been unchecked removes it from the list of selected paths
        else {
            checkedPaths.remove(tp);
        }
        // After a change the tree needs to be repainted to be in accordance to what was
        // changed
        this.repaint();
    }

    /**
     * Method that returns the list of checked paths as an array
     */
    public TreePath[] getCheckedPaths() {
        return checkedPaths.toArray(new TreePath[checkedPaths.size()]);
    }

    /**
     * Expands every node of the tree so the user has not got to
     */
    public void expandAllNodes() {
        int numRows = this.getRowCount();

        // before the end of the tree
        for (int row = 0; row < numRows; row++) {
            // expands every row
            this.expandRow(row);
            // which can create more rows so updates the number of rows and goes for the
            // next one
            numRows = this.getRowCount();
        }
    }
}