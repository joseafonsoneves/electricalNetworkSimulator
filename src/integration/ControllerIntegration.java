package integration;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;

import extension2.CSVRead;
import ptolemy.plot.Plot;
import simulator.City;
import userInterface.CSVChooser;
import userInterface.Controller;

/**
 * Allows the functioning of the buttons. It was widely inspired by the class
 * with the same name given in the "SwingExamples" archive.
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class ControllerIntegration extends Controller {
    /**
     * Just creates the controller defined in the super class
     * 
     * @param frame reference to the frame to use
     * @param plot  reference to the plot to use
     */
    public ControllerIntegration(JFrame frame, Plot plot) {
        super(frame, plot);
    }

    /**
     * It is the same thing as the one defined in the super class but now for more
     * buttons
     * 
     * @param e event that calls the attention and may lead to do something
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New":
                // gets the file
                File profilesFile = CSVChooser.getFile(super.getFrame(), "CityData");
                // if it is not null
                if (profilesFile != null) {
                    // reads the file and creates a city from it
                    HashMap<String, City> newCities = CSVRead.readSeveralCities(profilesFile.getAbsolutePath());
                    // if there was not an error reading the file
                    if (newCities != null && newCities.size() > 0) {
                        // updates the list of cities
                        super.setCities(newCities);
                        // updates the name of the file
                        super.setCitiesFile(profilesFile);
                        // and then updates it in the window of the plot
                        super.setPlotLabels();
                    }
                }
                break;
            case "Losses":
                // gets the file
                File lossesFile = CSVChooser.getFile(super.getFrame(), "CityData");
                // if it is not null
                if (lossesFile != null) {
                    // presents its name
                    System.out.println(lossesFile.getName());
                }
                break;
            case "Profiles":
                super.chooseProfiles();
                break;
            case "Simulation type":
                this.setSimType();
                break;
        }
    }
}
