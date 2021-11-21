package userInterface;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import profiles.Profile;
import profiles.ProfilesGroup;
import simulator.City;

public class DataTree {
    /**
     * Proportion between the dimensions of the screen and the initial dimensions of
     * the frame
     */
    private static final double screenToFrame = 3;

    /** Object of a tree */
    private JTree tree;
    /** Object of a window */
    private JFrame frame;

    /**
     * Creates a data tree out of the profiles present in a city
     * 
     * @param city city whose profiles one wants to present
     */
    public DataTree(City city) {
        // Creates and sets up the window adding it the panel
        this.frame = new JFrame("Data to Show");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // if the city was not yet added
        if (city == null) {
            JLabel warning = new JLabel("Warning: No city yet added", SwingConstants.CENTER);
            this.frame.add(warning);
        } else {
            DefaultMutableTreeNode root = getCityNodes(city);

            // create the tree by passing in the root node
            tree = new JTree(root);
            // hides the root node
            tree.setRootVisible(false);
            // expands all the tree so that the user hasn't got to
            this.expandAllNodes();

            // makes the tree scrollable
            this.frame.add(new JScrollPane(tree));
        }
    }

    /**
     * Shows the window of selection of data to be plotted
     */
    public void show() {
        // gets the dimensions of the screen
        Dimension dim = frame.getToolkit().getScreenSize();
        // the initial dimensions of the window must be proportional to the dimensions
        // of the screen
        int frameWidth = (int) Math.round(dim.width / screenToFrame);
        int frameHeight = (int) Math.round(dim.height / screenToFrame);

        // defines the initial dimensions of the frame
        frame.setSize(frameWidth, frameHeight);
        // defines it initial location to be in the center of the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Expands every node of the tree so the user has not got to
     */
    private void expandAllNodes() {
        int numRows = tree.getRowCount();

        // before the end of the tree
        for (int row = 0; row < numRows; row++) {
            // expands every row
            tree.expandRow(row);
            // which can create more rows so updates the number of rows and goes for the
            // next one
            numRows = tree.getRowCount();
        }
    }

    /**
     * Adds the nodes of a city which correspond to its producers and consumers into
     * a tree
     * 
     * @param city city whose producers and consumers one wants to present
     * @return root node of the created tree
     */
    private DefaultMutableTreeNode getCityNodes(City city) {
        // create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        // creates the nodes of the consumers and the producers
        DefaultMutableTreeNode producersNode = new DefaultMutableTreeNode("Producers");
        DefaultMutableTreeNode consumersNode = new DefaultMutableTreeNode("Consumers");
        root.add(producersNode);
        root.add(consumersNode);

        // creates the nodes of the producers and consumers of a city
        getChildNodes(producersNode, city.getProducers());
        getChildNodes(consumersNode, city.getConsumers());

        return root;
    }

    /**
     * Gets recursively the nodes of the tree from a previous node
     * 
     * @param parentNode    node of the initial profile
     * @param parentProfile initial profile
     */
    private void getChildNodes(DefaultMutableTreeNode parentNode, Profile parentProfile) {
        DefaultMutableTreeNode auxNode;

        // if the parent profile is a group of profiles
        if (parentProfile instanceof ProfilesGroup) {
            // surfs every child profile
            for (Profile profile : ((ProfilesGroup) parentProfile).getProfiles()) {
                // adding it to the tree
                auxNode = new DefaultMutableTreeNode(profile.getId());
                parentNode.add(auxNode);
                // and recursively sees if the child has children to add
                getChildNodes(auxNode, profile);
            }
        }
    }
}
