package executables;

import simulator.City;
import java.util.HashMap;
import java.util.Map;

import extension2.CSVReadWithExtension1;

/**
 * Un example d'utilisation des deux méthodes principales (Read et
 * ReadSeveralCities)
 * 
 * @author Antoine Pigamo
 */
public class SimCSVRead2 {
    public static void main(String[] args) throws Exception {

        //Test pour une ville seule
        City city = CSVReadWithExtension1.read("cityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());
        city.getPos().display();
        CSVReadWithExtension1.addPosition(city, "cityData/CityPositions.txt");
        city.getPos().display();

        // Test pour plusieurs villes

        HashMap<String, City> map = CSVReadWithExtension1.readSeveralCities("cityData/CitiesTest.txt");

        for (Map.Entry<String, City> mapentry : map.entrySet()) {
            City c = mapentry.getValue();
            CSVReadWithExtension1.addPosition(c, "cityData/CityPositions.txt");
            System.out.println(c.getId());
            c.getPos().display();
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");

        }

    }
}
