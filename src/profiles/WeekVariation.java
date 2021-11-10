package profiles;

import results.SimType;

/**
 * Describes the set of characteristics a weekly variable profile must follow
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class WeekVariation {
    /**
     * Days of the week in which the profile is active: null -> all days, first
     * element = -1 -> only weekdays, first element = -2 -> only weekends
     */
    private int[] daysOfTheWeek;

    /**
     * Creates the desired week variation model
     * 
     * @param daysOfTheWeek days of the week in which it is active
     */
    public WeekVariation(int... daysOfTheWeek) {
        // verifies all introduced values before creating the object
        if (daysOfTheWeek.length > 0 && (daysOfTheWeek[0] < -2 || daysOfTheWeek[0] > 6)) {
            throw new IllegalArgumentException("First day of the week specified not valid");
        }
        for (int i = 1; i < daysOfTheWeek.length; i++) {
            // checks all days of the week specified
            if (daysOfTheWeek[i] < 0 || daysOfTheWeek[i] > 6) {
                throw new IllegalArgumentException("Value of the days of the week not valid");
            }
        }

        this.daysOfTheWeek = daysOfTheWeek;
    }

    /**
     * Checks wether or not the given day is within the week period of operation of
     * the profile
     * 
     * @param day day to check
     * @return true if it is within or false if it is not within
     */
    public boolean checkDayInWeek(int day) {
        // gets the day of the week corresponding to the day to check
        int dayInDaysOfTheWeek = SimType.getDayOfTheWeek(day);

        // If there is differences in the operation relative to the days of the week
        if (this.daysOfTheWeek.length > 0) {
            // weekdays case
            if (this.daysOfTheWeek[0] == -1) {
                return dayInDaysOfTheWeek >= 1 && dayInDaysOfTheWeek <= 5;
            }
            // weekends case
            if (this.daysOfTheWeek[0] == -2) {
                return dayInDaysOfTheWeek == 0 || dayInDaysOfTheWeek == 6;
            }

            // if not any of the particular cases, surfs through the days of the week in
            // which there is operation
            for (int dayOfTheWeek : this.daysOfTheWeek) {
                // and if the day of the week corresponding to the day to be checked is in that
                // list, returns valid
                if (dayInDaysOfTheWeek == dayOfTheWeek) {
                    return true;
                }
            }
            return false;
        }

        return true;
    }

    /**
     * Gets the string description of the year variation part so that it can be
     * added to following descriptions of a profile
     * 
     * @return String description of the periodic and yearly parts of the son's
     *         profile
     */
    public String toString() {
        String des;

        if (this.daysOfTheWeek.length == 0) {
            des = "Lasts the whole week";
        } else if (this.daysOfTheWeek[0] == -1) {
            des = "Comprises only the weekdays";
        } else if (this.daysOfTheWeek[0] == -2) {
            des = "Comprises only the weekends";
        } else {
            des = "Comprises only ";
            for (int dayOfTheWeek : this.daysOfTheWeek) {
                des = des + SimType.getNameOfDayOfTheWeek(dayOfTheWeek) + ", ";
            }
        }

        return des;
    }
}
