package profiles.parameters;

/**
 * Interface for every parameter in the project like a duration a power. Allows
 * parameters to vary along the year in function of the day. This interface can
 * also be used to represent integer parameters if the user rounds the value it
 * returns. However, there may be problems with the comparison functions because
 * a double value may be, for instance, higher than a maximum but its rounded
 * value may not be. Therefore this is a problem that the user must take into
 * account when using this interface with integer parameters
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public interface Parameter {
    /**
     * Gets the value of the parameter at the given day
     * 
     * @param day day in which to compute
     * @return the value of the parameter at the given day
     */
    double getValueAtDay(int day);

    /**
     * Checks if the value of the parameter is for every day of the year bigger than
     * the min parameter
     * 
     * @param min the value must always be bigger than min so that it returns true
     * @return true if it is always bigger than min and false otherwise
     */
    boolean isAlwaysBiggerThan(double min);

    /**
     * Checks if the value of the parameter is for every day of the year smaller
     * than the max parameter
     * 
     * @param man the value must always be smaller than max so that it returns true
     * @return true if it is always smaller than max and false otherwise
     */
    boolean isAlwaysSmallerThan(double max);

    /**
     * Gets a String description of the behavior of the parameter
     * 
     * @return String description of the parameter
     */
    String getDescription();
}
