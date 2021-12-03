package profiles;

import java.util.function.Function;

import results.SimType;

/**
 * Profile that is similar to a square wave. It is the basis for models that are
 * delimited in the time of the day. It may serve to model devices or producers
 * that have constant power when turned on but that are not always turned on.
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public abstract class SquaredProfile {
    /** String id of the profile */
    private String id;
    /** Square behavior descriptor */
    private Square square;
    /** Week variation descriptor */
    private WeekVariation weekVar;
    /** Year variation descriptor */
    private YearVariation yearVar;

    /**
     * Creates a squared profile
     * 
     * @param id      Desired String id of the profile
     * @param square  Desired model of daily squared part
     * @param weekVar Desired model of the variation throughout the week
     * @param yearVar Desired model of the variation throughout the year
     */
    public SquaredProfile(String id, Square square, WeekVariation weekVar, YearVariation yearVar) {
        this.id = id;
        this.square = square;
        this.weekVar = weekVar;
        this.yearVar = yearVar;
    }

    public double[] getDayPower(int day, Function<Integer, Double> powerAtMinute) {
        if (SimType.checkDay(day)) {
            double[] res = new double[SimType.DAY.getLength()];

            // if the day checks in the week and in the year
            if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
                int instant;
                // goes for the number of repetitions specified
                for (int j = 1; j <= this.square.getRepetitionsNumber(); j++) {
                    // and inside each square
                    for (int i = 0; i < this.square.getDuration(day); i++) {
                        // finding the corresponding instant
                        instant = this.square.getStart(day) + (j - 1) * this.square.getPeriod() + i;

                        // this part allows to specify instants whose period and number of repetitions
                        // cause its squares to be on the following days
                        while (instant >= SimType.DAY.getLength()) {
                            instant = instant - SimType.DAY.getLength();
                        }

                        // the functions given only work inside a period. Therefore, although the
                        // instant is the minute of the day to fill in. The instant in which the power
                        // is computed refers to the reference frame of the period
                        res[instant] = powerAtMinute.apply(i);
                    }
                }
            }

            return res;
        } else {
            throw new IllegalArgumentException("Day " + day + " is not valid");
        }
    }

    public double[] getYearPower(Function<Integer, Double> averagePowerAtDayInSquare) {
        double[] res = new double[SimType.YEAR.getLength()];

        // fills every day with its medium power
        for (int i = 0; i < res.length; i++) {
            if (weekVar.checkDayInWeek(i) && yearVar.checkDayInYear(i)) {
                res[i] = this.square.getAveragePowerInDay(averagePowerAtDayInSquare.apply(i), i);
            }
        }

        return res;
    }

    /**
     * Gets the String id of the profile
     * 
     * @return String id of the profile
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the duration of the square of the profile in function of the day
     * 
     * @param day day in which to compute
     * @return duration of the square of the profile
     */
    public int getDuration(int day) {
        return this.square.getDuration(day);
    }

    /**
     * Gets the minute start of the square of the profile in function of the day
     * 
     * @param day day in which to compute
     * @return minute start of the square of the profile
     */
    public int getStart(int day) {
        return this.square.getStart(day);
    }

    /**
     * Gets a description that will be added to the particular ones of the sons'
     * 
     * @return String partial description
     */
    public String toString() {
        return square.toString() + ". " + yearVar.toString() + ". " + weekVar.toString();
    }
}
