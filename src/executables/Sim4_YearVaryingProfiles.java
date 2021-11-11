package executables;

import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayConstantProfile;
import profiles.parameters.YearConstant;
import profiles.parameters.YearSinusoid;
import results.Results;
import simulator.City;
import simulator.Simulator;

/**
 * Forth example of simulator. Created to show the data output capabilities
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim4_YearVaryingProfiles {
    public static void main(String[] args) {
        // creates the city
        City city = new City("City1");

        // creates a sinusoidal varying profile
        city.addConsumer(new DayConstantProfile("fridge", new YearSinusoid(200, 150, 180)));

        // creates a constant profile
        city.addProducer(new DayConstantProfile("prod2", new YearConstant(200)));

        // creates a simulator
        Simulator sim = new Simulator("outputs");
        // uses the simulator and the created city to make a simulation
        Results results = sim.yearSimulate(city);
        // creates a power plot
        DataToPlots powerPlot = new DataToPlots(800, 600);
        powerPlot.outputData(results, PlotType.POWER);
    }
}
