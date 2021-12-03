package profiles;

import results.SimType;

/**
 * Describes the set of characteristics a yearly variable profile must follow
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class YearVariation {
    /** First day of the year of operation */
    private int firstDay;
    /** Number of days of the year of operation */
    private int daysNumber;

    /**
     * Creates a year variation model in which the profile is active throughout all
     * the year
     */
    public YearVariation() {
        this.firstDay = 0;
        this.daysNumber = SimType.YEAR.getLength();
    }

    /**
     * Creates a year variation model in which the profile is active from a specific
     * day to another
     * 
     * @param firstDay   first day of the year in which the profile is active
     * @param daysNumber number of days in which the profile is active starting from
     *                   the first day
     */
    public YearVariation(int firstDay, int daysNumber) {
        // verifies all introduced values before creating the object
        if (!SimType.checkDay(firstDay)) {
            throw new IllegalArgumentException("First day of operation not valid");
        }
        if (!SimType.checkDay(daysNumber)) {
            throw new IllegalArgumentException("Number of days of operation not valid");
        }

        this.firstDay = firstDay;
        this.daysNumber = daysNumber;
    }

    /**
     * Checks wether or not the given day is within the yearly period of operation
     * of the profile
     * 
     * @param day day to check
     * @return true if it is within or false if it is not within
     */
    public boolean checkDayInYear(int day) {
        boolean res = day >= this.firstDay && day <= this.firstDay + this.daysNumber;

        // however one may construct an operation period that starts in a year and
        // finishes in the next. Therefore, one must also verify if the day may be in
        // the "next year" still inside the operation period
        day = day + SimType.YEAR.getLength();
        res = res || (day >= this.firstDay && day <= this.firstDay + this.daysNumber);

        return res;
    }

    /**
     * Gets the description segment corresponding to the year variation part so that
     * it can be added to the description of a profile
     * 
     * @return String description of the yearly part
     */
    public String toString() {
        if (this.firstDay == 0 && this.daysNumber == SimType.YEAR.getLength()) {
            return "Lasts the whole year";
        } else {
            return "Starts at " + SimType.dayToMonthDay(this.firstDay) + " and lasts for " + this.daysNumber + " days";
        }
    }
}
