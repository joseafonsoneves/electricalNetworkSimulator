package profiles;

import profiles.parameters.Parameter;
import results.SimType;

/**
 * Describes the set of characteristics a day squared profile with periodicity
 * must have
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Square {
    /** Duration of the square form */
    private Parameter duration;
    /** Starting instant of the profile */
    private Parameter start;
    /** Number of repetitions */
    private int repetitionsNumber;
    /** Time period between squares */
    private int period;

    /**
     * Creates a non periodic square
     * 
     * @param duration desired duration of the square
     * @param start    desired start of the square
     */
    public Square(Parameter duration, Parameter start) {
        // verifies all introduced values before creating the object
        if (!duration.isAlwaysBiggerThan(0) || !duration.isAlwaysSmallerThan(SimType.DAY.getLength())) {
            throw new IllegalArgumentException("Duration value not valid");
        }
        if (!start.isAlwaysBiggerThan(-0.5) || !start.isAlwaysSmallerThan(SimType.DAY.getLength())) {
            throw new IllegalArgumentException("Square start not valid");
        }

        this.duration = duration;
        this.start = start;
        this.repetitionsNumber = 1;
        this.period = 0;
    }

    /**
     * Creates a square taking also into consideration periodicity
     * 
     * @param duration          desired duration of the square
     * @param start             desired start of the square
     * @param repetitionsNumber desired number of repetitions of the square
     * @param period            desired time between squares
     */
    public Square(Parameter duration, Parameter start, int repetitionsNumber, int period) {
        // verifies all introduced values before creating the object
        if (!duration.isAlwaysBiggerThan(0) || !duration.isAlwaysSmallerThan(SimType.DAY.getLength())) {
            throw new IllegalArgumentException("Duration value not valid");
        }
        if (!start.isAlwaysBiggerThan(-0.5) || !start.isAlwaysSmallerThan(SimType.DAY.getLength())) {
            throw new IllegalArgumentException("Square start not valid");
        }
        if (repetitionsNumber < 1) {
            throw new IllegalArgumentException("At least one repetition is needed");
        }
        // When the repetitions number is 1 it means that the profile is
        // not periodic so it is not important to verify the value of the period. On the
        // other hand, if the number of repetitions is bigger than 1, the profile is
        // periodic so one must verify the period
        if (repetitionsNumber > 1 && !SimType.checkMinute(period)) {
            throw new IllegalArgumentException("Period not valid for periodic profile");
        }
        if (repetitionsNumber > 1 && !duration.isAlwaysSmallerThan(period)) {
            throw new IllegalArgumentException("Duration too big for period causes repetitions overlapping");
        }

        this.duration = duration;
        this.start = start;
        this.repetitionsNumber = repetitionsNumber;
        this.period = period;
    }

    /**
     * Gets the duration of the square at a given day. Since the duration and start
     * values were tested to maximum levels that are well below the maximum of int
     * in normal machines (machines in which ints are at least specified with 2
     * bytes) this type conversion from long to int is well defined
     * 
     * @param day day in which to compute
     * @return duration of the square
     */
    public int getDuration(int day) {
        return (int) Math.round(this.duration.getValueAtDay(day));
    }

    /**
     * Gets the start minute of the square at a given day. Since the duration and
     * start values were tested to maximum levels that are well below the maximum of
     * int in normal machines (machines in which ints are at least specified with 2
     * bytes) this type conversion from long to int is well defined
     * 
     * @param day day in which to compute
     * @return start minute of the square
     */
    public int getStart(int day) {
        return (int) Math.round(this.start.getValueAtDay(day));
    }

    /**
     * Gets the number of repetitions of the square
     * 
     * @return number of repetitions of the square
     */
    public int getRepetitionsNumber() {
        return this.repetitionsNumber;
    }

    /**
     * Gets the period of the square
     * 
     * @return period of the square
     */
    public int getPeriod() {
        return this.period;
    }

    /**
     * Gets the description segment corresponding to the squared part so that it can
     * be added to the description of a profile
     * 
     * @return new String specifying the square part of the profile
     */
    public String toString() {
        String des = "Lasts for in minutes " + this.duration.getDescription()
                + " minutes starting at minute of the day " + this.duration.getDescription();

        if (this.repetitionsNumber > 1) {
            return des + ". Repeated " + this.repetitionsNumber + " time(s) with a period of "
                    + SimType.minuteToMinuteHour(this.period);
        } else {
            return des + ". Not periodic";
        }
    }

    /**
     * Gets the average power of a square in a day from the average power in itself
     * 
     * @param averagePowerInSquare average power only inside the square
     * @param day                  day in which to compute
     * @return average power in the full day
     */
    public double getAveragePowerInDay(double averagePowerInSquare, int day) {
        return averagePowerInSquare * this.getDuration(day) / SimType.DAY.getLength() * this.repetitionsNumber;
    }
}
