package executables;

import simulator.City;
import java.util.HashMap;
import extension1.CityWithPosition;

import extension2.CSVRead;

/**
 * Un example d'utilisation des deux méthodes principales (Read et
 * ReadSeveralCities)
 * 
 * @author Antoine Pigamo
 */
public class SimCSVRead2 {
    public static void main(String[] args) throws Exception {

        // Test pour une ville seule
        // City city = CSVRead.read("cityData/CityNewModel.txt");
        // System.out.println(city.getId());
        // System.out.println(city.getConsumersDescription());
        // System.out.println(city.getProducersDescription());
        // city.getPos().display();
        // CSVRead.addPosition(city, "cityData/CityPositions.txt");
        // city.getPos().display();

        HashMap<String, City> map = CSVRead.readSeveralCities("cityData/CitiesTest.txt");

        // for every city in the group
        for (City city : map.values()) {
            // if city is not an instance of CityWithPosition
            if (!(city instanceof CityWithPosition)) {
                // puts a city with position at the place of a city in the hash map
                map.put(city.getId(), new CityWithPosition(city));
            }
        }

        int[][] connexion = CSVRead.readMatrixAndAddPositions(map, "cityData/CityPositionAndMatrix.txt"); // Position et
                                                                                                          // matrice de
                                                                                                          // connexion
                                                                                                          // en même
                                                                                                          // temps

        for (City c : map.values()) {
            c = new CityWithPosition(c);
            ((CityWithPosition) c).getPos().display();
        }

        for (int[] line : connexion) {
            for (int j : line) {
                System.out.print(j + " ");
            }
            System.out.println(" ");
        }

    }
}
