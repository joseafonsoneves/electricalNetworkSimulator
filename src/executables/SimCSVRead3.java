package executables;

import extension2.CSVRead;
import simulator.City;

public class SimCSVRead3 {
    public static void main(String[] args) {
        // Test pour une ville seule
        City city = CSVRead.read("cityData/CityNewModel.txt");
        System.out.println(city.getId());
        System.out.println(city.getConsumersDescription());
        System.out.println(city.getProducersDescription());
        city.getPos().display();
        CSVRead.addPosition(city, "cityData/CityPositions.txt");
        city.getPos().display();

        //La position et les profils ont bien été ajoutés à la ville.

    }
}
