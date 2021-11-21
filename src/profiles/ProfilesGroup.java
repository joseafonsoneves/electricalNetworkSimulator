package profiles;

import java.util.Collection;
import java.util.HashMap;

import results.SimType;

/**
 * This class represents a group of profiles even though it still conforms to
 * the profile interface
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class ProfilesGroup implements Profile {
    /**
     * Map of profiles in the group. It makes more sense to store the profiles in an
     * hash map than in an array list because if want to access one of them through
     * the group it makes more sense to access it based on its name than on the
     * position in which it was entered in the list. It also obliges the user to
     * name different objects with different ids
     */
    private HashMap<String, Profile> map;
    /** String containing the identifier name of the profile */
    private String id;

    /**
     * Constructor for a group of profiles
     * 
     * @param id desired name of the group of profiles
     */
    public ProfilesGroup(String id) {
        this.map = new HashMap<String, Profile>();
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Adds a profile to the map of profiles
     * 
     * @param profile profile to add
     */
    public void add(Profile profile) {
        this.map.put(profile.getId(), profile);
    }

    /**
     * Removes a profile from the map of profiles. For now it does not make a lot of
     * sense to remove profiles from the group but this is a good way of showing
     * that a hash map is a better choice for organizing the profiles
     * 
     * @param id String id of the profile to remove
     */
    public void remove(String id) {
        this.map.remove(id);
    }

    /**
     * Gets a collection of the profiles in the group
     * 
     * @return collection of profiles in the group
     */
    public Collection<Profile> getProfiles() {
        return this.map.values();
    }

    /**
     * Gets the joint power of all profiles in the group in every minute of the day
     * 
     * @param day day in which to compute the power
     * @return joint power in every minute of the day
     */
    public double[] getDayPower(int day) {
        // in Java initializations are made to zero values which is important since it
        // means the vectors will only contain zeros if no profile is specified
        double[] res = new double[SimType.DAY.getLength()];
        double[] power;

        // sums profile by profile the power vectors of every profile
        for (Profile profile : map.values()) {
            power = profile.getDayPower(day);

            for (int i = 0; i < res.length; i++) {
                res[i] = res[i] + power[i];
            }
        }

        return res;
    }

    /**
     * Gets the joint power of all profiles in the city in every day of the year
     * 
     * @return joint power in every day of the year
     */
    public double[] getYearPower() {
        // in Java initializations are made to zero values which is important since it
        // means the vectors will only contain zeros if no profile is specified
        double[] res = new double[SimType.YEAR.getLength()];
        double[] power;

        // sums profile by profile the power vectors of every profile
        for (Profile profile : map.values()) {
            power = profile.getYearPower();

            for (int i = 0; i < res.length; i++) {
                res[i] = res[i] + power[i];
            }
        }

        return res;
    }

    /**
     * I had the idea of creating pie charts with the energy production or
     * consumption percentage as a way of easily comparing them. However, I could
     * not find a library that was easy enough to use. I looked at libraries like
     * jfreechart or openjfx but it was only possible to get their source code and
     * not a easy to use .jar file so I decided to leave this idea to the upcoming
     * phases of the project. Nevertheless, I leave here a sample of the procedure I
     * envisaged for completing the task. For each profile, I would compute its
     * corresponding energy and I would save it into a HashMap with the key being
     * the profile id. Then I would create a data visualization tool that would
     * retrieve this HashMap and use the keys as legend and the values to draw the
     * pie
     */
    /*
     * public HashMap<String, Double> getDayEnergyDistribution(int day) { double
     * power[], energy = 0; HashMap<String, Double> distribution = new
     * HashMap<String, Double>();
     * 
     * for (ProfileInterface profile : map.values()) { power =
     * profile.getDayPower(day);
     * 
     * for (int i = 0; i < power.length; i++) { energy = energy + power[i] *
     * SimType.DAY.getInterval(); }
     * 
     * distribution.put(profile.getId(), energy); }
     * 
     * return distribution; }
     * 
     * public HashMap<String, Double> getYearDistribution() { double power[], energy
     * = 0; HashMap<String, Double> distribution = new HashMap<String, Double>();
     * 
     * for (profileInterface profile : map.values()) { power =
     * profile.getYearPower();
     * 
     * for (int i = 0; i < power.length; i++) { energy = energy + power[i] *
     * SimType.YEAR.getInterval(); }
     * 
     * distribution.put(profile.getId(), energy); }
     * 
     * return distribution; }
     */

    public String getDescription() {
        String des = "{\n" + this.id + "\n";

        // joins all the descriptions of the profiles on the map and separates them in
        // lines
        for (Profile profile : map.values()) {
            des = des + profile.getDescription() + "\n";
        }
        des = des + "}\n";

        return des;
    }
}