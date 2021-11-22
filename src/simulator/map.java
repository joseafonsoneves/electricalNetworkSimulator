package simulator;


import java.util.ArrayList;
import java.util.HashMap;
//test
public class Map {

    private int[][] tab;
    private HashMap<City, Integer> map = new HashMap<City, Integer>();

    public Map(int[][] tab, HashMap<City,Integer> map) {
        this.tab = tab;
        this.map = map;
    }


    public int[][] getTab() {
        return this.tab;
    }

    public HashMap<City,Integer> getMap() {
        return this.map;
    }

    

    public ArrayList<Integer> road (City city1, City city2, Map myMap){ // start city1, end city2
        
        int start = myMap.getMap().get(city1);
        int end = myMap.getMap().get(city2);
        

        ArrayList<Integer>  road = new ArrayList<Integer>();  
        road.add(start); // init
        int size =  myMap.tab[0].length;
        System.out.println(size);
        int i = 1;
        ArrayList<Integer> memory = new ArrayList<Integer>(); 
        int size_road = road.size();
        System.out.println(start);
        System.out.println(end);
        int N = 0;

        while(road.get(size_road-1) != end && N<15 ){
            N++;
            
            i = road.get(size_road-1);
            memory.add(i); // allow to not go 2 time on the same "node/city"
            System.out.println("memoire : "+memory);
            for (int j = 1;j <= size; j++){
                
                if(!memory.contains((j))){
                    System.out.println(i+" et "+j);
                    System.out.println("valeur : "+myMap.tab[i-1][j-1]);
                    if ( myMap.tab[i-1][j-1] == 1){
                        road.add(j);
                        System.out.println(road);
                        size_road = road.size();
                        break;

                    }
                    else if (j == end && myMap.tab[i-1][j-1] == 0) { // there is no solution -> we have to remove the last element and go on ...
                        
                        System.out.println("else : " + size_road);
                        System.out.println(road);
                    
                        road.remove(size_road-1);
                        size_road = road.size();
                        }
                 }
            }
         }
         return road;
    }

    public double cost(ArrayList<Integer> road,Map myMap){
        double cost = 0;
        ArrayList<City> roadCity = new ArrayList<City>();
        while(road.size() != 0){
            for (City i : myMap.getMap().keySet()){
                if (road.size() != 0 && road.get(0) == myMap.getMap().get(i) ){  // we get the list of the city in the good order
                    System.out.println(myMap.getMap().get(i));
                    System.out.println(road);
                    road.remove(0);
                    System.out.println(road);
                    roadCity.add(i);
                    

                }
            }
        }
        for (int i = 0; i< roadCity.size()-1;i++){

            cost += Math.sqrt(Math.pow(roadCity.get(i+1).getPos().getY() - roadCity.get(i).getPos().getY(),2) + (Math.pow(roadCity.get(i+1).getPos().getX() - roadCity.get(i).getPos().getX(),2)));
        }

        return cost;
    }
}




                
    
