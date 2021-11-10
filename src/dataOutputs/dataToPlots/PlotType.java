package dataOutputs.dataToPlots;

/**
 * Enum of the different types of plots that can be asked for. It works as a way
 * of refactoring the code of the class DataToPlots and it enables the fast
 * addition of plots of other quantities
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public enum PlotType {
    /** Describes the power plot type and associated values */
    POWER("Power", "W", new Series("Consumed", 0), new Series("Produced", 1)),
    /** Describes the energy plot type and associated values */
    ENERGY("Energy", "Wh", new Series("Consumed", 2), new Series("Produced", 3)),
    /** Describes the power loss plot type and associated values */
    POWER_LOSS("Power loss", "W", new Series("Lost", 4));

    /** name of the quantity to be represented */
    private final String id;
    /** unit of the quantity to be represented */
    private final String unit;
    /** array of Series objects */
    private final Series[] series;

    /**
     * Creates each PlotType instance for the enum
     * 
     * @param id     identifies the type of plot
     * @param unit   unit of the results shown
     * @param series series of points to add to the plots
     */
    PlotType(String id, String unit, Series... series) {
        this.id = id;
        this.unit = unit;
        this.series = series;
    }

    /**
     * Gets the id of the instance
     * 
     * @return id of the instance
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the units of the instance
     * 
     * @return units of the instance
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * Get the name of the series in the following index
     * 
     * @param index index of the series to choose
     * @return name of the chosen series
     */
    public String getSeriesName(int index) {
        return this.series[index].getName();
    }

    /**
     * Get the column of the dataset of the series in the following index
     * 
     * @param index index of the series to choose
     * @return column of the chosen series
     */
    public int getColumn(int index) {
        return this.series[index].getColumn();
    }

    /**
     * Gets the number of series in each instance
     * 
     * @return number of series
     */
    public int getNumSeries() {
        return this.series.length;
    }
}
