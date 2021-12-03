package extension3;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Simple class to create a list of buttons arranged in the vertical at a grid
 * bag layout. For now it may as simple as not to even need to consider the
 * vertical position it will be
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class VerticalToolbar {
    /**
     * weight of the toolbar on the panel it will be inserted into in the x
     * direction
     */
    private double weightx;
    /** x grid position of the toolbar */
    private int gridx;
    /** list of buttons */
    private ArrayList<JButton> buttons;

    /**
     * Constructor of the vertical toolbar
     * 
     * @param weightx desired value of the horizontal weight on the panel it will be
     *                inserted
     * @param gridx   grid horizontal position of the toolbar
     */
    public VerticalToolbar(double weightx, int gridx) {
        this.weightx = weightx;
        this.gridx = gridx;
        buttons = new ArrayList<JButton>();
    }

    /**
     * Button to add to the toolbar. For now the buttons are supposed to only have
     * name and tip but they can not have tip or they can also have an icon
     * 
     * @param name name of the button that may be displayed
     * @param tip  tip to give on the functioning of the button when hovering it
     */
    public void addButton(String name, String tip) {
        // creates the button with the given name
        JButton button = new JButton(name);
        // adds the tip
        button.setToolTipText(tip);
        // defines the action command as the name of the button. No need to complicate
        // it for now
        button.setActionCommand(name);
        // adds the button to the list
        buttons.add(button);
    }

    /**
     * Adds the toolbar to a specified panel
     * 
     * @param panel JPanel to which to add the toolbar
     */
    public void addToPanel(JPanel panel, Controller controller) {
        // Selects the characteristics of the buttons on the toolbar
        GridBagConstraints c = new GridBagConstraints();
        // Buttons shall fill the window in both directions
        c.fill = GridBagConstraints.BOTH;
        // Every button has the same vertical space
        c.weighty = 1;
        // fills with the values specified for the toolbar
        c.weightx = this.weightx;
        c.gridx = this.gridx;

        for (int i = 0; i < buttons.size(); i++) {
            // descends in the grid
            c.gridy = i;
            // adds to each button the given controller
            buttons.get(i).addActionListener(controller);
            // adds each button to the given panel
            panel.add(buttons.get(i), c);
        }
    }
}
