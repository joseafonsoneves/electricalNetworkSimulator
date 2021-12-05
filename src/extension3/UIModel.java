package extension3;

import java.io.File;
import java.util.HashMap;

import javax.swing.tree.TreePath;

import results.SimType;
import simulator.City;

/**
 * Saves the data needed for the user interface and also how to treat it
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class UIModel {
    /** Reference to the cities to which apply the actions of the controller */
    private HashMap<String, City> cities;
    /** Saves the type of simulation to perform */
    private SimType simType;
    /** If the simulation is of type day, it needs to know it is */
    private int day;
    /** Saves the paths selected */
    private TreePath[] paths;
    /** Configuration file from which the cities were taken out */
    private File citiesDataFile;

    /**
     * Initializes the model with no cities
     */
    public UIModel() {
        this.cities = new HashMap<String, City>();

        // When the model is created it is automatically set to a day simulation at
        // the 180th day of the year
        this.simType = SimType.DAY;
        this.day = 180;
    }

    /**
     * Gets a city based on its id
     * 
     * @param id id of the city to get
     * @return city identified by the id
     */
    public City getCity(String id) {
        return this.cities.get(id);
    }

    /**
     * Gets the group of cities present in the model
     * 
     * @param city
     */
    public HashMap<String, City> getCities() {
        return this.cities;
    }

    /**
     * Adds a city to the model
     * 
     * @param city city to add
     */
    public void addCity(City city) {
        this.cities.put(city.getId(), city);
    }

    /**
     * Sets the group of cities to use
     * 
     * @param cities new group of cities to use
     */
    public void setCities(HashMap<String, City> cities) {
        this.cities = cities;
    }

    /**
     * Returns wether or not the current model has cities to present
     * 
     * @return true if the model has cities and false if not
     */
    public boolean hasCities() {
        return this.cities != null && this.cities.size() >= 1;
    }

    /**
     * Gets the type of simulation that is currently being performed
     * 
     * @return type of simulation that is currently being performed
     */
    public SimType getSimType() {
        return this.simType;
    }

    /**
     * Gets the day in which to perform the simulation
     * 
     * @return day in which to perform the simulation
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Gets the selected paths
     * 
     * @return selected paths
     */
    protected TreePath[] getPaths() {
        return this.paths;
    }

    /**
     * Gets the cities file
     * 
     * @return the cities file saved
     */
    public File getCitiesFile() {
        return this.citiesDataFile;
    }

    /**
     * Sets the simulation type
     * 
     * @param simType new type of simulation to perform
     */
    public void setSimType(SimType simType) {
        this.simType = simType;
    }

    /**
     * Sets the day of the simulation to perform
     * 
     * @param day new day of simulation
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Just sets the cities file to the value given
     * 
     * @param file new file of cities
     */
    public void setCitiesFile(File file) {
        this.citiesDataFile = file;
    }

    /**
     * Sets the selected paths
     */
    protected void setPaths(TreePath[] newPaths) {
        this.paths = newPaths;
    }
}
