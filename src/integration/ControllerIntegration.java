package integration;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import extension2.CSVRead;
import extension3.CSVChooser;
import extension3.Controller;
import extension3.UserInterface;
import simulator.City;

/**
 * Class to allow the integration of different extensions with the user
 * interface extension. It extends the Controller extension so that it is
 * easy to do the integration
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
    public ControllerIntegration(UserInterface ui, UIModelIntegration uiModel) {
        super(ui, uiModel);
    }

    /**
     * Gets the UI model like the parent but since it will mandatorily be
     * UIModelIntegration casts it already to that class
     * 
     * @return UI model cast to UIModelIntegration
     */
    @Override
    public UIModelIntegration getUIModel() {
        return (UIModelIntegration) super.getUIModel();
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
            case "Losses":
                this.loadLosses();
                break;
            case "Data to plot":
                this.chooseData(new DataChooserIntegration(this.getFrame(), this.getUIModel()));
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
                this.getUIModel().setCities(newCities);
                // updates the name of the file
                this.getUIModel().setCitiesFile(profilesFile);
                // and then updates it in the window of the plot
                this.setPlotLabels();
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
    protected void loadLosses() {
        // gets the file
        File lossesFile = CSVChooser.getFile(super.getFrame(), "CityData");
        // if it is not null
        if (lossesFile != null) {
            // adds a position attribute to every city that does not have one
            this.getUIModel().convertCitiesToCitiesWithPosition();
            // puts the cities in the positions read from the file and reads the matrix of
            // connections as well
            int[][] connections = CSVRead.readMatrixAndAddPositions(this.getUIModel().getCities(),
                    lossesFile.getAbsolutePath());
            // makes the computations and if they were not successful
            if (!this.getUIModel().computeLosses(connections)) {
                // communicates it to the user
                JOptionPane.showMessageDialog(null,
                        "The losses computation only works for one and one only city with producers");
            }
        }
    }

    @Override
    protected void addPathToPlot(TreePath path, int seriesIndex) {
        // if the path corresponds to losses
        if (path.getPathComponent(1).toString().compareTo("Losses") == 0) {
            this.addLossesPlots(path, seriesIndex);
        }
        // the path corresponds to the profile of a city
        else {
            // adds the corresponding plot to the plot object
            this.addProfilePlots(path, seriesIndex);
        }
    }

    /**
     * Adds a plot of losses that was selected by the user
     * 
     * @param path        path to the names of the losses that were selected
     * @param seriesIndex index of the series in which to plot the data
     */
    protected void addLossesPlots(TreePath path, int seriesIndex) {
        if (this.getUIModel().hasLosses()) {
            // gets the data of the losses
            double[] series = this.getUIModel().getLoss(path.getPathComponent(2).toString());

            // if there was no problem
            if (series != null) {
                // adds the series to the plot
                this.getUI().addPlotSeries(seriesIndex, series, path.getPathComponent(2).toString());
            } else {
                throw new RuntimeException("Difference between selected losses and losses saved");
            }
        }
    }
}
