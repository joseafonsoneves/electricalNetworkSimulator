package executables;

import dataOutputs.DataToCSV;
import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayConstantProfile;
import profiles.DayConstantSquaredProfile;
import profiles.ProfilesGroup;
import profiles.Square;
import profiles.WeekVariation;
import profiles.YearVariation;
import profiles.parameters.YearConstant;
import results.Results;
import simulator.City;
import simulator.Simulator;

/**
 * Second example of simulator. Created to show week and year variations
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim2WeekYearGroup {
        public static void main(String[] args) {
                // creates the city
                City city = new City("Toulouse");

                // creates a house
                ProfilesGroup joseHouse = new ProfilesGroup("House of José");
                // adds machines to the house
                joseHouse.add(new DayConstantProfile("fridge", new YearConstant(20)));
                joseHouse.add(new DayConstantSquaredProfile("water warmer", new YearConstant(40),
                                new Square(new YearConstant(20), new YearConstant(7 * 60), 1, 0),
                                new WeekVariation(-1)));
                joseHouse.add(new DayConstantSquaredProfile("car charger", new YearConstant(40),
                                new Square(new YearConstant(6 * 60), new YearConstant(20 * 60), 1, 0)));
                joseHouse.add(new DayConstantSquaredProfile("heating", new YearConstant(20),
                                new Square(new YearConstant(12 * 60), new YearConstant(20 * 60), 1, 0),
                                new YearVariation(270, 180)));
                // given we can set different variations over the year and the week we can set
                // different behaviors for the same machine and join them in a group
                ProfilesGroup tv = new ProfilesGroup("tv");
                tv.add(new DayConstantSquaredProfile("tv weekdays", new YearConstant(40),
                                new Square(new YearConstant(20), new YearConstant(7 * 60 + 40), 2, 13 * 60),
                                new WeekVariation(-1)));
                tv.add(new DayConstantSquaredProfile("tv weekend", new YearConstant(40),
                                new Square(new YearConstant(7 * 60), new YearConstant(14 * 60), 1, 0),
                                new WeekVariation(-2)));
                joseHouse.add(tv);

                // adds consumers to the city
                city.addConsumer(joseHouse);
                city.addConsumer(new DayConstantProfile("factory", new YearConstant(20)));

                // creates and adds all the created producers to the city
                city.addProducer(new DayConstantProfile("coal plant", new YearConstant(150)));

                // creates a simulator
                Simulator sim = new Simulator("outputs");

                // uses the simulator and the created city to make a simulation
                Results results = sim.daySimulate(city, 0);

                // creates some outputs
                DataToCSV.outputData(results);
                DataToPlots powerPlot = new DataToPlots(800, 600);
                powerPlot.outputData(results, PlotType.POWER);
        }
}
