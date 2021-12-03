package extension3.dataChooser;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import extension3.dataChooser.checkBoxTree.CheckBoxTree;

import java.awt.GridLayout;
import java.util.HashMap;

import profiles.Profile;
import profiles.ProfilesGroup;
import simulator.City;

public class DataChooser extends JDialog {
    /**
     * Proportion between the dimensions of the screen and the initial dimensions of
     * the frame
     */
    private static final double screenToFrame = 2;

    /**
     * Validated paths. There is a difference between the paths that were selected
     * and the paths that were validated. If you hit the exit button instead of the
     * validate button, that means you are just cancelling the selection so you do
     * not require a change in the paths selected
     */
    private TreePath[] validatedPaths;

    /**
     * Creates a data tree out of the profiles present in a map of cities
     * 
     * @param cities map of cities whose profiles one wants to present
     */
    public DataChooser(JFrame owner, HashMap<String, City> cities) {
        // Creates and sets up the dialog
        super(owner, "Select the profiles to present", true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // if the city was not yet added
        if (cities == null || cities.size() == 0) {
            JLabel warning = new JLabel("Warning: No city yet added", SwingConstants.CENTER);
            this.add(warning);
        } else {
            // create the tree by passing in the root node
            CheckBoxTree tree = new CheckBoxTree(addNodes(cities), 1);
            // hides the first node
            tree.setRootVisible(false);
            // expands all the nodes of the tree
            tree.expandAllNodes();

            // creates a panel for the frame
            JPanel panel = new JPanel();
            // divides it in a grid layout
            panel.setLayout(new GridLayout(1, 2));
            // to which adds the tree in a scrollable pane at the left
            panel.add(new JScrollPane(tree));
            // and then a button to validate the selection at the right
            JButton button = new JButton("Validate & Leave");
            button.setActionCommand("Validate & Leave");
            button.addActionListener(new ButtonController(this, tree));
            panel.add(button);

            // then adds the panel to the frame
            this.add(panel);
        }
    }

    /**
     * Displays the window of selection of data to be plotted
     */
    public TreePath[] getSelection() {
        // gets the dimensions of the screen
        Dimension dim = this.getToolkit().getScreenSize();
        // the initial dimensions of the window must be proportional to the dimensions
        // of the screen
        int frameWidth = (int) Math.round(dim.width / screenToFrame);
        int frameHeight = (int) Math.round(dim.height / screenToFrame);

        // defines the initial dimensions of the frame
        this.setSize(frameWidth, frameHeight);
        // defines it initial location to be in the center of the window
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // waits here for the dialog to be closed so that it can return the validated
        // paths
        return this.validatedPaths;
    }

    /**
     * Adds the nodes of a group of cities which correspond to its producers and
     * consumers into a tree
     * 
     * @param cities group of cities whose producers and consumers one wants to
     *               present
     * @return root node of the created tree
     */
    private DefaultMutableTreeNode addNodes(HashMap<String, City> cities) {
        // create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        // creates the nodes to use in the loop
        DefaultMutableTreeNode cityNode;
        DefaultMutableTreeNode producersNode;
        DefaultMutableTreeNode consumersNode;

        // for each city in the group
        for (City city : cities.values()) {
            // creates a node for it and connects it to the root node
            cityNode = new DefaultMutableTreeNode(city.getId());
            root.add(cityNode);

            // creates the nodes of the consumers and the producers
            producersNode = new DefaultMutableTreeNode("Producers");
            consumersNode = new DefaultMutableTreeNode("Consumers");
            // and connects them to the node of the city
            cityNode.add(producersNode);
            cityNode.add(consumersNode);

            // creates the nodes of the producers and consumers of a city
            addChildNodes(producersNode, city.getProducers());
            addChildNodes(consumersNode, city.getConsumers());
        }

        return root;
    }

    /**
     * Gets recursively the nodes of the tree from a previous node
     * 
     * @param parentNode    node of the initial profile
     * @param parentProfile initial profile
     */
    private void addChildNodes(DefaultMutableTreeNode parentNode, Profile parentProfile) {
        DefaultMutableTreeNode auxNode;

        // if the parent profile is a group of profiles
        if (parentProfile instanceof ProfilesGroup) {
            // surfs every child profile
            for (Profile profile : ((ProfilesGroup) parentProfile).getProfiles()) {
                // adding it to the tree
                auxNode = new DefaultMutableTreeNode(profile.getId());
                parentNode.add(auxNode);
                // and recursively sees if the child has children to add
                addChildNodes(auxNode, profile);
            }
        }
    }

    /**
     * Sets a new array of paths as the selected ones
     * 
     * @param validatedPaths new array of validated paths to consider
     */
    public void setValidatedPaths(TreePath[] validatedPaths) {
        this.validatedPaths = validatedPaths;
    }
}
