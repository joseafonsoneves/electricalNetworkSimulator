package extension3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import extension3.dataChooser.DataChooser;
import profiles.Profile;
import profiles.ProfilesGroup;
import results.SimType;
import simulator.City;

/**
 * Allows the functioning of the buttons. It was widely inspired by the class
 * with the same name given in the "SwingExamples" archive.
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Controller implements ActionListener {
    /** Reference to the user interface it is associated with */
    UserInterface ui;
    /** Reference to the model to consider in this interface */
    UIModel uiModel;

    /**
     * Creates a controller instance to take care of user input
     * 
     * @param ui      reference to the corresponding user interface
     * @param uiModel reference to the model to use
     */
    public Controller(UserInterface ui, UIModel uiModel) {
        // saves the reference to the ui
        this.ui = ui;
        // saves the model
        this.uiModel = uiModel;

        // According to this initial setting the title and the x label of the plot are
        // set
        this.setPlotLabels();
        // the y label will always be in powers
        this.ui.getPlot().setYLabel("Power in W");
    }

    /**
     * Gets the frame of the object
     * 
     * @return gets the frame of the object
     */
    protected JFrame getFrame() {
        return this.ui.getFrame();
    }

    /**
     * Gets the user interface
     * 
     * @return associated user interface
     */
    protected UserInterface getUI() {
        return this.ui;
    }

    /**
     * Gets the ui model it is implementing
     * 
     * @return implemented ui model
     */
    protected UIModel getUIModel() {
        return this.uiModel;
    }

    /**
     * Called when the activation action is launched on the view
     * 
     * @param e event that calls the attention and may lead to do something
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Profiles":
                this.chooseData(new DataChooser(this.getFrame(), this.uiModel));
                break;
            case "Simulation type":
                this.setSimType();
                break;
        }
    }

    /**
     * Plots the data of the selected profiles in the plot of the userInterface
     */
    protected void updatePlot() {
        // clears the plot and the legends but not the axis labels or the title
        this.ui.clearPlot();
        // if there are paths to draw
        if (this.getUIModel().getPaths() != null) {
            // for every path in the list of selected paths
            for (int i = 0; i < this.getUIModel().getPaths().length; i++) {
                // adds the corresponding plot to the plot object
                this.addPathToPlot(this.getUIModel().getPaths()[i], i);
            }
        }
        // presents the changes to the user
        this.ui.updateUI();
    }

    /**
     * This method facilitates the integration of new things to the tree since it
     * may be overridden to change how a path is transformed in a series in the plot
     * 
     * @param path        path to convert to a series in the plot
     * @param seriesIndex index of the series to add in the plot
     */
    protected void addPathToPlot(TreePath path, int seriesIndex) {
        this.addProfilePlots(path, seriesIndex);
    }

    /**
     * Adds the plots of a consumer or a producer of a given city to the plot object
     * 
     * @param path        path to be converted to the plot of a profile
     * @param seriesIndex index of the series in which to put it
     */
    protected void addProfilePlots(TreePath path, int seriesIndex) {
        Profile profile;

        // gets the city corresponding to the path
        City city = this.uiModel.getCity(path.getPathComponent(1).toString());

        // if it is a consumer
        if (path.getPathComponent(2).toString().compareTo("Consumers") == 0) {
            // gets the group of consumers
            profile = city.getConsumers();
        }
        // if it is not a consumer, it will be a producer
        else {
            // gets the group of producers
            profile = city.getProducers();
        }

        // for element in the path
        for (int j = 2; j < path.getPathCount() - 1; j++) {
            // surfs the tree of profiles taking into consideration that until the last each
            // one of them will be a group of profiles
            profile = ((ProfilesGroup) profile).getProfile(path.getPathComponent(j + 1).toString());
        }

        // gets the series of powers depending on the simulation type
        double[] series;
        if (this.getUIModel().getSimType() == SimType.DAY) {
            series = profile.getDayPower(this.getUIModel().getDay());
        } else {
            series = profile.getYearPower();
        }

        // adds the legend as the id of the profile
        this.ui.addPlotSeries(seriesIndex, series, profile.getId());
    }

    /**
     * When you change the type of simulation or the day of the simulation to
     * perform, this function may be called to update the plot
     */
    public void setPlotLabels() {
        // this section of code is already part of the integration and we added it
        // because it is more easy for the user to know what is happening if he knows
        // which file we are talking about easily
        // if the name of the cities data file is not null
        String str;
        if (this.getUIModel().getCitiesFile() == null) {
            // it adds nothing to the title
            str = "";
        } else {
            // else it talks about the file in the file
            str = " of file " + this.getUIModel().getCitiesFile().getName();
        }
        // depending on the type of simulation updates the title
        if (this.getUIModel().getSimType() == SimType.DAY) {
            this.ui.setPlotTitle("Selected Data at day " + this.getUIModel().getDay() + str);
        } else {
            this.ui.setPlotTitle("Selected Data during a year" + str);
        }
        // the x label already comes in the sim type enum
        this.ui.setPlotXLabel(this.getUIModel().getSimType().getXLabel());
        // presents the changes to the user
        this.ui.updateUI();
    }

    protected void setSimType() {
        boolean plotNeedsUpdate = false;

        // creates a dialog to choose between them
        String s = (String) JOptionPane.showInputDialog(
                this.getFrame(),
                "Type of simulation to perform?\nIf you choose Day, a new dialog will allow you to choose the day",
                "Simulation type choice",
                JOptionPane.DEFAULT_OPTION,
                null,
                SimType.getPossibleSimTypes(),
                this.getUIModel().getSimType().getId());

        // if something has really been chosen
        if (s != null) {
            if (s.compareTo(SimType.DAY.getId()) == 0) {
                // if the simulation type has been changed
                if (this.getUIModel().getSimType() != SimType.DAY) {
                    // records the change
                    this.getUIModel().setSimType(SimType.DAY);
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
                        this.getFrame(),
                        "Day in which to perform the simulation?",
                        "Simulation day choice",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibleDays,
                        String.valueOf(this.getUIModel().getDay()));

                // if something has really been chosen
                if (s != null) {
                    // gets the selected day as an integer
                    int newDay = Integer.parseInt(s);
                    if (this.getUIModel().getDay() != newDay) {
                        // it will be the day that the user wants to use
                        this.getUIModel().setDay(newDay);
                        plotNeedsUpdate = true;
                    }
                }
            } else {
                // if the simulation type has been changed
                if (this.getUIModel().getSimType() != SimType.YEAR) {
                    // records the change
                    this.getUIModel().setSimType(SimType.YEAR);
                    // and the need to update the plot
                    plotNeedsUpdate = true;
                }
            }
        }

        if (plotNeedsUpdate) {
            this.updateOnSimTypeChange();
        }
    }

    /**
     * Gathers the actions needed to be done in case of update of the sim type. This
     * function is important in the integration context because its redefinition
     * allows the adding of new actions to be done at this time
     */
    protected void updateOnSimTypeChange() {
        // updates the plot labels
        this.setPlotLabels();
        // updates the plots of the profiles shown
        this.updatePlot();
    }

    /**
     * Lets the user choose the profiles to show in the plot
     */
    protected void chooseData(DataChooser chooser) {
        // gets the paths of the profiles to use
        TreePath[] newPaths = chooser.getSelection();
        // only updates the paths saved if the selection is valid
        if (newPaths != null) {
            this.getUIModel().setPaths(newPaths);
            // gets the data of the desired profiles into the plot
            this.updatePlot();
        }
    }
}
