package executables;

import city.City;
import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayConstantProfile;
import profiles.parameters.YearConstant;
import profiles.parameters.YearSinusoid;

/**
 * Forth example of simulator. Created to show the data output capabilities
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim4_YearVaryingProfiles {
    public static void main(String[] args) {
        // creates the city
        City city = new City("City1", "outputs");

        city.addConsumer(new DayConstantProfile("fridge", new YearSinusoid(200, 150, 180)));

        city.addProducer(new DayConstantProfile("prod2", new YearConstant(200)));

        city.addDataOutput(new DataToPlots(800, 600, PlotType.POWER));

        city.yearSimulate();
    }
}
