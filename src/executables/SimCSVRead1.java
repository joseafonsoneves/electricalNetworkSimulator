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
public class SimCSVRead1 {
    public static void main(String[] args) throws Exception {

        // Test pour une ville seule
        City city = CSVRead.read("CityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());
        // city.getPos().display();
        CSVRead.addPosition(new CityWithPosition(city), "CityData/CityPositions.txt");
        // city.getPos().display();

        // Test pour plusieurs villes

        HashMap<String, City> map = CSVRead.readSeveralCities("CityData/CitiesTest.txt");

        for (City c : map.values()) {
            CSVRead.addPosition(new CityWithPosition(c), "CityData/CityPositions.txt");
            System.out.println(c.getId());
            // c.getPos().display();
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");

        }

    }
}
