package executables;

import city.City;
import dataOutputs.DataToCSV;
import dataOutputs.DataToConsole;
import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayConstantProfile;
import profiles.ProfilesGroup;
import profiles.parameters.YearConstant;

/**
 * First example of simulator. Created to show the data output capabilities
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim1_DataOutputs {
    public static void main(String[] args) {
        // creates the city
        City city = new City("City1", "outputs");

        // creates a house
        ProfilesGroup joseHouse = new ProfilesGroup("House of Jose");
        // adds machines to the house
        joseHouse.add(new DayConstantProfile("fridge", new YearConstant(200)));
        joseHouse.add(new DayConstantProfile("app2", new YearConstant(200)));

        // adds consumers to the city
        city.addConsumer(new DayConstantProfile("cons1", new YearConstant(100)));
        city.addConsumer(new DayConstantProfile("cons2", new YearConstant(100)));
        city.addConsumer(joseHouse);

        // creates and adds all the created producers to the city
        city.addProducer(new DayConstantProfile("prod1", new YearConstant(300)));
        city.addProducer(new DayConstantProfile("prod2", new YearConstant(600)));

        // adds all the data visualization tools to the city
        city.addDataOutput(new DataToCSV());
        city.addDataOutput(new DataToConsole());
        city.addDataOutput(new DataToPlots(800, 600, PlotType.POWER));
        city.addDataOutput(new DataToPlots(800, 600, PlotType.ENERGY));
        city.addDataOutput(new DataToPlots(800, 600, PlotType.POWER_LOSS));

        city.daySimulate(0);
    }
}
