package executables;

import java.util.ArrayList;
import java.util.HashMap;

import extension1.CityWithPosition;
import extension1.DataToConsoleExt1;
import extension1.Map;
import extension1.Position;
import profiles.DayConstantProfile;
import profiles.ProfilesGroup;
import profiles.parameters.YearConstant;
import results.Results;

public class SimMap1 {
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

        // ArrayList<Integer> road = France.road(Marseille,Toulouse); OK
        // System.out.println(road);

        HashMap<String, ArrayList<Integer>> roadMap = France.roadMap(Toulouse);
        // System.out.println(roadMap); OK

        HashMap<String, Double> distanceMap = France.distanceMap(roadMap);
        // System.out.println(distanceMap); OK

        ProfilesGroup joseHouse = new ProfilesGroup("House of Jose");
        // adds machines to the house
        joseHouse.add(new DayConstantProfile("fridge", new YearConstant(200)));
        joseHouse.add(new DayConstantProfile("app2", new YearConstant(200)));

        // adds consumers to the CityWithPosition including the house created
        Toulouse.addConsumer(new DayConstantProfile("cons1", new YearConstant(100)));
        Toulouse.addConsumer(new DayConstantProfile("cons2", new YearConstant(100)));
        Toulouse.addConsumer(joseHouse);

        // creates and adds producers to the CityWithPosition
        Toulouse.addProducer(new DayConstantProfile("prod1", new YearConstant(400)));
        Toulouse.addProducer(new DayConstantProfile("prod2", new YearConstant(1000)));

        Bordeaux.addConsumer(new DayConstantProfile("cons1", new YearConstant(300)));
        Bordeaux.addConsumer(new DayConstantProfile("cons2", new YearConstant(300)));

        // System.out.println(France.costMap(distanceMap)[10]); // OK

        Results results = France.dayMapSimulate(distanceMap);
        // Results resultsToulouse = France.dayCitySimulate(distanceMap,"Bordeaux"); OK

        // outputs the results obtained to the console
        DataToConsoleExt1.outputData(results);

        HashMap<String, double[]> loss = France.lossDay(distanceMap);
        System.out.println(loss.get("totalLoss")[1438]); // test OK

    }

}
