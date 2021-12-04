package integration;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import extension1.CityWithPosition;
import extension1.Map;
import extension2.CSVRead;
import extension3.CSVChooser;
import extension3.Controller;
import ptolemy.plot.Plot;
import simulator.City;

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
            case "Load":
                this.loadNewCities();
                break;
            /*
             * case "Losses":
             * this.computeLosses();
             * break;
             */
            case "Profiles":
                this.chooseProfiles();
                break;
            case "Simulation type":
                this.setSimType();
                break;
        }
    }

    /**
     * From the click on the button to the loading of a group of cities, this method
     * allows to graphically select a file, load from it information on a group of
     * cities and then add them as the new group of cities to take into account
     */
    protected void loadNewCities() {
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
            // if it could not read new cities from the file signals the error
            else {
                JOptionPane.showMessageDialog(null, "Could not load the selected file");
            }
        }
    }

    /**
     * From the click on the button to the computation of the losses between cities,
     * this method allows to graphically select a file, load from it information on
     * the position of a group of cities and the established connections between
     * them and to compute the losses in power between them
     */
    protected void computeLosses() {
        // gets the file
        File lossesFile = CSVChooser.getFile(super.getFrame(), "CityData");
        // if it is not null
        if (lossesFile != null) {
            // adds a position attribute to every city that does not have one
            convertCitiesToCitiesWithPosition();
            // puts the cities in the positions read from the file and reads the matrix of
            // connections as well
            int[][] connections = CSVRead.readMatrixAndAddPositions(this.getCities(),
                    lossesFile.getAbsolutePath());
            // converts the list of cities to the data format used in the extension 1
            HashMap<CityWithPosition, Integer> graph = convertCitiesToGraph();
            // creates a map according to extension 1
            Map newMap = new Map(connections, graph);
        }
    }

    /**
     * Although one starts by only creating cities, when the computation of losses
     * is needed one has to convert cities to cities with positions so that
     * positions can be added to the cities
     */
    private void convertCitiesToCitiesWithPosition() {
        HashMap<String, City> cities = this.getCities();

        // for every city in the group
        for (City city : cities.values()) {
            // if city is not an instance of CityWithPosition
            if (!(city instanceof CityWithPosition)) {
                // puts a city with position at the place of that city in the hash map
                cities.put(city.getId(), new CityWithPosition(city));
            }
        }
    }

    /**
     * Extension 1 receives an hash map of cities as keys and numbers as indexes so
     * one has to convert from the typical hash map of strings and cities to this
     * new data format
     */
    private HashMap<CityWithPosition, Integer> convertCitiesToGraph() {
        HashMap<String, City> cities = this.getCities();
        HashMap<CityWithPosition, Integer> graph = new HashMap<CityWithPosition, Integer>();

        // the index of the first city
        int count = 1;
        // for every city in the group
        for (City city : cities.values()) {
            // if there is no error of conversion between classes
            if (city instanceof CityWithPosition) {
                // adds the city to the graph of cities
                graph.put((CityWithPosition) city, count);
            } else {
                // throws an exception
                throw new RuntimeException("Unexpected error: city is not an instance of CityWithPosition");
            }
            // increases the index
            count = count + 1;
        }

        return graph;
    }
}
