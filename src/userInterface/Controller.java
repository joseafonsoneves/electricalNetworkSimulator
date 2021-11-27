package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

import simulator.City;
import userInterface.dataChooser.DataChooser;

/**
 * Allows the functioning of the buttons. It was widely inspired by the class
 * with the same name given in the "SwingExamples" archive.
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Controller implements ActionListener {
    /** Reference to the city to which to apply the actions of the controller */
    City city;
    /** Frame to which the controller is applied */
    JFrame frame;

    public Controller(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Sets the city to use in the toolbar
     * 
     * @param city city to use
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Called when the activation action is launched on the view
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case "New":
            // gets the file
            File fc = ConfigChooser.getFile();
            // if it is not null
            if (fc != null) {
                // presents its name
                System.out.println(fc.getName());
                // reads the file and creates a city from it
                this.city = readCSVMask.fromFileToCity(fc);
            }
            break;
        case "Losses":
            System.out.println("Losses");
            break;
        case "Selection":
            DataChooser chooser = new DataChooser(frame, city);
            TreePath[] profilesPaths = chooser.getProfiles();
            for (TreePath profilePath : profilesPaths) {
                System.out.println(profilePath);
            }
            break;
        case "Clear":
            System.out.println("Clear");
            break;
        }
    }
}
