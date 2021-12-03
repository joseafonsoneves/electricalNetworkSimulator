package executables;

import java.util.ArrayList;
import java.util.function.BiFunction;

import extension3.UserInterface;
import extension4.Accumulate;
import extension4.CompositionWithLinear;
import extension4.Linear;
import extension4.Model;
import extension4.ModelComposer;
import extension4.Operations;
import extension4.WhiteNoise;
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

/**
 * First example related with the interface. Created from the complex scenario.
 * It does not use the other extensions
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class SimIntegrationExtensions34 {
        public static void main(String[] args) {
                City toulouse = createToulouse();
                City brest = createBrest();

                // After creating the cities, creates the user interface and adds them
                // to it
                UserInterface ui = new UserInterface();
                ui.show();
                ui.addCity(brest);
                ui.addCity(toulouse);
        }

        /**
         * creates the city of Toulouse according to the initial version of the project
         * 
         * @return a custom made city called Toulouse made with the initial version of
         *         the project
         */
        private static City createToulouse() {
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

                return toulouse;
        }

        /**
         * Creates a complex profile with the model composer class created in
         * extension 4
         * 
         * @return complex profile represented by a model composer
         */
        public static ModelComposer createComplexModel() {
                YearVariation yearVar0 = new YearVariation(0, 364);
                WeekVariation weekVar0 = new WeekVariation(0, 1, 2, 3, 4, 5, 6);

                WhiteNoise cloudNoise = new WhiteNoise("cloud", weekVar0, yearVar0, 1, 1, 0,
                                1440);

                // création du modèle d'ensoleillement

                double[] suncoeffrising = new double[365];
                double[] suncoeffplummet = new double[365];
                double[] sunOrigins = new double[365];
                int[] informationStart = new int[365];
                int[] informationEnd = new int[365];
                double[] zeros = new double[365];
                int[] midday = new int[365];

                for (int i = 0; i < suncoeffrising.length; i++) {
                        if (0 <= i && i < 182) {
                                informationStart[i] = 510 - i % 91;
                                informationEnd[i] = 1050 + i % 91;
                                suncoeffrising[i] = Math.exp(1 - i / 182);
                                suncoeffplummet[i] = -1 * suncoeffrising[i];
                        }

                        if (182 <= i && i < 365) {
                                informationStart[i] = informationStart[181] + i % 91;
                                informationEnd[i] = informationEnd[181] - i % 91;
                                suncoeffrising[i] = Math.exp((182 - i) / 2);
                                suncoeffplummet[i] = -1 * suncoeffrising[i];
                        }
                        Linear buff = new Linear("buff", weekVar0, yearVar0, suncoeffrising[i], 0,
                                        informationStart[i], informationEnd[i]);
                        sunOrigins[i] = buff.getDayPower(i)[720];

                        zeros[i] = 0.0;
                        midday[i] = 720;
                }

                CompositionWithLinear linearRising = new CompositionWithLinear("soleil",
                                weekVar0, yearVar0, 0, 0, 0, 720, suncoeffrising, zeros, informationStart,
                                midday);
                CompositionWithLinear linearplummet = new CompositionWithLinear("soleil",
                                weekVar0, yearVar0, 0, 0, 720, 1440, suncoeffplummet, sunOrigins, midday,
                                informationEnd);
                Linear Constant0 = new Linear("min0", weekVar0, yearVar0, 0.0, 0.0, 0, 1440);
                ArrayList<Model> models = new ArrayList<>();
                models.add(linearRising);
                models.add(linearplummet);
                models.add(Constant0);
                models.add(cloudNoise);

                ArrayList<BiFunction<Double, Double, Double>> ops = new ArrayList<>();
                ops.add((v1, v2) -> v1 + v2);
                ops.add((v1, v2) -> -1 * Math.min(v2, -1 * v1));
                ops.add((v1, v2) -> v1 * (1 + v2 / 5));

                ArrayList<String> opSymbols = new ArrayList<>();
                opSymbols.add("+");
                opSymbols.add("levelMin0");
                opSymbols.add("clouds modifier");

                Operations operations0 = new Operations(ops, opSymbols);
                ModelComposer composedN = new ModelComposer("Complex model", models, operations0);

                return composedN;
        }

        /**
         * Creates a city called brest with normal consumers and a complex model
         * producer created with extension 4
         * 
         * @return city called Brest with normal consumers and a complex model producer
         */
        private static City createBrest() {
                // creates the city of Brest
                City brest = new City("Brest");
                // creates a house
                ProfilesGroup remiHouse = new ProfilesGroup("House of Remi");
                // adds machines to the house
                remiHouse.add(new DayConstantProfile("fridge", new YearConstant(200)));
                // adds consumers to the city including the house created
                brest.addConsumer(new DayConstantProfile("cons1", new YearConstant(100)));
                brest.addConsumer(remiHouse);
                // Creates a complex model with the extension 4 and assigns it to Brest
                brest.addProducer(createComplexModel());

                // test accumulate graph
                YearVariation yearVar0 = new YearVariation(0, 364);
                WeekVariation weekVar0 = new WeekVariation(0, 1, 2, 3, 4, 5, 6);
                Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 10.0, 0.0, 1140, 1340);
                Accumulate accumulateWithLinearTest = new Accumulate(linearTest, 500, 100000);
                brest.addProducer(accumulateWithLinearTest);

                return brest;
        }
}