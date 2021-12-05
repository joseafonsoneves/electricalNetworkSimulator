package integration;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import java.util.HashMap;
import extension3.UIModel;
import extension3.dataChooser.DataChooser;

/**
 * Class to allow the integration of different extensions with the user
 * interface extension. It extends the DataChooser extension so that it is
 * easy to do the integration
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class DataChooserIntegration extends DataChooser {
    /**
     * Presents the constructor but it does not change
     * 
     * @param owner parent frame
     * @param model to implement
     */
    public DataChooserIntegration(JFrame owner, UIModelIntegration uiModel) {
        super(owner, uiModel);
    }

    /**
     * Gets the tree nodes of the tree to present to the user starting always from
     * the model. Just as presented before this method is very useful to the
     * integration
     * 
     * @param uiModel model to convert to nodes
     * @return root node that represents the tree
     */
    @Override
    protected DefaultMutableTreeNode getTreeNodes(UIModel uiModel) {
        // gets the nodes from the cities
        DefaultMutableTreeNode root = super.addProfilesNodes(new DefaultMutableTreeNode("Root"), uiModel.getCities());
        // and adds them to the ones of the losses if the losses were already defined
        if (((UIModelIntegration) uiModel).hasLosses()) {
            root = this.addLosses(root, ((UIModelIntegration) uiModel).getLosses());
        }

        return root;
    }

    /**
     * This method receives a root node and a map of losses and adds the losses to
     * the root node
     * 
     * @param root   root node of the tree to which add the new nodes
     * @param losses map of losses to add to the tree
     */
    protected DefaultMutableTreeNode addLosses(DefaultMutableTreeNode root, HashMap<String, double[]> losses) {
        if (losses != null) {
            // creates the node of losses
            DefaultMutableTreeNode lossesNode = new DefaultMutableTreeNode("Losses");
            // connects it to the root
            root.add(lossesNode);
            // for each loss in the losses map
            for (String lossId : losses.keySet()) {
                // adds a node to the tree
                lossesNode.add(new DefaultMutableTreeNode(lossId));
            }
            // and then sends the nodes out
            return root;
        } else {
            return null;
        }
    }
}
