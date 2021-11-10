package profiles.parameters;

/**
 * This class represents a constant valued during year parameter
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class YearConstant implements Parameter {
    /** Constant value of the parameter throughout the year */
    private double value;

    /**
     * Creates a parameter that does not change throughout the year
     * 
     * @param value value to set it to
     */
    public YearConstant(double value) {
        this.value = value;
    }

    // Gets the value at a certain day which is just a way of implementing
    // the interface since the value is independent of the day
    public double getValueAtDay(int day) {
        return value;
    }

    public boolean isAlwaysBiggerThan(double min) {
        return this.value > min;
    }

    public boolean isAlwaysSmallerThan(double max) {
        return this.value < max;
    }

    public String getDescription() {
        return String.valueOf(this.value);
    }
}
