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
        City city = CSVRead.Read("CityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());
        // city.getPos().display();
        CSVRead.AddPosition(city, "CityData/CityPositions.txt");
        // city.getPos().display();

        // Test pour plusieurs villes

        HashMap<String, City> map = CSVRead.ReadSeveralCities("CityData/CitiesTest.txt");

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            CSVRead.AddPosition(c, "CityData/CityPositions.txt");
            System.out.println(c.getId());
            // c.getPos().display();
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");

        }

    }
}
