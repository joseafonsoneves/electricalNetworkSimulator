package executables;

import simulator.City;
import java.util.HashMap;
import java.util.Map;

import extension2.CSVRead;

/**
 * Un example d'utilisation des deux méthodes principales (Read et
 * ReadSeveralCities)
 * 
 * @author Antoine Pigamo
 */
public class SimCSVRead1 {
    public static void main(String[] args) throws Exception {

        // Test pour une ville seule
        City city = CSVRead.read("cityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());
        city.getPos().display();
        CSVRead.addPosition(city, "cityData/CityPositions.txt");
        city.getPos().display();

        // Test pour plusieurs villes
        HashMap<String, City> map = CSVRead.readSeveralCities("cityData/CitiesTest.txt");

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            CSVRead.addPosition(c, "cityData/CityPositions.txt");
            System.out.println(c.getId());
            c.getPos().display();
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");

        }

        // Test pour la matrice de connexion;
        int[][] connexion = CSVRead.readMatrix("cityData/MatrixConnexion.txt");
        for (int[] line : connexion) {
            for (int j : line) {
                System.out.print(j + " ");
            }
            System.out.println(" ");
        }
        
        //Test avec la méthodes qui réalise les des méthodes précedentes en une seule fois
        int[][] connexion2 = CSVRead.readMatrixAndAddPositions(map, "cityData/CityPositionAndMatrix.txt"); // Position et
                                                                                                          // matrice de
                                                                                                          // connexion
                                                                                                          // en même
                                                                                                          // temps

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            c.getPos().display();
        }

        for (int[] line : connexion2) {
            for (int j : line) {
                System.out.print(j + " ");
            }
            System.out.println(" ");
        }
    }
}
