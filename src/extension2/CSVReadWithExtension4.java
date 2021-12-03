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
public class CSVReadWithExtension4 extends AddProfileMethodsWithExtension4 {

    /**
     * @param filename le chemin vers le fichier texte permettant de créer la ville.
     *                 La description des fichiers texte utilisés est contenue dans
     *                 le README.txt du package extension2.cityData
     * @return City, la ville créée à partir du fichier texte.
     */
    public static City read(String filename) {
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
                    addDayConstant(city, type, tokens);
                } else if (profile.equals("DayConstantSquared")) {
                    addDayConstantSquared(city, type, tokens);
                } else if (profile.equals("DayLinearSquared")) {
                    addDayLinearSquared(city, type, tokens);
                } else if (profile.equals("DayQuadraticSquared")) {
                    addDayQuadraticSquared(city, type, tokens);
                } else if (profile.equals("Sinusoid")) {

                } else if (profile.equals("WhiteNoise")) {

                } else if (profile.equals("Delayer")) {

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
    static ArrayList<Integer> endLines(String filename) {
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
    static ArrayList<Integer> startLines(String filename) {
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
    public static String accessLine(int index, String filename) {
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
     * @param filename le chemin d'accès vers le fichier texte qui contient
     *                 plusieurs villes
     * @return HashMap<String, City> contenant l'ID de la ville ainsi qui l'objet
     *         City correspondant
     * @throws Exception
     */
    public static HashMap<String, City> readSeveralCities(String filename) {

        HashMap<String, City> ListCities = new HashMap<String, City>();

        // On récupère les données importantes pour regarder chaque ville du fichier
        int n = countCities(filename);
        ArrayList<Integer> ls = CSVReadWithExtension1.startLines(filename);
        ArrayList<Integer> le = CSVReadWithExtension1.endLines(filename);

        for (int i = 0; i < n; i++) {

            City city_i = new City(accessLine(ls.get(i), filename));

            for (int l = ls.get(i) + 1; l <= le.get(i); l++) {
                String line = accessLine(l, filename);
                if (line.equals("")) {
                    break; // Si le fichier comporte une ligne vide en trop, alors cela signifie que la
                           // lecture du fichier est fini.
                }
                String[] tokens = line.split(";");
                String type = tokens[0];
                String profile = tokens[1];

                if (profile.equals("DayConstant")) { // Dans un second temps on regarde quel profil on traite
                    addDayConstant(city_i, type, tokens);

                } else if (profile.equals("DayConstantSquared")) {
                    addDayConstantSquared(city_i, type, tokens);

                } else if (profile.equals("DayLinearSquared")) {
                    addDayLinearSquared(city_i, type, tokens);

                } else if (profile.equals("DayQuadraticSquared")) {
                    addDayQuadraticSquared(city_i, type, tokens);

                } else if (profile.equals("Sinusoid")) {

                } else if (profile.equals("WhiteNoise")) {

                } else if (profile.equals("Delayer")) {

                } else {
                    return null; // Si on ne satisfait aucune des conditions précedentes alors le fichier n'est
                                 // pas au bon format. On renvoie alors une valeur nulle pour le HashMap.
                }
            }

            ListCities.put(city_i.getId(), city_i);

        }
        return ListCities;
    }
}

