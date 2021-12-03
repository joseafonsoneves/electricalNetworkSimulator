package extension3;

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
		this.controller = this.createController(plot);

		// Creates a small vertical toolbar compared to the plot and places at the right
		// of the plot
		VerticalToolbar toolbar = this.createToolbar();
		toolbar.addToPanel(panel, this.controller);

		// adds the panel to the frame
		this.frame.setContentPane(panel);
	}

	/**
	 * Gets the frame of the user interface window
	 * 
	 * @return frame of the user interface window
	 */
	protected JFrame getFrame() {
		return this.frame;
	}

	/**
	 * This method creates the toolbar. This method is important because it will be
	 * overridden in integration steps to add different buttons to the interface.
	 * For now it only has the buttons that correspond to actions performed by this
	 * extension or the initial version but in the integration it will have buttons
	 * that represent actions performed by other extensions
	 * 
	 * @return creates a vertical toolbar with the buttons needed for this extension
	 *         and the initial version only
	 */
	protected VerticalToolbar createToolbar() {
		VerticalToolbar toolbar = new VerticalToolbar(0.05, 1);
		toolbar.addButton("Profiles", "Selects new profiles");
		toolbar.addButton("Simulation type", "Selects the type of simulation to perform");

		return toolbar;
	}

	/**
	 * This method creates the controller. This method is important because it will
	 * be overridden in integration steps to add other controllers to the interface.
	 * For now it controls the buttons that correspond to actions performed by this
	 * extension or the initial version but in the integration it will control new
	 * buttons
	 * 
	 * @param plot the controller must receive a reference to plot
	 * @return creates a controller for the buttons needed for this extension
	 *         and the initial version only
	 */
	protected Controller createController(Plot plot) {
		return new Controller(this.frame, plot);
	}

	/**
	 * Adds a city to use. The interface designed is ready to work in two
	 * configurations: with the adding of a city exteriorly to it or with the
	 * adding of a city using it. In the case of exterior addition this is the
	 * method that passes the city to the controller where the reference to it will
	 * be stored
	 * 
	 * @param city city to choose
	 */
	public void addCity(City city) {
		controller.addCity(city);
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
