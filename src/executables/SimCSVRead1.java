package executables;

import java.util.ArrayList;

import extension2.CSVRead;
import simulator.City;

public class SimCSVRead1 {
    public static void main(String[] args) throws Exception {

        // Test pour une ville seule
        City city = CSVRead.Read("CityData/CityTest.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());

        // Test pour plusieurs villes

        ArrayList<City> lc = CSVRead.ReadSeveralCities("CityData");

        for (City c : lc) {
            System.out.println(c.getId());
            System.out.println(c.getConsumersDescription());
            System.out.println(c.getProducersDescription());
            System.out.println(" ");
        }
    }
}
