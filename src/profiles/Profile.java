package profiles;

/**
 * An interface for every implemented profile in the project. By profile, one
 * must understand any producer, consumer, or part of their power created since
 * all correspond to vectors of power throughout a day or year. For now, it is
 * still possible not to distinguish between producers or consumers. If in the
 * future this is not possible, one may divide this interface in two
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public interface Profile {
    /**
     * Gets the id of the profile
     * 
     * @return String id of the profile
     */
    String getId();

    /**
     * Gets the power of the profile in the given day
     * 
     * @param day
     * @return array of doubles corresponding to the power in each minute of the day
     */
    double[] getDayPower(int day);

    /**
     * Gets the yearly power of the profile
     * 
     * @return array of doubles corresponding to the power in each day of the year
     */
    double[] getYearPower();

    /**
     * Gets a string containing a description of the behavior of the profile and its
     * attributes
     * 
     * @return string description of the profile
     */
    String getDescription();
}
