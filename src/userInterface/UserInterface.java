package userInterface;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ptolemy.plot.Plot;
import simulator.City;

/**
 * Class created to implement the 3rd extension: a graphical interface. Its
 * objective will be to allow the user to only interact with the project through
 * the .csv files created with the structure of the groups of cities and this
 * interface
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 * @version v0.0.1 - before integration
 */
public class UserInterface {
	/**
	 * Proportion between the dimensions of the screen and the initial dimensions of
	 * the frame
	 */
	private static final double screenToFrame = 1.5;

	/** Frame object of the interface */
	private JFrame frame;
	/** Panel object of the main window */
	private JPanel panel;
	/** Plot object to be used inside the interface */
	private Plot plot;
	/** Vertical toolbar to contain the buttons needed in the interface */
	private VerticalToolbar toolbar;
	/** Controller to the buttons implemented in the interface */
	private Controller controller;

	/**
	 * Creates all the objects present in the interface so that one can afterwards
	 * give them functions and display them
	 */
	public UserInterface(City city) {
		// Creates the panel in a GridBagLayout form
		this.panel = new JPanel();
		this.panel.setLayout(new GridBagLayout());

		createPlot();

		// Creates the controller for the main window. It will be able to use the plot
		// of the main window so it is created after it
		this.controller = new Controller(city);

		// Creates a small vertical toolbar compared to the plot and places at the right
		// of the plot
		toolbar = new VerticalToolbar(0.05, 1);
		toolbar.addButton("New", "Uses a new configuration file");
		toolbar.addButton("Losses", "Simulates the losses");
		toolbar.addButton("Selection", "Selects new elements");
		toolbar.addButton("Clear", "Clears the plot");
		toolbar.createToolbar(this.panel, this.controller);

		// Creates and sets up the window adding it the panel
		this.frame = new JFrame("Electrical Simulator Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setContentPane(this.panel);
	}

	/** Creates the plot at the left side of the window */
	private void createPlot() {
		// Creates the plot
		this.plot = new Plot();
		this.plot.setMarksStyle("points");
		this.plot.setTitle("Selected Data");
		GridBagConstraints c = new GridBagConstraints();
		// Placed in the origin of the window
		c.gridx = 0;
		c.gridy = 0;
		// Allows it to resize itself in both directions
		c.fill = GridBagConstraints.BOTH;
		// Gives it more size than the toolbar in the x direction
		c.weightx = 0.99;
		// Occupies the vertical direction till the end of it
		c.gridheight = GridBagConstraints.REMAINDER;
		this.panel.add(this.plot, c);
	}

	/** Shows the main window */
	public void show() {
		// gets the dimensions of the screen
		Dimension dim = frame.getToolkit().getScreenSize();
		// the initial dimensions of the window must be proportional to the dimensions
		// of the screen
		int frameWidth = (int) Math.round(dim.width / screenToFrame);
		int frameHeight = (int) Math.round(dim.height / screenToFrame);

		// defines the initial dimensions of the frame
		frame.setSize(frameWidth, frameHeight);
		// defines it initial location to be in the center of the window
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}