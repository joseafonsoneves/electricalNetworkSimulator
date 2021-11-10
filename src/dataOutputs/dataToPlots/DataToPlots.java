package dataOutputs.dataToPlots;

import java.awt.Dimension;
import javax.swing.JFrame;
import ptolemy.plot.Plot;

import dataOutputs.DataOutput;
import results.Results;

/**
 * Plots different types of values. It was heavily adapted from the code
 * provided in session M5
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Neves
 */
public class DataToPlots implements DataOutput {
    /** Plot object */
    private Plot plot;
    /** Frame object */
    private JFrame frame;
    /** Type of plot to make from the results object received */
    private PlotType plotType;

    /**
     * Create an empty plotting window of size width x height, e.g. 800x600 (4/3) or
     * 800x450 (16/9).
     * 
     * @param width  Width in pixels
     * @param height Height in pixels
     */
    public DataToPlots(int width, int height, PlotType plotType) {
        if (width > 0 && height > 0) {
            this.plotType = plotType;

            // initialize PtPlot's plotter widget with the specified size
            plot = new Plot();
            plot.setMarksStyle("points");
            plot.setSize(new Dimension(width, height));

            // put it in a main window
            frame = new JFrame("Plotter");
            frame.add(plot);
            frame.pack();

            // exits the Java program when you close the main window
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            throw new IllegalArgumentException("Width or height are not positive");
        }
    }

    public void outputData(String destinationFolder, Results dataset) {
        // auxiliary variable to save the points to add to the plots
        double[] series;

        // add title to the graph
        plot.setTitle(plotType.getId() + " in " + plotType.getUnit() + " throughout a " + dataset.getTypeId()
                + " in city " + dataset.getCity());
        // add x label to the graph
        plot.setXLabel(dataset.getXLabel());

        // adds the series of points
        for (int i = 0; i < plotType.getNumSeries(); i++) {
            plot.clear(i);
            plot.addLegend(i, plotType.getSeriesName(i));

            // add the points of each series to the plot
            // gets the series data taking into account which column of the dataset it
            // should address
            series = dataset.getColumn(plotType.getColumn(i));

            // adds each one of them to the plot
            for (int j = 0; j < series.length; j++) {
                plot.addPoint(i, j, series[j], true);
            }
        }

        // present it to the user
        frame.setVisible(true);
    }
}
