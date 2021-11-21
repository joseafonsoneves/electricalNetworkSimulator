package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import simulator.City;

/**
 * Allows the functioning of the buttons. It was widely inspired by the class
 * with the same name given in the "SwingExamples" archive.
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Controller implements ActionListener {
    /** Reference to the city to which apply the actions of the controller */
    City city;

    DataTree tree;

    /**
     * Creates a controller that uses the data from the given city
     * 
     * @param city city whose data one will use to plot
     */
    public Controller(City city) {
        this.city = city;
    }

    /**
     * Called when the activation action is launched on the view
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case "New":
            System.out.println("New");
            break;
        case "Losses":
            System.out.println("Losses");
            break;
        case "Selection":
            tree = new DataTree(city);
            tree.show();
            break;
        case "Clear":
            System.out.println("Clear");
            break;
        }
    }
}
