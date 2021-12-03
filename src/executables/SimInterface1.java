package executables;

import profiles.DayConstantProfile;
import profiles.DayConstantSquaredProfile;
import profiles.DayLinearSquaredProfile;
import profiles.DayQuadraticSquaredProfile;
import profiles.ProfilesGroup;
import profiles.Square;
import profiles.WeekVariation;
import profiles.YearVariation;
import profiles.parameters.YearConstant;
import profiles.parameters.YearSinusoid;
import simulator.City;
import userInterface.UserInterface;

/**
 * First example related with the interface. Created from the complex scenario.
 * It does not use the other extensions
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class SimInterface1 {
        public static void main(String[] args) {
                // creates the city of toulouse
                City toulouse = new City("Toulouse");

                // creates a house
                ProfilesGroup joseHouse = new ProfilesGroup("House of José");
                // adds machines to the house
                // since the outside temperature changes along the year it is expected that the
                // fridge as different powers at different days of the year
                joseHouse.add(new DayConstantProfile("fridge", new YearSinusoid(45, 35, 180)));
                // the same happens to the water warmer
                joseHouse.add(new DayConstantSquaredProfile("water warmer", new YearSinusoid(42, 38, 365 / 2 - 180),
                                new Square(new YearConstant(20), new YearConstant(7 * 60)), new WeekVariation(-1)));
                // for the car charger we model its power as constant during usage
                joseHouse.add(new DayConstantSquaredProfile("car charger", new YearConstant(40),
                                new Square(new YearConstant(6 * 60), new YearConstant(20 * 60))));
                // for the heating it is also more needed in the winter than in the summer
                joseHouse.add(new DayConstantSquaredProfile("heating", new YearSinusoid(12, 8, 0),
                                new Square(new YearConstant(12 * 60), new YearConstant(20 * 60)),
                                new YearVariation(270, 180)));
                // given we can set different variations over the year and the week we can set
                // different behaviors for the same machine and join them in a group
                ProfilesGroup tv = new ProfilesGroup("tv");
                // you see television in different times depending on if it is a weekday or a
                // weekend
                tv.add(new DayConstantSquaredProfile("tv weekdays", new YearConstant(20),
                                new Square(new YearConstant(20), new YearConstant(7 * 60 + 40), 2, 13 * 60),
                                new WeekVariation(-1)));
                tv.add(new DayConstantSquaredProfile("tv weekend", new YearConstant(20),
                                new Square(new YearConstant(4 * 60), new YearConstant(14 * 60)),
                                new WeekVariation(-2)));
                joseHouse.add(tv);

                // creates an office
                ProfilesGroup joseOffice = new ProfilesGroup("Office of José");
                // adds machines to the office
                // they turn on the lights at the start of the day and shut them down at the end
                // of the day
                joseOffice.add(new DayConstantSquaredProfile("Lights", new YearConstant(200),
                                new Square(new YearConstant(11 * 60), new YearConstant(8 * 60 - 30)),
                                new WeekVariation(-1)));
                // creates a composite profile for the office computers
                ProfilesGroup computers = new ProfilesGroup("computers");
                // people start arriving before time and finish arriving after time so it can be
                // modelled linearly like this
                computers.add(new DayLinearSquaredProfile("computers starting", new YearConstant(0.1),
                                new YearConstant(200), new Square(new YearConstant(60), new YearConstant(8 * 60 - 30)),
                                new WeekVariation(-1)));
                computers.add(new DayConstantSquaredProfile("computers normal", new YearConstant(200),
                                new Square(new YearConstant(10 * 60 - 60), new YearConstant(8 * 60 + 30)),
                                new WeekVariation(-1)));
                computers.add(new DayLinearSquaredProfile("computers finishing", new YearConstant(200),
                                new YearConstant(0.1), new Square(new YearConstant(60), new YearConstant(18 * 60 - 30)),
                                new WeekVariation(-1)));
                joseOffice.add(computers);

                // adds the consumers
                toulouse.addConsumer(joseHouse);
                toulouse.addConsumer(joseOffice);

                // creates and adds all the created producers to the city
                toulouse.addProducer(new DayConstantProfile("coal plant", new YearConstant(150)));
                toulouse.addProducer(new DayQuadraticSquaredProfile("solar plant", new YearConstant(0.1),
                                new YearSinusoid(550, 450, 180), new YearConstant(0.1),
                                new Square(new YearSinusoid(14 * 60, 10 * 60, 180),
                                                new YearSinusoid(8 * 60, 6 * 60, 365 / 2 - 180))));

                // creates the city of Brest
                City brest = new City("Brest");

                // creates a house
                ProfilesGroup remiHouse = new ProfilesGroup("House of Remi");
                // adds machines to the house
                remiHouse.add(new DayConstantProfile("fridge", new YearConstant(200)));
                remiHouse.add(new DayConstantProfile("app2", new YearConstant(200)));

                // adds consumers to the city including the house created
                brest.addConsumer(new DayConstantProfile("cons1", new YearConstant(100)));
                brest.addConsumer(new DayConstantProfile("cons2", new YearConstant(100)));
                brest.addConsumer(remiHouse);

                // creates and adds producers to the city
                brest.addProducer(new DayConstantProfile("prod1", new YearConstant(300)));
                brest.addProducer(new DayConstantProfile("prod2", new YearConstant(600)));

                // ------- After creating the city, creates the data tree -------
                UserInterface ui = new UserInterface();
                ui.show();
                ui.addCity(brest);
                ui.addCity(toulouse);
        }
}