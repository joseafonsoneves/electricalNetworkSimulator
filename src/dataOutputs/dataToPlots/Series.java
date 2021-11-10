package dataOutputs.dataToPlots;

/**
 * Simple class to store the data of a series in a plot
 * 
 * @author DE OLIVEIRA MORENO NEVES, José
 */
public class Series {
    /** name of the series */
    private String name;
    /** column of the dataset to which is corresponds to */
    private int column;

    /**
     * Creates the series object for the given names and column
     * 
     * @param name   name of the series
     * @param column column of Results from which to extract the points of the
     *               series
     */
    public Series(String name, int column) {
        this.name = name;
        this.column = column;
    }

    /**
     * Gets the name of the series
     * 
     * @return name of the series
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the column of the series
     * 
     * @return column of the series
     */
    public int getColumn() {
        return this.column;
    }
}
