package results;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Enum of the types of the simulations performed. Each value of the enum saves
 * information on the type of simulation performed
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public enum SimType {
    /** Describes the day simulation type and associated values */
    DAY("Day", "Minute", 1.0 / 60, 60 * 24),
    /** Describes the year simulation type and associated values */
    YEAR("Year", "Day", 24, 365);

    /** Corresponding String description */
    private final String id;
    /** Corresponding x label description */
    private final String xLabel;
    /** Corresponding sampling interval value */
    private final double interval;
    /** Corresponding number of instants */
    private final int length;

    /** The year to be taken as representative of the simulation */
    private static final Year simulatedYear = Year.of(2021);

    /**
     * In 2021, the year started at a friday, so it is considered as the first week
     * day of the simulated year. In this project, the days of the week are counted
     * from 0 to 6 being 0 the sunday
     */
    private static final int firstWeekday = 5;

    /**
     * Creates an instance of a new value of the enum
     * 
     * @param id       corresponding String id
     * @param xLabel   corresponding label of the x axis
     * @param interval corresponding time between two samples in this simulation
     *                 type
     * @param length   corresponding number of samples in this simulation type
     */
    SimType(String id, String xLabel, double interval, int length) {
        this.id = id;
        this.xLabel = xLabel;
        this.interval = interval;
        this.length = length;
    }

    /**
     * Gets the id of the simulation type
     * 
     * @return id of the simulation type
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the label of the x axis of the simulation type
     * 
     * @return label of the x axis of the simulation type
     */
    public String getXLabel() {
        return this.xLabel;
    }

    /**
     * Gets the time between two samples of the simulation type
     * 
     * @return time between two samples of the simulation type
     */
    public double getInterval() {
        return this.interval;
    }

    /**
     * Gets the number of samples of the simulation type
     * 
     * @return number of samples of the simulation type
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Checks wether or not a day is valid
     * 
     * @param day number of day starting at 0 for the 1st january to check
     * @return true if the number is valid and false if the number is not
     */
    public static boolean checkDay(int day) {
        return day >= 0 && day < SimType.YEAR.getLength();
    }

    /**
     * Checks wether or not a minute is valid
     * 
     * @param minute number of minute starting at 0
     * @return true if the number is valid and false if it is not
     */
    public static boolean checkMinute(int minute) {
        return minute >= 0 && minute < SimType.DAY.getLength();
    }

    /**
     * Turns a minute from 0 to 1440 into a hour minute format string
     * 
     * @param minute minute to convert
     * @return string containing the desired format
     */
    public static String minuteToMinuteHour(int minute) {
        return String.format("%02d", minute / 60) + "h" + String.format("%02d", minute % 60) + "min";
    }

    /**
     * Turns a day from 0 to 364 into a month day format considering the simulated
     * year defined above
     * 
     * @param day day to convert
     * @return string containing the desired format
     */
    public static String dayToMonthDay(int day) {
        // gets the date. In our project, we considered the day to be from 0 to 364 but
        // in the java libraries it is considered to be from 1 to 365 so it is converted
        LocalDate ld = simulatedYear.atDay(day + 1);

        // creates a formatter for the date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM");

        return dtf.format(ld);
    }

    /**
     * Gets the current execution date and time
     * 
     * @return Formatted string containing the current date and time
     */
    public static String nowDateTime() {
        // creates a formatter for the date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        // gets the current date and time
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    /**
     * From a day in the usual format (int from 0 to 364) gets the corresponding
     * weekday
     * 
     * @param day day to convert to weekday
     * @return weekday of the corresponding day
     */
    public static int getDayOfTheWeek(int day) {
        int res = day % 7 + firstWeekday;

        return res > 6 ? res - 7 : res;
    }

    /**
     * Get name of the day of the week from the day of the week number
     * 
     * @param dayOfTheWeek number of the day of the week
     * @return returns the name of that day
     */
    public static String getNameOfDayOfTheWeek(int dayOfTheWeek) {
        switch (dayOfTheWeek) {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "Undefined";
        }
    }

    /**
     * Gets an array of strings containing the ids of the available simulation types
     * 
     * @return array of strings containing the ids of the available simulation types
     */
    public static String[] getPossibleSimTypes() {
        return new String[] { DAY.id, YEAR.id };
    }

    public static int[] getPossibleDays() {
        int[] res = new int[YEAR.getLength()];

        for (int i = 0; i < res.length; i++) {
            res[i] = i;
        }

        return res;
    }
};
