package executables;

import java.util.ArrayList;
import java.util.HashMap;

import extension1.CityWithPosition;
import extension1.DataToConsoleExt1;
import extension1.Map;
import extension1.Position;
import profiles.DayConstantProfile;

import profiles.parameters.YearConstant;

import results.Results;

public class SimMap2 {
    public static void main(String[] args) {

        int[][] tab = { { 0, 1, 1, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 1, 1 }, { 1, 1, 0, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 1, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 0 } };
        CityWithPosition Toulouse = new CityWithPosition("Toulouse", new Position(1, 1));
        CityWithPosition Bordeaux = new CityWithPosition("Bordeaux", new Position(1, 2));
        CityWithPosition Nancy = new CityWithPosition("Nancy", new Position(1, 3));
        CityWithPosition Marseille = new CityWithPosition("Marseille", new Position(1, 4));
        CityWithPosition Rennes = new CityWithPosition("Rennes", new Position(1, 5));
        CityWithPosition Paris = new CityWithPosition("Paris", new Position(1, 6));
        CityWithPosition Nantes = new CityWithPosition("Nantes", new Position(5, 15));

        HashMap<CityWithPosition, Integer> map = new HashMap<CityWithPosition, Integer>();
        map.put(Toulouse, 1);
        map.put(Bordeaux, 2);
        map.put(Nancy, 3);
        map.put(Marseille, 4);
        map.put(Rennes, 5);
        map.put(Paris, 6);
        map.put(Nantes, 7);

        Map France = new Map(tab, map);

        // ArrayList<Integer> road = France.road(Marseille, Toulouse);
        // System.out.println(road);

        HashMap<String, ArrayList<Integer>> roadMap = France.roadMap(Toulouse);
        // System.out.println(roadMap); OK

        HashMap<String, Double> distanceMap = France.distanceMap(roadMap);
        // System.out.println(distanceMap); OK

        Bordeaux.addConsumer(new DayConstantProfile("fridge", new YearConstant(300)));

        // creates a constant profile
        Bordeaux.addProducer(new DayConstantProfile("prod2", new YearConstant(1500)));
        Nancy.addProducer(new DayConstantProfile("prod3", new YearConstant(1300)));

        Toulouse.addConsumer(new DayConstantProfile("fridge", new YearConstant(200)));

        // creates a simulator

        // Results resultsToulouse = France.yearCitySimulate(distanceMap,"Toulouse");
        // DataToConsoleExt1.outputData(resultsToulouse); OK

        Results results = France.yearMapSimulate(distanceMap);
        // outputs the results obtained to the console
        DataToConsoleExt1.outputData(results);
        HashMap<String, double[]> loss = France.lossYear(distanceMap);
        System.out.println(loss.get("totalLoss")[364]); // test OK

    }

}
