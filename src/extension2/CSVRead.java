package extension2;

import simulator.City;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVRead extends AddProfileMethods {

    public static City Read(String filename) {
        try {
            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            // Récupération de la première ligne
            String line1 = bin.readLine();
            String[] tokens1 = line1.split(";");
            City city = new City(tokens1[0].trim());

            while (bin.ready()) {

                String line = bin.readLine();
                String[] tokens = line.split(";");
                String type = tokens[0];
                String profile = tokens[1];

                // if (type.equals("producer")) { // On regarde si on traite un consommateur ou
                // un producteur.
                if (profile.equals("DayConstant")) { // Dans un second temps on regarde quel type de profil on a.
                    AddDayConstant(city, type, tokens);
                }
                if (profile.equals("DayConstantSquared")) {
                    AddDayConstantSquared(city, type, tokens);
                }
                if (profile.equals("DayLinearSquared")) {
                    AddDayLinearSquared(city, type, tokens);
                }
                if (profile.equals("DayQuadraticsSquared")) {
                }

            }
            bin.close();
            return city;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    static int countFiles(String parent) throws Exception {
        File file = new File(parent);

        if (!file.exists())
            throw new FileNotFoundException();
        return file.list().length;
    }

    public static ArrayList<City> ReadSeveralCities(String filename) throws Exception {

        ArrayList<City> ListCities = new ArrayList<City>();

        // On compte le nombre de ville dans le répertoire
        int n = countFiles(filename);

        for (int i = 1; i <= (n - 2); i++) {
            String CityFilename = filename + "/City" + Integer.toString(i) + ".txt";
            City City_i = Read(CityFilename);
            ListCities.add(City_i);
        }
        return ListCities;
    }
}
