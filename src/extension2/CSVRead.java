package extension2;

import simulator.City;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import extension1.CityWithPosition;

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
                }
                // Ajout des nouveaux modèles
                else if (profile.equals("Sinusoid")) {
                    addSinusoid(city, type, tokens);
                } else if (profile.equals("WhiteNoise")) {
                    addWhiteNoise(city, type, tokens);
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
     * Cette classe permet de retourner une ligne spécifique d'un fichier texte.
     * 
     * @param index    numéro de la ligne que l'on veut récupérer
     * @param filename chemin vers le fichier
     * @return String la ligne que l'on veut récupérer
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
     * Cette classe permet de lire plusieurs villes au sein d'un même fichier texte
     * et de retourner toutes les villes lues dans un même HashMap.
     * 
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
        ArrayList<Integer> ls = CSVRead.startLines(filename);
        ArrayList<Integer> le = CSVRead.endLines(filename);

        for (int i = 0; i < n; i++) {

            City city_i = new City(accessLine(ls.get(i), filename));
            if (city_i.getId().compareTo("Losses") == 0) {
                return null;
            }

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
                    addSinusoid(city_i, type, tokens);
                } else if (profile.equals("WhiteNoise")) {
                    addWhiteNoise(city_i, type, tokens);
                } else {
                    return null; // Si on ne satisfait aucune des conditions précedentes alors le fichier n'est
                                 // pas au bon format. On renvoie alors une valeur nulle pour le HashMap.
                }
            }

            ListCities.put(city_i.getId(), city_i);

        }
        return ListCities;
    }

    /**
     * Cette méthode ajoute une position à une ville. (Classe réalisable qu'en
     * présence de l'extension 1)
     * 
     * @param city     la ville dont on veut ajouter la position
     * @param filename le chemin vers le fichier texte qui référence les villes et
     *                 leur position
     * @throws IOException
     */
    static public void addPosition(CityWithPosition city, String filename) {
        try {

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            int verif = 0; // Testeur pour vérifier que la position de la ville existe dans le fichier
                           // texte.

            while (bin.ready()) {

                String line = bin.readLine();
                String[] tokens = line.split(";");

                if (tokens[0].equals(city.getId())) { // Si on trouve la position, alors on modifie la position de la
                                                      // ville
                    city.moveTo(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    verif++;
                }

            }

            bin.close();
            if (verif != 1) { // Si la condition est pas respectée, erreur.
                throw new IllegalArgumentException("La position de la ville n'existe pas");
            }
        }

        catch (IOException fileReadException) {
            fileReadException.printStackTrace();
        }
    }

    /**
     * Cette méthode permet de donner les dimensions d'une matrice issue d'un
     * fichier texte.
     * 
     * @param filename chemin vers le fichier de la matrice
     * @return int[] tableau contenant le nombre de lignes puis le nombre de
     *         colonnes de la matrice
     */
    static public int[] getDimensionMatrix(String filename) {
        try {

            int[] size = new int[2]; // Initialisation

            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);
            String line = bin.readLine();
            String[] tokens = line.split(";");

            size[1] = tokens.length; // On recupère le nombre de colonnes avec la première ligne. On verifiera par la
                                     // suite si chaque ligne respecte bien le bon nombre de colonnes

            int lines = 0;
            while (bin.ready()) {
                String line2 = bin.readLine();
                if (!line2.equals("")) { // On incrémente le compteur de ville que si on lit une ligne pas vide
                    lines++;
                }
            }
            bin.close();
            size[0] = lines + 1; // on rajoute au compteur la ligne qu'on avait lu au début pour trouver le
                                 // nombre de colonnes

            if (size[0] != size[1]) {
                throw new IllegalArgumentException("La matrice n'est pas carré");
            } else {
                return size;
            }
        } catch (

        IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }

    }

    /**
     * Cette méthode permet de lire une matrice d'un fichier texte. Cela est
     * notamment utile pour l'extension 1 avec la matrice de connexion entre ville.
     * 
     * @param filename chemin vers le fichier de la matrice
     * @return int[][] la matrice sous forme d'un tableau à 2 dimensions
     */
    static public int[][] readMatrix(String filename) {
        try {
            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            int[] size = getDimensionMatrix(filename); // On récupère la taille de la matrice
            int[][] matrix = new int[size[0]][size[1]]; // On initialise notre matrice
            int i = 0; // compteur de lignes
            while (bin.ready()) {

                String line = bin.readLine();

                if (line.equals("") || line == null) { // si jamais on lit une ligne vide (comme un saut de ligne), on
                                                       // sort de la boucle
                    break;
                }

                String[] tokens = line.split(";");

                if (tokens.length != size[1]) { // On vérifie que la ligne qu'on lit à la bonne dimension
                    bin.close();
                    throw new IllegalArgumentException("La matrice n'est pas au bon format");
                }

                for (int j = 0; j < tokens.length; j++) {
                    if (Integer.parseInt(tokens[j]) != 0 && Integer.parseInt(tokens[j]) != 1) { // On vérifie que
                                                                                                // l'élément lu dans la
                                                                                                // matrice eest bien
                                                                                                // soit un 1 ou soit un
                                                                                                // 0.
                        bin.close();
                        throw new IllegalArgumentException("The matrix should be only filled with 0 and 1");
                    }
                    matrix[i][j] = Integer.parseInt(tokens[j]);
                }
                i++;
            }
            bin.close();
            if (matrix[0].length == matrix.length) { // Avant de renvoyer la matrice, on vérifie que cette dernière est
                                                     // bien carré.
                return matrix;
            } else {
                throw new IllegalArgumentException("La matrice n'est pas carré 2");
            }
        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }

    /**
     * Cette méthode combine la méthode addPositions and readMatrix pour permettre
     * une meilleure integration avec les autres extension.
     * 
     * @param map      HashMap<String, City> qui représente toutes les villes qui
     *                 doivent recevoir leur position
     * @param filename le chemin du fichier qui comporte l'ensemble des positions
     *                 des villes et la matrice
     * @return int[][] la matrice de conexion, les positions ont, quant à elles, été
     *         ajoutées durant le fonctionnement de la méthode.
     */
    static public int[][] readMatrixAndAddPositions(HashMap<String, City> map, String filename) {
        try {
            FileReader in = new FileReader(filename);
            BufferedReader bin = new BufferedReader(in);

            // Partie Positions
            for (City c : map.values()) {
                CSVRead.addPosition((CityWithPosition) c, filename);
            }

            // Partie Matrice
            ArrayList<Integer> separationLine = endLines(filename);
            int istart = separationLine.get(0) + 2; // On trouve la ligne de départ

            String firstLineMatrix = accessLine(istart, filename);
            String[] tokens1 = firstLineMatrix.split(";");
            int n = tokens1.length; // On trouve la taille de la matrice carrée grâce à la première ligne
            int[][] matrix = new int[n][n]; // Initialisation

            for (int i = istart; i <= istart + n - 1; i++) { // On parcourt chaque ligne

                String line = accessLine(i, filename);

                String[] tokens = line.split(";");

                if (tokens.length != n) { // Si une ligne a un nombre de colonnes différents de n alors la matrice n'est
                                          // pas carrée.
                    bin.close();
                    throw new IllegalArgumentException("La matrice n'est pas carrée");
                }
                for (int j = 0; j < n; j++) {
                    if (Integer.parseInt(tokens[j]) != 0 && Integer.parseInt(tokens[j]) != 1) { // On vérifie que
                                                                                                // l'élément lu dans la
                                                                                                // matrice eest bien
                                                                                                // soit un 1 ou soit un
                                                                                                // 0.
                        bin.close();
                        throw new IllegalArgumentException("The matrix should be only filled with 0 and 1");
                    }
                    matrix[i - istart][j] = Integer.parseInt(tokens[j]);
                }
            }
            bin.close();
            return matrix;

        } catch (IOException fileReadException) {
            fileReadException.printStackTrace();
            return null;
        }
    }
}
