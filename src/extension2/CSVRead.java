package extension2;

import simulator.City;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui référence les différentes méthodes qui permettent de lire un
 * fichier texte pour créer une ou plusieurs villes
 * 
 * @author Antoine Pigamo
 */
public class CSVRead extends AddProfileMethods {

    /**
     * @param filename le chemin vers le fichier texte permettant de créer la ville.
     *                 La description des fichiers texte utilisés est contenue dans
     *                 le README.txt du package extension2.cityData
     * @return City, la ville créée à partir du fichier texte.
     */
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

                // On regarde si on traite un consommateur ou un producteur.
                if (profile.equals("DayConstant")) { // Dans un second temps on regarde quel type de profil on a.
                    AddDayConstant(city, type, tokens);
                }
                if (profile.equals("DayConstantSquared")) {
                    AddDayConstantSquared(city, type, tokens);
                }
                if (profile.equals("DayLinearSquared")) {
                    AddDayLinearSquared(city, type, tokens);
                }
                if (profile.equals("DayQuadraticSquared")) {
                    AddDayQuadraticSquared(city, type, tokens);
                }
            }
            bin.close();
            return city;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    /**
     * @param filename chemin d'accés au fichier texte
     * @return int, le nombre de villes dans le fichier texte
     * @throws Exception
     */
    static int countCities(String filename) {
        try {
            int n = 0;
            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            while (bin.ready()) {
                String line = bin.readLine();
                if (line.equals("--")) {
                    n = n + 1;
                }
            }
            if (!bin.ready()) {
                n = n + 1;
            }
            bin.close();
            return n;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return 0;
        }
    }

    /**
     * @param filename chemin d'accés au fichier texte
     * @return ArrayList<Integer> , la liste des lignes de fin d'une ville dans un
     *         fichier qui contient plusieurs villes
     * @throws Exception
     */
    static ArrayList<Integer> EndLines(String filename) {
        try {
            ArrayList<Integer> endLinesList = new ArrayList<Integer>();

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);
            int i = 0;
            while (bin.ready()) {
                String line = bin.readLine();
                if (line.equals("--")) {
                    endLinesList.add(i);
                }
                i++;
            }
            if (!bin.ready()) {
                endLinesList.add(i);
            }
            bin.close();

            return endLinesList;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    /**
     * @param filename
     * @return ArrayList<Integer>
     * @throws Exception
     */
    static ArrayList<Integer> StartLines(String filename) {
        try {
            ArrayList<Integer> startLinesList = new ArrayList<Integer>();

            // Initialisation
            startLinesList.add(1);

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);
            int i = 1;
            while (bin.ready()) {
                String line = bin.readLine();
                if (line.equals("--")) {
                    startLinesList.add(i + 1);
                }
                i++;
            }
            bin.close();

            return startLinesList;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    /**
     * @param index
     * @param filename
     * @return String
     * @throws IOException
     */
    public static String AccessLine(int index, String filename) {
        try {
            String goodLine = null;

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);
            int i = 1;
            while (bin.ready()) {
                String line = bin.readLine();
                if (i == index) {
                    goodLine = line;
                }
                i++;
            }
            bin.close();
            return goodLine;
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    /**
     * @param filename
     * @return HashMap<String, City>
     * @throws Exception
     */
    public static HashMap<String, City> ReadSeveralCities(String filename) {

        HashMap<String, City> ListCities = new HashMap<String, City>();

        // ON récupère les données importantes pour regarder chaque ville du fichier
        int n = countCities(filename);
        ArrayList<Integer> ls = CSVRead.StartLines(filename);
        ArrayList<Integer> le = CSVRead.EndLines(filename);

        for (int i = 0; i < n; i++) {

            City city_i = new City(AccessLine(ls.get(i), filename));

            for (int l = ls.get(i) + 1; l <= le.get(i); l++) {
                String line = AccessLine(l, filename);
                if (line.equals("")) {
                    throw new IllegalArgumentException("Le fichier texte comporte une ligne vide");
                }
                String[] tokens = line.split(";");
                String type = tokens[0];
                String profile = tokens[1];

                if (profile.equals("DayConstant")) { // Dans un second temps on regarde quel type de profil on a.
                    AddDayConstant(city_i, type, tokens);
                }
                if (profile.equals("DayConstantSquared")) {
                    AddDayConstantSquared(city_i, type, tokens);
                }
                if (profile.equals("DayLinearSquared")) {
                    AddDayLinearSquared(city_i, type, tokens);
                }
                if (profile.equals("DayQuadraticSquared")) {
                    AddDayQuadraticSquared(city_i, type, tokens);
                }
            }
            ListCities.put(city_i.getId(), city_i);
        }
        return ListCities;
    }

    /**
     * @param city
     * @param filename
     * @throws IOException
     */
    static public void AddPosition(City city, String filename) {
        try {

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            int verif = 0;

            while (bin.ready()) {

                String line = bin.readLine();
                String[] tokens = line.split(";");

                if (tokens[0].equals(city.getId())) {
                    // city.moveTo(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    verif++;
                }

            }

            bin.close();
            if (verif != 1) {
                throw new IllegalArgumentException("La position de la ville n'existe pas");
            }
        }

        catch (IOException fileReadException) {
            fileReadException.printStackTrace();
        }
    }
}
