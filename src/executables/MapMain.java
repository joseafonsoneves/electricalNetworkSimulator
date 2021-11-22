package executables;

import java.util.ArrayList;
import java.util.HashMap;

import simulator.City;
import simulator.Map;
import simulator.Position;

public class MapMain {
    public static void main(String[] args) {
        
        int [][] tab = {{0,1,1,0,0,0,0},{1,0,1,0,0,1,1},{1,1,0,1,0,0,0},{0,0,1,0,1,0,0},{0,0,0,1,0,0,0},{0,1,0,0,0,0,0},{0,1,0,0,0,0,0}};
        City Toulouse = new City("Toulouse",new Position(1,1));
        City Bordeaux = new City("Bordeaux",new Position(1,2));
        City Nancy = new City("Nancy",new Position(1,3));
        City Marseille = new City("Marseille",new Position(1,4));
        City Rennes = new City("Rennes",new Position(1,5));
        City Paris = new City("Paris",new Position(1,6));
        City Nantes = new City("Nantes",new Position(5,7));

        HashMap<City, Integer> map = new HashMap<City, Integer>();
        map.put(Toulouse,1);
        map.put(Bordeaux,2);
        map.put(Nancy,3);
        map.put(Marseille,4);
        map.put(Rennes,5);
        map.put(Paris,6);
        map.put(Nantes,7);

        Map France = new Map(tab,map);

        ArrayList<Integer> road = France.road(Paris,Nantes,France);
        System.out.println(road);
        double cost = France.cost(road, France);
        System.out.println(cost);


    }
    
}
