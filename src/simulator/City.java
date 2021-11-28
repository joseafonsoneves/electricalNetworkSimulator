package simulator;

import profiles.ProfilesGroup;
import profiles.Profile;

/**
 * A class that represents a city with all its producers and consumers
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class City {
    /** identifier name of the city */
    private String id;
    /** list of producers in the city */
    private ProfilesGroup producers;
    /** list of consumers in the city */
    private ProfilesGroup consumers;

    /**
     * Creates city with only the identifier of the city and initializes the lists
     * of producers and consumers
     * 
     * @param id the identifier of the city
     */
    public City(String id) {
        this.id = id;
        this.producers = new ProfilesGroup("Producers of " + id);
        this.consumers = new ProfilesGroup("Consumers of " + id);
    }

    /**
     * Adds a producer to the list of producers
     * 
     * @param producer producer to add
     */
    public void addProducer(Profile producer) {
        this.producers.add(producer);
    }

    /**
     * Removes a producer from the list of producers
     * 
     * @param producerId id of the producer to remove
     */
    public void removeProducer(String producerId) {
        this.producers.remove(producerId);
    }

    /**
     * Gets the group of producers
     * 
     * @return profiles group of producers
     */
    public ProfilesGroup getProducers() {
        return this.producers;
    }

    /**
     * Removes consumer from the list of consumers
     * 
     * @param consumer consumer to
     */
    public void addConsumer(Profile consumer) {
        this.consumers.add(consumer);
    }

    /**
     * Adds a consumer to the list of consumers
     * 
     * @param consumerId id of the consumer to remove
     */
    public void removeConsumer(String consumerId) {
        this.consumers.remove(consumerId);
    }

    /**
     * Gets the group of consumers
     * 
     * @return profiles group of consumers
     */
    public ProfilesGroup getConsumers() {
        return this.consumers;
    }

    /**
     * Gets the id of the city
     * 
     * @return String representing the id of the city
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the joint production of all producers in a city at a given day
     * 
     * @param day day in which to compute the production
     * @return array of production at the given day
     */
    public double[] getDayProduction(int day) {
        return this.producers.getDayPower(day);
    }

    /**
     * Returns the joint production of all producers in a city during a year
     * 
     * @return array of production during a year
     */
    public double[] getYearProduction() {
        return this.producers.getYearPower();
    }

    /**
     * Returns the joint consumption of all consumers in a city at a given day
     * 
     * @param day day in which to compute the consumption
     * @return array of consumption at the given day
     */
    public double[] getDayConsumption(int day) {
        return this.consumers.getDayPower(day);
    }

    /**
     * Returns the joint consumption of all producers in a city during a year
     * 
     * @return array of consumption during a year
     */
    public double[] getYearConsumption() {
        return this.consumers.getYearPower();
    }

    /**
     * Gets a description of the consumers inside a city
     * 
     * @return String describing the consumers of the city
     */
    public String getConsumersDescription() {
        return this.consumers.getDescription();
    }

    /**
     * Gets a description of the producers inside a city
     * 
     * @return String describing the producers of the city
     */
    public String getProducersDescription() {
        return this.producers.getDescription();
    }
}
