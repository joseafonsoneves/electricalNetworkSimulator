package executables;

import dataOutputs.DataToCSV;
import dataOutputs.DataToConsole;
import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayConstantProfile;
import profiles.ProfilesGroup;
import profiles.parameters.YearConstant;
import results.Results;
import simulator.City;
import simulator.Simulator;

/**
 * First example of simulator. Created to show the data output capabilities
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim1DataOutputs {
    public static void main(String[] args) {
        // creates the city
        City city = new City("City1");

        // creates a house
        ProfilesGroup joseHouse = new ProfilesGroup("House of Jose");
        // adds machines to the house
        joseHouse.add(new DayConstantProfile("fridge", new YearConstant(200)));
        joseHouse.add(new DayConstantProfile("app2", new YearConstant(200)));

        // adds consumers to the city including the house created
        city.addConsumer(new DayConstantProfile("cons1", new YearConstant(100)));
        city.addConsumer(new DayConstantProfile("cons2", new YearConstant(100)));
        city.addConsumer(joseHouse);

        // creates and adds producers to the city
        city.addProducer(new DayConstantProfile("prod1", new YearConstant(300)));
        city.addProducer(new DayConstantProfile("prod2", new YearConstant(600)));

        // creates a simulator
        Simulator sim = new Simulator("outputs");

        // uses the simulator and the created city to make a simulation at day 0
        Results results = sim.daySimulate(city, 0);

        // outputs the results obtained to the console
        DataToConsole.outputData(results);

        // outputs the results obtained to a .csv file specified by the simulator and
        // the results obtained
        DataToCSV.outputData(results);

        // outputs the results obtained as a power plot
        DataToPlots powerPlot = new DataToPlots(800, 600);
        powerPlot.outputData(results, PlotType.POWER);

        // outputs the results obtained as an energy plot
        DataToPlots energyPlot = new DataToPlots(800, 600);
        energyPlot.outputData(results, PlotType.ENERGY);

        // outputs the results obtained as power loss plots
        DataToPlots powerLossPlot = new DataToPlots(800, 600);
        powerLossPlot.outputData(results, PlotType.POWER_LOSS);
    }
}
