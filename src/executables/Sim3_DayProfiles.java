package executables;

import city.City;
import dataOutputs.DataToCSV;
import dataOutputs.dataToPlots.DataToPlots;
import dataOutputs.dataToPlots.PlotType;
import profiles.DayLinearSquaredProfile;
import profiles.DayQuadraticSquaredProfile;
import profiles.Square;
import profiles.parameters.YearConstant;

/**
 * Third example of simulator. Created to show the more complex profiles created
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Sim3_DayProfiles {
        public static void main(String[] args) {
                // creates the city
                City city = new City("Toulouse", "outputs");

                // adds consumers to the city
                city.addConsumer(new DayLinearSquaredProfile("linear1", new YearConstant(40), new YearConstant(50),
                                new Square(new YearConstant(60 * 3), new YearConstant(0), 4, 5 * 60)));
                city.addConsumer(new DayLinearSquaredProfile("linear2", new YearConstant(50), new YearConstant(40),
                                new Square(new YearConstant(60 * 2), new YearConstant(3 * 60), 4, 5 * 60)));

                // creates and adds all the created producers to the city
                city.addProducer(new DayQuadraticSquaredProfile("quadratic1", new YearConstant(60),
                                new YearConstant(80), new YearConstant(70),
                                new Square(new YearConstant(60 * 3), new YearConstant(0), 4, 5 * 60)));
                city.addProducer(new DayQuadraticSquaredProfile("quadratic2", new YearConstant(70),
                                new YearConstant(50), new YearConstant(60),
                                new Square(new YearConstant(60 * 2), new YearConstant(3 * 60), 4, 5 * 60)));

                // adds all the data visualization tools to the city
                city.addDataOutput(new DataToCSV());
                city.addDataOutput(new DataToPlots(800, 600, PlotType.POWER));

                city.daySimulate(0);
        }
}
