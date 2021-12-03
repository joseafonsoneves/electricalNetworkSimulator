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

        //Test pour une ville seule
        City city = CSVRead.read("cityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());

        // Test pour plusieurs villes

        HashMap<String, City> map = CSVRead.readSeveralCities("cityData/CitiesTest.txt");

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            System.out.println(c.getId());
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");

        }

    }
}
