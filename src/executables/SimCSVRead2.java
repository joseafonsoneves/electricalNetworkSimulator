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
public class SimCSVRead2 {
    public static void main(String[] args) throws Exception {

        HashMap<String, City> map = CSVRead.readSeveralCities("cityData/CitiesTest.txt");

        int[][] connexion = CSVRead.readMatrixAndAddPositions(map, "cityData/CityPositionAndMatrix.txt"); // Position et
                                                                                                          // matrice de
                                                                                                          // connexion
                                                                                                          // en même
                                                                                                          // temps

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            c.getPos().display();
        }

        for (int[] line : connexion) {
            for (int j : line) {
                System.out.print(j + " ");
            }
            System.out.println(" ");
        }

    }
}
