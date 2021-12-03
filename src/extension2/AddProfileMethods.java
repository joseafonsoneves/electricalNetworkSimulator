package extension2;

import simulator.City;
import extension4.Sinusoid;
import extension4.WhiteNoise;
import profiles.DayConstantProfile;
import profiles.WeekVariation;
import profiles.YearVariation;
import profiles.parameters.YearConstant;
import profiles.Square;
import profiles.DayConstantSquaredProfile;
import profiles.DayLinearSquaredProfile;
import profiles.DayQuadraticSquaredProfile;

/**
 * Classe qui référence les différentes méthodes qui permettent d'ajouter
 * différents profils à une ville
 * 
 * @author Antoine Pigamo
 */

public class AddProfileMethods {

    /**
     * @param city        la ville concernée par l'ajout
     * @param typeProfile le type de profil (consommateur ou producteur)
     * @param tokens      la chaîne de caractère contenant toutes les informations
     *                    pour créer notre profil. Cette chaîne de caractère change
     *                    en fonction du profil concerné et de sa manière de
     *                    consommer ou produire (Constant,Linear,Quadratic,...).
     * @return City
     */
    public static City addDayConstant(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            // Ensuite on regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[4].equals("Y")) { // YearVariation
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new YearVariation(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]))));
            } else if (tokens[4].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 4]; // On met dans un tableau les jours concernés par la
                                                               // variation.
                for (int i = 5; i < tokens.length; i++) {
                    Days_Array[i - 5] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2]))));
            }

        } else {
            // Ensuite on regarde quel type de variation a le consommateurs. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[4].equals("Y")) { // YearVariation
                city.addConsumer(new DayConstantProfile(tokens[3], new YearConstant(Integer.parseInt(tokens[2])),
                        new YearVariation(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]))));
            } else if (tokens[4].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 4]; // On met dans un tableau les jours concernés par la
                                                               // variation.
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

    /**
     * @param city        la ville concernée par l'ajout
     * @param typeProfile le type de profil (consommateur ou producteur)
     * @param tokens      la chaîne de caractère contenant toutes les informations
     *                    pour créer notre profil. Cette chaîne de caractère change
     *                    en fonction du profil concerné et de sa manière de
     *                    consommer ou produire (Constant,Linear,Quadratic,...).
     * @return City
     */
    public static City addDayConstantSquared(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            // On regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[8].equals("Y")) { // YearVariation
                city.addProducer(new DayConstantSquaredProfile(tokens[7], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[9]), Integer.parseInt(tokens[10]))));
            } else if (tokens[8].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 8]; // On met dans un tableau les jours concernés par la
                                                               // variation.
                for (int i = 9; i < tokens.length; i++) {
                    Days_Array[i - 9] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayConstantSquaredProfile(tokens[7], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayConstantSquaredProfile(tokens[7], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }
        } else {
            // Ensuite on regarde quel type de variation a le consommateur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[6].equals("Y")) {
                city.addConsumer(new DayConstantSquaredProfile(tokens[5], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[9]), Integer.parseInt(tokens[10]))));
            } else if (tokens[6].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 8]; // On met dans un tableau les jours concernés par la
                                                               // variation.
                for (int i = 9; i < tokens.length; i++) {
                    Days_Array[i - 9] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayConstantSquaredProfile(tokens[7], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayConstantSquaredProfile(tokens[7], new YearConstant(Integer.parseInt(tokens[6])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }

        }
        return city;
    }

    /**
     * @param city        la ville concernée par l'ajout
     * @param typeProfile le type de profil (consommateur ou producteur)
     * @param tokens      la chaîne de caractère contenant toutes les informations
     *                    pour créer notre profil. Cette chaîne de caractère change
     *                    en fonction du profil concerné et de sa manière de
     *                    consommer ou produire (Constant,Linear,Quadratic,...).
     * @return City
     */
    public static City addDayLinearSquared(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            // On regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[9].equals("Y")) { // YearVariation
                city.addProducer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[10]), Integer.parseInt(tokens[11]))));
            } else if (tokens[9].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 9]; // On met dans un tableau les jours concernés par la
                                                               // variation.
                for (int i = 10; i < tokens.length; i++) {
                    Days_Array[i - 10] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }
        } else {
            // Ensuite on regarde quel type de variation a le consommateur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[9].equals("Y")) {
                city.addConsumer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[10]), Integer.parseInt(tokens[11]))));
            } else if (tokens[9].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 9]; // On met dans un tableau les jours concernés par la
                                                               // variation.
                for (int i = 10; i < tokens.length; i++) {
                    Days_Array[i - 10] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayLinearSquaredProfile(tokens[8], new YearConstant(Integer.parseInt(tokens[6])),
                        new YearConstant(Integer.parseInt(tokens[7])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }

        }
        return city;
    }

    /**
     * @param city        la ville concernée par l'ajout de ce profil
     * @param typeProfile le type de profil (consommateur ou producteur)
     * @param tokens      la chaîne de caractère contenant toutes les informations
     *                    pour créer notre profil. Cette chaîne de caractère change
     *                    en fonction du profil concerné et de sa manière de
     *                    consommer ou produire (Constant,Linear,Quadratic,...).
     * @return City
     */
    public static City addDayQuadraticSquared(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            // On regarde quel type de variation a le producteur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[10].equals("Y")) { // YearVariation
                city.addProducer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[11]), Integer.parseInt(tokens[12]))));
            } else if (tokens[10].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 10]; // On met dans un tableau les jours concernés par la
                                                                // variation.
                for (int i = 11; i < tokens.length; i++) {
                    Days_Array[i - 11] = Integer.parseInt(tokens[i]);
                }
                city.addProducer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addProducer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }
        } else {
            // Ensuite on regarde quel type de variation a le consommateur. L'ajout
            // du producteurs dépend de ses variations.
            if (tokens[10].equals("Y")) {
                city.addConsumer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new YearVariation(Integer.parseInt(tokens[11]), Integer.parseInt(tokens[12]))));
            } else if (tokens[10].equals("W")) { // WeekVariation
                int[] Days_Array = new int[tokens.length - 10]; // On met dans un tableau les jours concernés par la
                                                                // variation.
                for (int i = 11; i < tokens.length; i++) {
                    Days_Array[i - 11] = Integer.parseInt(tokens[i]);
                }
                city.addConsumer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5])),
                        new WeekVariation(Days_Array)));
            } else { // Pas de variations
                city.addConsumer(new DayQuadraticSquaredProfile(tokens[9],
                        new YearConstant(Integer.parseInt(tokens[6])), new YearConstant(Integer.parseInt(tokens[7])),
                        new YearConstant(Integer.parseInt(tokens[8])),
                        new Square(new YearConstant(Integer.parseInt(tokens[2])),
                                new YearConstant(Integer.parseInt(tokens[3])), Integer.parseInt(tokens[4]),
                                Integer.parseInt(tokens[5]))));
            }

        }
        return city;
    }

    public static City addSinusoid(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            int[] Days_Array = new int[tokens.length - 11]; // On met dans un tableau les jours concernés par la
                                                            // variation.
            for (int i = 12; i < tokens.length; i++) {
                Days_Array[i - 12] = Integer.parseInt(tokens[i]);
            }
            city.addProducer(new Sinusoid(tokens[6], new WeekVariation(Days_Array),
                    new YearVariation(Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9])),
                    Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]),
                    Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));
        } else {
            int[] Days_Array = new int[tokens.length - 11]; // On met dans un tableau les jours concernés par la
                                                            // variation.
            for (int i = 12; i < tokens.length; i++) {
                Days_Array[i - 12] = Integer.parseInt(tokens[i]);
            }
            city.addConsumer(new Sinusoid(tokens[6], new WeekVariation(Days_Array),
                    new YearVariation(Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9])),
                    Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]),
                    Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));

        }
        return city;
    }

    public static City addWhiteNoise(City city, String typeProfile, String[] tokens) {
        if (typeProfile.equals("producer")) {
            int[] Days_Array = new int[tokens.length - 10]; // On met dans un tableau les jours concernés par la
                                                            // variation.
            for (int i = 11; i < tokens.length; i++) {
                Days_Array[i - 11] = Integer.parseInt(tokens[i]);
            }
            city.addProducer(new WhiteNoise(tokens[6], new WeekVariation(Days_Array),
                    new YearVariation(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8])),
                    Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]),
                    Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5])));
        } else {
            int[] Days_Array = new int[tokens.length - 10]; // On met dans un tableau les jours concernés par la
                                                            // variation.
            for (int i = 11; i < tokens.length; i++) {
                Days_Array[i - 11] = Integer.parseInt(tokens[i]);
            }
            city.addConsumer(new WhiteNoise(tokens[6], new WeekVariation(Days_Array),
                    new YearVariation(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8])),
                    Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]),
                    Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5])));

        }
        return city;
    }

    public static City addDelayer(City city, String typeProfile, String[] tokens) {
        return null;
    }
}
