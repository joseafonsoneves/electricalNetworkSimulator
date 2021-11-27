package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.tree.TreePath;

import profiles.Profile;
import profiles.ProfilesGroup;
import ptolemy.plot.Plot;
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
    /** Reference to the plot so that it can add it graphs */
    Plot plot;

    /**
     * Creates a controller instance to take care of user input
     * 
     * @param frame reference to the frame to use
     * @param plot  reference to the plot to use
     */
    public Controller(JFrame frame, Plot plot) {
        this.frame = frame;
        this.plot = plot;
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
                // creates the dialog to choose the profiles to use
                DataChooser chooser = new DataChooser(frame, city);
                // gets the paths of the profiles to use
                TreePath[] profilePaths = chooser.getProfiles();
                // gets the data of the desired profiles into the plot
                this.plotProfiles(profilePaths);
                break;
            case "Clear":
                System.out.println("Clear");
                break;
        }
    }

    /**
     * Plots the data of the selected profiles in the plot of the userInterface
     * 
     * @param profilePaths paths to the profiles
     */
    private void plotProfiles(TreePath[] profilePaths) {
        // to hold the profiles that will be recovered
        Profile profile;
        // to hold the series of powers that will be recovered
        double[] series;

        // if there were no paths selected or there was an error in selection aborts
        if (profilePaths == null)
            return;

        // clears the plot and the legends but not the axis labels or the title
        this.plot.clear(false);
        this.plot.clearLegends();

        // for every path in the list of selected paths
        for (int i = 0; i < profilePaths.length; i++) {
            // gets the current path
            TreePath path = profilePaths[i];

            // if it is a consumer
            if (path.getPathComponent(1).toString().compareTo("Consumers") == 0) {
                // gets the group of consumers
                profile = city.getConsumers();
            }
            // if it is not a consumer, it will be a producer
            else {
                // gets the group of producers
                profile = city.getProducers();
            }

            // for element in the path
            for (int j = 1; j < path.getPathCount() - 1; j++) {
                // surfs the tree of profiles taking into consideration that until the last each
                // one of them will be a group of profiles
                profile = ((ProfilesGroup) profile).getProfile(path.getPathComponent(j + 1).toString());
            }

            // gets the series of powers of the profile in the given day
            series = profile.getDayPower(0);
            // adds the legend as the id of the profile
            plot.addLegend(i, profile.getId());
            // adds each point of the series to the plot
            for (int j = 0; j < series.length; j++) {
                plot.addPoint(i, j, series[j], true);
            }
        }

        // presents the changes to the user
        plot.updateUI();
    }
}
