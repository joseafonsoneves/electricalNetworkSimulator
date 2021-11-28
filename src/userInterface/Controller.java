package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import profiles.Profile;
import profiles.ProfilesGroup;
import ptolemy.plot.Plot;
import results.SimType;
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
    /** Saves the type of simulation to perform */
    SimType simType;
    /** If the simulation is of type day, it needs to know it is */
    int day;
    /** Saves the paths of the profiles selected */
    TreePath[] profilePaths;

    /**
     * Creates a controller instance to take care of user input
     * 
     * @param frame reference to the frame to use
     * @param plot  reference to the plot to use
     */
    public Controller(JFrame frame, Plot plot) {
        this.frame = frame;
        this.plot = plot;

        // When the controller is created it is automatically set to a day simulation at
        // the 180th day of the year
        this.simType = SimType.DAY;
        this.day = 180;
        // According to this initial setting the title and the x label of the plot are
        // set
        this.setPlotLabels();
        // the y label will always be in powers
        plot.setYLabel("Power in W");
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
                TreePath[] newPaths = chooser.getSelection();
                // only updates the paths saved if the selection is valid
                if (newPaths != null) {
                    this.profilePaths = newPaths;
                }
                // gets the data of the desired profiles into the plot
                this.plotProfiles();
                break;
            case "Simulation type":
                this.setSimType();
                break;
        }
    }

    /**
     * Plots the data of the selected profiles in the plot of the userInterface
     */
    private void plotProfiles() {
        // to hold the profiles that will be recovered
        Profile profile;
        // to hold the series of powers that will be recovered
        double[] series;

        // if there were no paths selected or there was an error in selection aborts
        if (this.profilePaths == null) {
            return;
        }

        // clears the plot and the legends but not the axis labels or the title
        this.plot.clear(false);
        this.plot.clearLegends();

        // for every path in the list of selected paths
        for (int i = 0; i < this.profilePaths.length; i++) {
            // gets the current path
            TreePath path = this.profilePaths[i];

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

            // gets the series of powers depending on the simulation type
            if (this.simType == SimType.DAY) {
                series = profile.getDayPower(this.day);
            } else {
                series = profile.getYearPower();
            }

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

    /**
     * When you change the type of simulation or the day of the simulation to
     * perform, this function may be called to update the plot
     */
    private void setPlotLabels() {
        // depending on the type of simulation updates the title
        if (this.simType == SimType.DAY) {
            plot.setTitle("Selected Data at day " + this.day);
        } else {
            plot.setTitle("Selected Data during a year");
        }

        // the x label already comes in the sim type enum
        plot.setXLabel(this.simType.getXLabel());

        // presents the changes to the user
        plot.updateUI();
    }

    private void setSimType() {
        boolean plotNeedsUpdate = false;

        // creates a dialog to choose between them
        String s = (String) JOptionPane.showInputDialog(
                frame,
                "Type of simulation to perform?",
                "Simulation type choice",
                JOptionPane.PLAIN_MESSAGE,
                null,
                SimType.getPossibleSimTypes(),
                this.simType.getId());

        // if something has really been chosen
        if (s != null) {
            if (s.compareTo(SimType.DAY.getId()) == 0) {
                // if the simulation type has been changed
                if (this.simType != SimType.DAY) {
                    // records the change
                    this.simType = SimType.DAY;
                    // and records that the plot needs to be updated
                    plotNeedsUpdate = true;
                }
                // gets the array of possible days from the simType enum and then transforms
                // each one of them into a string that saves in the array possibleDays
                String[] possibleDays = Arrays.stream(SimType.getPossibleDays())
                        .mapToObj(String::valueOf)
                        .toArray(String[]::new);
                // creates a dialog to choose between possible days
                s = (String) JOptionPane.showInputDialog(
                        frame,
                        "Type of simulation to perform?",
                        "Simulation type choice",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibleDays,
                        String.valueOf(this.day));

                // if something has really been chosen
                if (s != null) {
                    // gets the selected day as an integer
                    int newDay = Integer.parseInt(s);
                    if (this.day != newDay) {
                        // it will be the day that the user wants to use
                        this.day = newDay;
                        plotNeedsUpdate = true;
                    }
                }
            } else {
                // if the simulation type has been changed
                if (this.simType != SimType.YEAR) {
                    // records the change
                    this.simType = SimType.YEAR;
                    // and the need to update the plot
                    plotNeedsUpdate = true;
                }
            }
        }

        if (plotNeedsUpdate) {
            // updates the plot labels
            this.setPlotLabels();
            // updates the plots of the profiles shown
            this.plotProfiles();
        }
    }
}
