package profiles.parameters;

import results.SimType;

/***
 * This class represents a sinusoidal valued parameter with a period of a year,
 * specified min and max values and a day at which it reaches a maximum. It may
 * be used to approximate continuous transitions along the year like time of
 * sunlight in a day. For now we have considered that in each year only a cycle
 * can occur but if necessary it can be added that the number of cycles in the
 * year is specified by the user
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */

public class YearSinusoid implements Parameter {
    /** maximum value of the sinusoid */
    private double max;
    /** minimum value of the sinusoid */
    private double min;
    /** day at which the maximum value is obtained */
    private int dayAtMax;

    /**
     * Creates a parameter that changes sinusoidally through out the year
     * 
     * @param max      maximum value attained by the parameter
     * @param min      minimum value attained by the parameter
     * @param dayAtMax day at which the maximum value is attained
     */
    public YearSinusoid(double max, double min, int dayAtMax) {
        if (max > min && dayAtMax >= 0 && dayAtMax < SimType.YEAR.getLength()) {
            this.max = max;
            this.min = min;
            this.dayAtMax = dayAtMax;
        } else {
            throw new IllegalArgumentException("Max must be higher than min");
        }
    }

    public double getValueAtDay(int day) {
        return (this.max - this.min) / 2 * Math.cos(2 * Math.PI / SimType.YEAR.getLength() * (day - this.dayAtMax))
                + (this.max + this.min) / 2;
    }

    public boolean isAlwaysBiggerThan(double min) {
        return this.min > min;
    }

    public boolean isAlwaysSmallerThan(double max) {
        return this.max < max;
    }

    public String getDescription() {
        return "sinusoidal with maximum " + this.max + ", minimum " + this.min + ", and day of maximum "
                + this.dayAtMax;
    }
}
