package extension3;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ptolemy.plot.Plot;

/**
 * Class created to implement the 3rd extension: a graphical interface. Its
 * objective will be to allow the user to only interact with the project through
 * the .csv files created with the structure of the groups of cities and this
 * interface
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class UserInterface {
	/**
	 * Proportion between the dimensions of the screen and the initial dimensions of
	 * the frame
	 */
	private static final double screenToFrame = 1.5;

	/** Frame object of the interface */
	private JFrame frame;
	/** Reference to the Plot object that it contains */
	private Plot plot;
	/**
	 * Reference to the vertical toolbar that contains the buttons to perform the
	 * actions
	 */
	private VerticalToolbar toolbar;

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
		this.plot = new Plot();
		this.plot.setMarksStyle("points");
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
		panel.add(this.plot, c);

		// Creates a small vertical toolbar compared to the plot and places at the right
		// of the plot
		this.toolbar = this.createToolbar();
		toolbar.addToPanel(panel);

		// adds the panel to the frame
		this.frame.setContentPane(panel);
	}

	/**
	 * Gets the frame of the user interface window
	 * 
	 * @return frame of the user interface window
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Gets the plot of the user interface
	 * 
	 * @return plot of the user interface
	 */
	public Plot getPlot() {
		return this.plot;
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
	 * This method sets a new controller as action listener of the toolbar so that
	 * the buttons work
	 * 
	 * @return adds a controller for the buttons needed for this extension
	 *         and the initial version only
	 */
	public void setController(Controller controller) {
		toolbar.addController(controller);
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

	/** Updates the UI which right now is only to update the plot */
	public void updateUI() {
		this.plot.updateUI();
	}

	/**
	 * Sets the title of the plot
	 * 
	 * @param s new title of the plot
	 */
	public void setPlotTitle(String s) {
		this.plot.setTitle(s);
	}

	/**
	 * Sets the xlabel of the plot
	 * 
	 * @param s new xlabel of the plot
	 */
	public void setPlotXLabel(String s) {
		this.plot.setXLabel(s);
	}

	/**
	 * Adds a series to the plot with a legend
	 * 
	 * @param seriesIndex index of the series to each to add the point
	 * @param series      data to add
	 * @param legend      legend to add with the series
	 */
	public void addPlotSeries(int seriesIndex, double series[], String legend) {
		// adds the legend of the series
		this.plot.addLegend(seriesIndex, legend);
		// adds each point of the series to the plot
		for (int j = 0; j < series.length; j++) {
			this.getPlot().addPoint(seriesIndex, j, series[j], true);
		}
	}

	/** Clears the plot area and the legends */
	public void clearPlot() {
		// clears the plot area but not the axis labels or the title
		this.plot.clear(false);
		// clears the legends of the plot
		this.plot.clearLegends();
	}
}
