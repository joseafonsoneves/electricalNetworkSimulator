package simulator;
import profiles.ProfilesGroup;
import profiles.Profile;

import java.util.ArrayList;
import java.util.HashMap;
//test
public class map {

    private int[][] tab;
    private HashMap<City, Integer> map = new HashMap<City, Integer>();

    public map(int[][] tab, HashMap<City,Integer> map) {
        this.tab = tab;
        this.map = map;
    }


    public int[][] getTab() {
        return this.tab;
    }

    public HashMap<City,Integer> getMap() {
        return this.map;
    }

    

    public ArrayList<Integer> road (City city1, City city2, map MyMap){ // start city1, end city2
        int start = 0;
        int end = 0;
        int N = 0;

        for (City i :MyMap.getMap().keySet()){ // map contains city from 0 to N
            if (city1.getId() == i.getId()){
                start = N;

            }
            if (city2.getId() == i.getId()){
                end = N;
            }

            else {
                System.out.println("The requires cities doesn't exist");
            }

            N++;


        }
        ArrayList<Integer>  road = new ArrayList<Integer>();  
        road.add(start); // init
        int size =  MyMap.tab[0].length;
        int i = 1;
        ArrayList<Integer> memory = new ArrayList<Integer>(); 
        
        while(road.get(-1) != end){
            i = road.get(-1);
            memory.add(i); // allow to not go 2 time on the same "node/city"
            int size_m = memory.size();
            for (int j = 1;j <= size && !memory.contains((j)); j++){
                if ( MyMap.tab[i][j] == 1){
                    road.add(j);
                    break;

                }
                else { // there is no solution -> we have to remove the last element and go on ...
                    int a = road.get(-1);
                    road.remove(a);
                    }
             }
         }
         return road;
    }

    public int cost(ArrayList<Integer> road){
        int cost = 0;


        return cost;
    }
}




                
    
