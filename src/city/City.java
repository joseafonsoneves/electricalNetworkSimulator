package city;

import java.util.ArrayList;
import java.io.*;

import results.Results;
import results.SimType;
import dataOutputs.DataOutput;
import profiles.ProfilesGroup;
import profiles.Profile;

/**
 * A class that represents a city with all its producers and consumers and
 * allows to compute joint productions, consumptions, and energies. Moreover, it
 * contains a set of data visualization tools that can be applied to the
 * results. Therefore, this class acts not only as a way of storing the lists of
 * consumers and a list of producers but as well as a simulator
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class City {
    /** identifier name of the city */
    private String id;
    /** outputs folder name */
    private String outputsFolder;
    /** list of producers in the city */
    private ProfilesGroup producers;
    /** list of consumers in the city */
    private ProfilesGroup consumers;
    /** list of data output tools */
    private ArrayList<DataOutput> dataOutputs;

    /**
     * Creates city with only the identifier of the city and initializes the lists
     * of producers and consumers
     * 
     * @param id            the identifier of the city
     * @param outputsFolder the name of the folder of the outputs
     */
    public City(String id, String outputsFolder) {
        File f1 = new File(outputsFolder);
        f1.mkdir();

        this.id = id;
        this.outputsFolder = outputsFolder;
        this.producers = new ProfilesGroup("Producers");
        this.consumers = new ProfilesGroup("Consumers");
        this.dataOutputs = new ArrayList<DataOutput>();
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
     * Adds a consumer to the list of consumers
     * 
     * @param consumer consumer to add
     */
    public void addConsumer(Profile consumer) {
        this.consumers.add(consumer);
    }

    /**
     * Adds a data visualization tool to the list of tools
     * 
     * @param output data tool to add
     */
    public void addDataOutput(DataOutput output) {
        this.dataOutputs.add(output);
    }

    /**
     * Create file with information on the simulation performed
     * 
     * @param folderName folder in which to create the file
     */
    private void writeInfoFile(String folderName, Results dataset) {
        try {
            // This may throw a controllable exception so it is caught afterwards
            FileWriter writer = new FileWriter(folderName + "/info.txt");

            writer.write("City: " + this.id + "\n");
            writer.write("Made at: " + dataset.getDate() + "\n");
            writer.write("Was production enough?: " + String.valueOf(dataset.isProductionEnough()) + "\n\n");
            // Adds the description of each consumer so that it can be consulted
            writer.write(this.consumers.getDescription());
            // Adds the description of each producer so that it can be consulted
            writer.write(this.producers.getDescription());

            writer.close();
        } catch (IOException e) {
            System.err.println("Could not write info file. Proceeded without it");
        }
    }

    /**
     * Creates a folder for the simulation and applies the added visualization tools
     * 
     * @param dataset Results object obtained from the computations
     */
    private void outputData(Results dataset) {
        String folderName = this.outputsFolder + "/" + dataset.getSimName();

        // creates the simulation folder
        File f1 = new File(folderName);
        f1.mkdir();

        writeInfoFile(folderName, dataset);

        // applies the data visualization tools to the dataset giving the path
        // to the simulation folder to the tools
        for (DataOutput dataOutput : this.dataOutputs) {
            dataOutput.outputData(outputsFolder + "/" + dataset.getSimName(), dataset);
        }
    }

    /**
     * Computes and applies the visualization tools to the results of a day
     * 
     * @param day day in which to simulate
     */
    public void daySimulate(int day) {
        if (SimType.checkDay(day)) {
            this.outputData(new Results(this.id, SimType.DAY, this.producers.getDayPower(day),
                    this.consumers.getDayPower(day)));
        } else {
            throw new IllegalArgumentException("Day " + String.valueOf(day) + " is not valid");
        }
    }

    /**
     * Computes the production and the consumption and applies the visualization
     * tools to the results of a year
     */
    public void yearSimulate() {
        this.outputData(
                new Results(this.id, SimType.YEAR, this.producers.getYearPower(), this.consumers.getYearPower()));
    }
}
