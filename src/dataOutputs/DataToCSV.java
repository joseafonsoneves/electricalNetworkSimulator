package dataOutputs;

import java.io.*;
import com.opencsv.CSVWriter;

import results.Results;

/**
 * Prints the obtained dataset into a CSV file whose format is minute;power
 * produced;power consumed;energy produced;energy consumed. It was adapted from
 * the following website
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Neves
 * @see https://www.geeksforgeeks.org/writing-a-csv-file-in-java-using-opencsv/
 */
public class DataToCSV {
    // it cannot be static because it has to implement the interface
    public static void outputData(Results dataset) {
        double consumption;
        double production;
        double consumedEnergy;
        double producedEnergy;

        // creates a file object for the csv file to be created
        File f1 = new File(dataset.getSimFolder() + "/data.csv");

        try {
            // create FileWriter object with file as parameter. This may throw a
            // controllable exception so it is caught afterwards
            FileWriter outputFile = new FileWriter(f1);

            // create CSVWriter with ';' as separator
            CSVWriter writer = new CSVWriter(outputFile, ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            // for every row in dataset creates an array of strings consisting
            // of the minute and the row values of dataset
            for (int i = 0; i < dataset.getProductionLen(); i++) {
                consumption = Math.round(dataset.getConsumptionItem(i) * 100) / 100.0;
                production = Math.round(dataset.getProductionItem(i) * 100) / 100.0;
                consumedEnergy = Math.round(dataset.getEnergyConsumedItem(i) * 100) / 100.0;
                producedEnergy = Math.round(dataset.getEnergyProducedItem(i) * 100) / 100.0;

                writer.writeNext(new String[] { String.valueOf(i), String.valueOf(consumption),
                        String.valueOf(production), String.valueOf(consumedEnergy), String.valueOf(producedEnergy) });
            }

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not write csv file. Proceeded without it");
        }
    }
}
