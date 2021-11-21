package userInterface;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VerticalToolbar {
    private double weightx;
    private int gridx;
    private ArrayList<JButton> buttons;

    public VerticalToolbar(double weightx, int gridx) {
        this.weightx = weightx;
        this.gridx = gridx;
        buttons = new ArrayList<JButton>();
    }

    public void addButton(String name, String tip) {
        JButton button = new JButton(name);
        button.setToolTipText(tip);
        button.setActionCommand(name);
        buttons.add(button);
    }

    /**
     * Adds the toolbar to a specified panel
     * 
     * @param panel JPanel to which to add the toolbar
     */
    public void createToolbar(JPanel panel, Controller controller) {
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
            c.gridy = i;
            buttons.get(i).addActionListener(controller);
            panel.add(buttons.get(i), c);
        }
    }
}
