package integration;

import java.util.ArrayList;
import java.util.HashMap;

import extension1.CityWithPosition;
import extension1.Map;
import extension3.UIModel;
import results.SimType;
import simulator.City;

public class UIModelIntegration extends UIModel {
    /** Saves the losses that were computed */
    private HashMap<String, double[]> losses;

    /**
     * Sets the losses given
     * 
     * @param losses losses to set
     */
    public void setLosses(HashMap<String, double[]> losses) {
        this.losses = losses;
    }

    /**
     * Gets the losses given
     * 
     * @return losses stored
     */
    public HashMap<String, double[]> getLosses() {
        return this.losses;
    }

    /**
     * Returns the loss corresponding to the given id
     * 
     * @param id identifier of the loss
     * @return loss corresponding to the id
     */
    public double[] getLoss(String id) {
        return this.losses.get(id);
    }

    /**
     * Returns wether or not losses have been stored
     * 
     * @return true if yes and false if not
     */
    public boolean hasLosses() {
        return losses != null && losses.size() >= 1;
    }

    /**
     * Although one starts by only creating cities, when the computation of losses
     * is needed one has to convert cities to cities with positions so that
     * positions can be added to the cities
     */
    public void convertCitiesToCitiesWithPosition() {
        // for every city in the group
        for (City city : this.getCities().values()) {
            // if city is not an instance of CityWithPosition
            if (!(city instanceof CityWithPosition)) {
                // puts a city with position at the place of that city in the hash map
                this.getCities().put(city.getId(), new CityWithPosition(city));
            }
        }
    }

    /**
     * Extension 1 receives an hash map of cities as keys and numbers as indexes so
     * one has to convert from the typical hash map of strings and cities to this
     * new data format
     * 
     * @return hash map of CityWithPosition as key and Integer as Value
     */
    public HashMap<CityWithPosition, Integer> convertCitiesToGraph() {
        HashMap<String, City> cities = this.getCities();
        HashMap<CityWithPosition, Integer> graph = new HashMap<CityWithPosition, Integer>();

        // the index of the first city
        int count = 1;
        // for every city in the group
        for (City city : cities.values()) {
            // if there is no error of conversion between classes
            if (city instanceof CityWithPosition) {
                // adds the city to the graph of cities
                graph.put((CityWithPosition) city, count);
            } else {
                // throws an exception
                throw new RuntimeException("Unexpected error: city is not an instance of CityWithPosition");
            }
            // increases the index
            count = count + 1;
        }

        return graph;
    }

    /**
     * The extension 1 needs to know which city is the one that contains the
     * producers because it only works for a single city with producers so this
     * method verifies if only one city has producers in the cities given and if it
     * is the case returns the city that has them
     * 
     * @return only city with producers if there is only one or null if there is
     *         none or more than one
     */
    public CityWithPosition getProducerCity() {
        // saves if a city with producers has already been seen
        boolean thereIsOneCityWithProducers = false;
        CityWithPosition cityWithProducers = null;

        for (City city : this.getCities().values()) {
            // every time it sees a city with producers
            if (city.getProducers().size() > 0) {
                // if no other city with producers has already been registered
                if (!thereIsOneCityWithProducers) {
                    // stores that it has seen one
                    thereIsOneCityWithProducers = true;
                    // saves it and keeps on checking the others
                    cityWithProducers = (CityWithPosition) city;
                }
                // if another city with producers has already been registered
                else {
                    // there two cities with producers so extension 1 is not applicable
                    // so exits the method with an error
                    return null;
                }
            }
        }

        // if there is no city with producers it gets an error as well
        if (!thereIsOneCityWithProducers) {
            return null;
        } else {
            return cityWithProducers;
        }
    }

    /**
     * Computes the losses for this model with the connectivity table given and in
     * the simulation type required
     * 
     * @param simType     type of simulation to perform day or year
     * @param connections table of connectivity between the cities
     * @return true if the computations were successful and false if it not
     */
    public boolean computeLosses(int[][] connections) {
        // converts the list of cities to the data format used in the extension 1
        HashMap<CityWithPosition, Integer> graph = this.convertCitiesToGraph();
        // creates a map according to extension 1
        Map newMap = new Map(connections, graph);
        // from here it has to discover which is the city with producers so that it can
        // pass it to extension 1
        CityWithPosition cityWithProducers = this.getProducerCity();
        // if it could find one and only one city with producers
        if (cityWithProducers != null) {
            // it gets the road map and the distance map
            HashMap<String, ArrayList<Integer>> roadMap = newMap.roadMap(cityWithProducers);
            HashMap<String, Double> distanceMap = newMap.distanceMap(roadMap);
            // we could not reach an agreement over the correctness of this approach but it
            // was presented here to show that it can nevertheless be integrated
            if (this.getSimType() == SimType.DAY) {
                losses = newMap.lossDay(distanceMap);
            } else {
                losses = newMap.lossYear(distanceMap);
            }
            // returns that the computations were successful
            return true;
        } else {
            // returns that the computations were not successful
            return false;
        }
    }
}
