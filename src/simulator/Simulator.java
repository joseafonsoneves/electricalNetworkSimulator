package simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import results.Results;
import results.SimType;

/**
 * This class receives the reference of a city and allows to compute joint
 * productions and consumptions under the format of a Results object which on
 * his own will also conduct further analysis on the results
 */

public class Simulator {
    /** outputs folder name */
    private String outputsFolder;

    /**
     * Creates a simulator giving it the folder to place its outputs
     * 
     * @param outputsFolder the name of the folder of the outputs
     */
    public Simulator(String outputsFolder) {
        File f1 = new File(outputsFolder);
        f1.mkdir();

        this.outputsFolder = outputsFolder;
    }

    /**
     * Creates a folder for the simulation performed with a file describing it
     * 
     * @param dataset results obtained at the simulation
     * @param city    city object on which the simulation was performed
     */
    private void createSimFolder(Results dataset, City city) {
        // creates the simulation folder
        File f1 = new File(dataset.getSimFolder());
        f1.mkdir();

        // tries to write the simulation info file
        try {
            // This may throw a controllable exception so it is caught afterwards
            FileWriter writer = new FileWriter(dataset.getSimFolder() + "/info.txt");

            writer.write("City: " + dataset.getCity() + "\n");
            writer.write("Made at: " + dataset.getDate() + "\n");
            writer.write("Was production enough?: " + String.valueOf(dataset.isProductionEnough()) + "\n\n");
            // Adds the description of each consumer so that it can be consulted
            writer.write(city.getConsumersDescription());
            // Adds the description of each producer so that it can be consulted
            writer.write(city.getProducersDescription());

            writer.close();
        } catch (IOException e) {
            System.err.println("Could not write info file. Proceeded without it");
        }
    }

    /**
     * Computes the different results of the simulator in a specified day
     * 
     * @param day  day in which to simulate
     * @param city city for which to do the simulation
     * @return the results obtained in the simulation
     */
    public Results daySimulate(City city, int day) {
        if (!SimType.checkDay(day)) {
            throw new IllegalArgumentException("Day " + String.valueOf(day) + " is not valid");
        }

        // Gets the results of the simulation of a city
        Results dataset = new Results(city.getId(), SimType.DAY, this.outputsFolder, city.getDayProduction(day),
                city.getDayConsumption(day));

        this.createSimFolder(dataset, city);

        return dataset;
    }

    /**
     * Computes the different results of the simulator in a year
     * 
     * @param city city for which to do the simulation
     * @return the results obtained in the simulation
     */
    public Results yearSimulate(City city) {
        // Gets the results of the simulation of a city
        Results dataset = new Results(city.getId(), SimType.DAY, this.outputsFolder, city.getYearProduction(),
                city.getYearConsumption());

        this.createSimFolder(dataset, city);

        return dataset;
    }
}