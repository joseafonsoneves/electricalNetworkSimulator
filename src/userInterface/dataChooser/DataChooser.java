package userInterface.dataChooser;

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

import java.awt.GridLayout;

import profiles.Profile;
import profiles.ProfilesGroup;
import simulator.City;
import userInterface.dataChooser.checkBoxTree.CheckBoxTree;

public class DataChooser extends JDialog {
    /**
     * Proportion between the dimensions of the screen and the initial dimensions of
     * the frame
     */
    private static final double screenToFrame = 2;

    /** Object of a check box tree */
    private CheckBoxTree tree;
    /** Checked paths */
    private TreePath[] checkedPaths;

    /**
     * Creates a data tree out of the profiles present in a city
     * 
     * @param city city whose profiles one wants to present
     */
    public DataChooser(JFrame owner, City city) {
        // Creates and sets up the dialog
        super(owner, "Select the profiles to present", true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // if the city was not yet added
        if (city == null) {
            JLabel warning = new JLabel("Warning: No city yet added", SwingConstants.CENTER);
            this.add(warning);
        } else {
            // create the tree by passing in the root node
            tree = new CheckBoxTree(addCityNodes(city));
            // hides the root node
            tree.setRootVisible(false);
            // expands all the tree so that the user hasn't got to
            this.expandAllNodes();

            // creates a panel for the frame
            JPanel panel = new JPanel();
            // divides it in a grid layout
            panel.setLayout(new GridLayout(1, 2));
            // to which adds the tree in a scrollable pane at the left
            panel.add(new JScrollPane(tree));
            // and then a button to validate the selection at the right
            JButton button = new JButton("Validate");
            button.setActionCommand("Validate");
            button.addActionListener(new ButtonController(tree, this));
            panel.add(button);

            // then adds the panel to the frame
            this.add(panel);
        }
    }

    /**
     * Displays the window of selection of data to be plotted
     */
    public TreePath[] getProfiles() {
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

        // waits here for the dialog to be closed so that it can return the checked
        // paths
        return this.checkedPaths;
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
    private DefaultMutableTreeNode addCityNodes(City city) {
        // create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        // creates the nodes of the consumers and the producers
        DefaultMutableTreeNode producersNode = new DefaultMutableTreeNode("Producers");
        DefaultMutableTreeNode consumersNode = new DefaultMutableTreeNode("Consumers");
        root.add(producersNode);
        root.add(consumersNode);

        // creates the nodes of the producers and consumers of a city
        addChildNodes(producersNode, city.getProducers());
        addChildNodes(consumersNode, city.getConsumers());

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

    public void setCheckedPaths(TreePath[] checkedPaths) {
        this.checkedPaths = checkedPaths;
    }
}
