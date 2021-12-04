package integration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import java.util.HashMap;
import simulator.City;
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
     * Creates a data tree out of the profiles present in a map of cities
     * 
     * @param cities map of cities whose profiles one wants to present
     * @param losses map of losses one wants to present
     */
    public DataChooserIntegration(JFrame owner, HashMap<String, City> cities, HashMap<String, double[]> losses) {
        // uses a simple constructor from the parent
        super(owner);

        // if the city was not yet added
        if (cities == null || cities.size() == 0) {
            JLabel warning = new JLabel("Warning: No city yet added", SwingConstants.CENTER);
            this.add(warning);
        } else {
            // gets the nodes from the cities
            DefaultMutableTreeNode root = super.addProfiles(new DefaultMutableTreeNode("Root"), cities);
            // and adds them to the ones of the losses if the losses were already defined
            if (losses != null) {
                root = this.addLosses(root, losses);
            }
            // creates the layout of the window with the tree with the nodes specified
            super.createLayout(root);
        }
    }

    /**
     * This method receives a root node and a map of losses and adds the losses to
     * the root node
     * 
     * @param root   root node of the tree to which add the new nodes
     * @param losses map of losses to add to the tree
     */
    private DefaultMutableTreeNode addLosses(DefaultMutableTreeNode root, HashMap<String, double[]> losses) {
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
