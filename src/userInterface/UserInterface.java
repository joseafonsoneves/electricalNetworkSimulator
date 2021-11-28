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
	/** Controller to the buttons implemented in the interface */
	private Controller controller;

	/**
	 * Creates all the objects present in the interface so that one can afterwards
	 * give them functions and display them
	 */
	public UserInterface() {
		// Creates the window
		this.frame = new JFrame("Electrical Simulator Interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creates the panel in a GridBagLayout form
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		// Creates the plot
		Plot plot = new Plot();
		plot.setMarksStyle("points");
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
		panel.add(plot, c);

		// Creates the controller for the main window. It will be able to use the plot
		// of the main window so it is created after it
		this.controller = new Controller(this.frame, plot);

		// Creates a small vertical toolbar compared to the plot and places at the right
		// of the plot
		VerticalToolbar toolbar = new VerticalToolbar(0.05, 1);
		toolbar.addButton("New", "Uses a new configuration file");
		toolbar.addButton("Profiles", "Selects new profiles");
		toolbar.addButton("Simulation type", "Selects the type of simulation to perform");
		toolbar.addButton("Losses", "Simulates the losses");
		toolbar.addToPanel(panel, this.controller);

		// adds the panel to the frame
		this.frame.setContentPane(panel);
	}

	/**
	 * Sets the city to use at the actions performed by the buttons. The interface
	 * designed is ready to work in two configurations: with the setting of a city
	 * exteriorly to it or with the setting of the city using it. In the case of
	 * exterior setting this is the method that passes the city to the controller
	 * where the reference to it will be stored
	 * 
	 * @param city city to choose
	 */
	public void setCity(City city) {
		controller.setCity(city);
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
