package extension2;

import simulator.City;
import profiles.DayConstantProfile;
import profiles.WeekVariation;
import profiles.YearVariation;
import profiles.parameters.YearConstant;
import profiles.Square;
import profiles.DayConstantSquaredProfile;
import profiles.DayLinearSquaredProfile;

public class AddProfileMethods {

    public static City AddDayConstant(City city, String TypeProfile, String[] tokens) {
        if (TypeProfile.equals("producer")) {
            // Ensuite on regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[4].equals("Y")) { // YearVariation
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new YearVariation(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]))));
            } else if (tokens[4].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 4];
                for (int i = 5; i < tokens.length; i++) {
                    Days_Array[i - 5] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2]))));
            }

        } else {
            // Ensuite on regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[4].equals("Y")) { // YearVariation
                city.addConsumer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new YearVariation(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]))));
            } else if (tokens[4].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 4];
                for (int i = 5; i < tokens.length; i++) {
                    Days_Array[i - 5] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2]))));
            }
        }

        return city;
    }

    public static City AddDayConstantSquared(City city, String TypeProfile, String[] tokens) {
        if (TypeProfile.equals("producer")) {
            // On regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[6].equals("Y")) { // YearVariation
                city.addProducer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new YearVariation(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]))));
            } else if (tokens[6].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 6];
                for (int i = 7; i < tokens.length; i++) {
                    Days_Array[i - 7] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])))));
            }
        } else {
            // Ensuite on regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[6].equals("Y")) {
                city.addConsumer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new YearVariation(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]))));
            } else if (tokens[6].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 6];
                for (int i = 7; i < tokens.length; i++) {
                    Days_Array[i - 7] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[4])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])))));
            }

        }
        return city;
    }

    public static City AddDayLinearSquared(City city, String TypeProfile, String[] tokens) {
        if (TypeProfile.equals("producer")) {
            // On regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[7].equals("Y")) { // YearVariation
                city.addProducer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new YearVariation(Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]))));
            } else if (tokens[7].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 7];
                for (int i = 8; i < tokens.length; i++) {
                    Days_Array[i - 8] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])))));
            }
        } else {
            // Ensuite on regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[7].equals("Y")) {
                city.addConsumer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new YearVariation(Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]))));
            } else if (tokens[7].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 7];
                for (int i = 8; i < tokens.length; i++) {
                    Days_Array[i - 8] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3]))),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayLinearSquaredProfile(tokens[6], new YearConstant(Integer.parseInt(tokens[4])),
                        new YearConstant(Integer.parseInt(tokens[5])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])))));
            }

        }
        return city;
    }
}
