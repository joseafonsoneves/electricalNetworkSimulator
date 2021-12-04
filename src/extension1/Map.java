package extension1;

import java.util.ArrayList;
import java.util.HashMap;

import results.Results;
import results.SimType;

import simulator.Simulator;

//test
public class Map {

    private int[][] tab;
    private HashMap<CityWithPosition, Integer> myMap = new HashMap<CityWithPosition, Integer>();

    public Map(int[][] tab, HashMap<CityWithPosition, Integer> map) {
        this.tab = tab;
        this.myMap = map;
    }

    /**
     * @return int[][]
     */
    public int[][] getTab() {
        return this.tab;
    }

    /**
     * @return HashMap<CityWithPosition, Integer>
     */
    public HashMap<CityWithPosition, Integer> getMyMap() {
        return this.myMap;
    }

    /**
     * @param city1
     * @param city2
     * @param end
     * @return ArrayList<Integer> return the road between 2 city ex : [8,2,5,7] :
     *         city 8 go to the city 7 passing by 2 and 5
     */
    public ArrayList<Integer> road(CityWithPosition city1, CityWithPosition city2) { // start city1, end city2

        int start = myMap.get(city1);
        int end = myMap.get(city2);

        ArrayList<Integer> road = new ArrayList<Integer>();
        road.add(start); // init
        int size = tab[0].length;

        int i = 1;
        ArrayList<Integer> memory = new ArrayList<Integer>();
        int size_road = road.size();

        while (road.get(size_road - 1) != end) {

            i = road.get(size_road - 1);
            memory.add(i); // allow to not go 2 time on the same "node/city"
            // System.out.println("memoire : "+memory);
            for (int j = 1; j <= size; j++) {

                if (!memory.contains((j))) { // pas reboucler à l'infini
                    // System.out.println(i+" et "+j);
                    // System.out.println("valeur : "+Map.tab[i-1][j-1]);
                    if (tab[i - 1][j - 1] == 1) {
                        road.add(j);
                        // System.out.println(road);
                        size_road = road.size();
                        break;

                    } else if (j == size && tab[i - 1][j - 1] == 0) { // there is no solution -> we have to remove the
                                                                      // last element and go on ...

                        // System.out.println("else : " + size_road);
                        // System.out.println(road);

                        road.remove(size_road - 1);
                        size_road = road.size();
                    }
                }
            }
        }
        return road;
    }

    /**
     * @param map
     * @param productionCity)HashMap<String
     * @param HashMap<String
     * @param map.getMyMap().keySet()
     * @return HashMap<String, ArrayList<Integer>>
     */
    public HashMap<String, ArrayList<Integer>> roadMap(CityWithPosition productionCity) { // nom de la ville qui produit

        HashMap<String, ArrayList<Integer>> roadMap = new HashMap<String, ArrayList<Integer>>();

        for (CityWithPosition city : myMap.keySet()) {
            if (city.getId() != productionCity.getId()) {

                roadMap.put(city.getId(), road(city, productionCity));
            }

        }
        return roadMap;

    }

    /**
     * @param road
     * @param Map
     * @return double
     */
    public double distance(ArrayList<Integer> road) {
        double distance = 0;
        ArrayList<CityWithPosition> roadCity = new ArrayList<CityWithPosition>();
        while (road.size() != 0) {
            for (CityWithPosition i : myMap.keySet()) {
                if (road.size() != 0 && road.get(0) == myMap.get(i)) { // we get the list of the city in the good order
                    // System.out.println(myMap.getMyMap().get(i));
                    // System.out.println(road);
                    road.remove(0);
                    // System.out.println(road);
                    roadCity.add(i);

                }
            }
        }
        for (int i = 0; i < roadCity.size() - 1; i++) {

            distance += Math.sqrt(Math.pow(roadCity.get(i + 1).getPos().getY() - roadCity.get(i).getPos().getY(), 2)
                    + (Math.pow(roadCity.get(i + 1).getPos().getX() - roadCity.get(i).getPos().getX(), 2)));
        }

        return distance;
    }

    /**
     * @param roadMap
     * @param map)HashMap<String
     * @param HashMap<String
     * @param map.getMyMap().keySet()
     * @return HashMap<String, Double>
     */
    public HashMap<String, Double> distanceMap(HashMap<String, ArrayList<Integer>> roadMap) { // hashmap(ville,distance,)
                                                                                              // pour avoir la cono de
                                                                                              // la ville
        // et ensuite faire perte = consoVille *
        HashMap<String, Double> distanceMap = new HashMap<String, Double>();
        for (CityWithPosition city : myMap.keySet()) {
            ArrayList<Integer> road = roadMap.get(city.getId());
            if (road == null) {
                road = new ArrayList<Integer>(); // road null -> this is the productor
            }
            distanceMap.put(city.getId(), distance(road));

        }

        return distanceMap;
    }

    /**
     * @param power
     * @param type
     * @return double[]
     */
    public static double[] getEnergy(double[] power, SimType type) {
        double[] res = new double[power.length];
        // get sampling interval duration
        double interval = type.getInterval();

        // energy in the first sample interval
        res[0] = power[0] * interval;

        // for every other interval sums the energies produced
        for (int i = 1; i < power.length; i++) {
            res[i] = res[i - 1] + power[i] * interval;
        }

        return res;
    }

    /**
     * @param distanceMap
     * @return double[]
     */
    public double[] costMap(HashMap<String, Double> distanceMap) {

        double[] powerLoss = new double[1439];
        for (CityWithPosition city : myMap.keySet()) { // for each city exept the producter : si 7 ville seulement 6
                                                       // itérations
            for (int j = 0; j < 1439; j++) { // for a day 1440min
                powerLoss[j] += distanceMap.get(city.getId()); // sum off all distances
                // System.out.println(powerLoss[i]);
            } // System.out.println(distanceMap.get(roadMap.get(i)));System.out.println(roadMap.get(i));

        }
        return powerLoss;
    }

    /**
     * @param cityName
     * @param distanceMap
     * @return double[]
     */
    public double[] costCity(String cityName, HashMap<String, Double> distanceMap) {
        double[] powerLoss = new double[1439];
        for (int j = 0; j < 1439; j++) {

            powerLoss[j] += distanceMap.get(cityName);

        }
        return powerLoss;
    }

    /**
     * @param map
     * @param distanceMap
     * @param cityName
     * @return Results
     */
    public Results dayCitySimulate(HashMap<String, Double> distanceMap, String cityName) {
        Simulator sim = new Simulator("outputs");

        Results results = new Results(SimType.DAY);
        results.setConsumption(new double[1439]);
        results.setProduction(new double[1439]);
        results.setEnergyConsumption(new double[1439]);
        results.setEnergyProduction(new double[1439]);
        results.setPowerLoss(new double[1439]);

        double[] energyLoss = getEnergy(costCity(cityName, distanceMap), SimType.DAY);
        for (CityWithPosition city : myMap.keySet()) {
            Results results_city = sim.daySimulate(city, 0);
            for (int i = 0; i < results_city.getEnergyProduction().length - 1; i++) {

                results.getEnergyProduction()[i] += results_city.getEnergyProduction()[i];
                results.getEnergyConsumption()[i] += results_city.getEnergyConsumption()[i];
                results.getProduction()[i] += city.getDayProduction(0)[i];
                results.getConsumption()[i] += city.getDayConsumption(0)[i];

            }

        }
        for (int i = 0; i < 1439; i++) {
            results.getPowerLoss()[i] += energyLoss[i];// cumulated energy loss for each city
        }
        return results;

    }

    /**
     * @param map
     * @param distanceMap
     * @return Results
     */
    public Results dayMapSimulate(HashMap<String, Double> distanceMap) {

        Simulator sim = new Simulator("outputs");

        Results results = new Results(SimType.DAY);
        results.setConsumption(new double[1439]);
        results.setProduction(new double[1439]);
        results.setEnergyConsumption(new double[1439]);
        results.setEnergyProduction(new double[1439]);
        results.setPowerLoss(new double[1439]);

        double[] energyLoss = getEnergy(costMap(distanceMap), SimType.DAY);
        for (CityWithPosition city : myMap.keySet()) {
            Results results_city = sim.daySimulate(city, 0);
            for (int i = 0; i < results_city.getEnergyProduction().length - 1; i++) {

                results.getEnergyProduction()[i] += results_city.getEnergyProduction()[i];
                results.getEnergyConsumption()[i] += results_city.getEnergyConsumption()[i];
                results.getProduction()[i] += city.getDayProduction(0)[i];
                results.getConsumption()[i] += city.getDayConsumption(0)[i];

            }

        }
        for (int i = 0; i < 1439; i++) {
            results.getPowerLoss()[i] += energyLoss[i];// cumulated energy loss for each city
        }
        return results;

    }

    /**
     * @param map
     * @param distanceMap
     * @param cityName
     * @return Results
     */
    public Results yearCitySimulate(HashMap<String, Double> distanceMap, String cityName) {

        Simulator sim = new Simulator("outputs");

        Results results = new Results(SimType.YEAR);
        results.setConsumption(new double[365]);
        results.setProduction(new double[365]);
        results.setEnergyConsumption(new double[365]);
        results.setEnergyProduction(new double[365]);
        results.setPowerLoss(new double[365]);

        results.setPowerLoss(getEnergy(costCity(cityName, distanceMap), SimType.YEAR));

        for (CityWithPosition city : myMap.keySet()) {
            Results resultsCity = sim.yearSimulate(city);
            if (cityName == city.getId()) {
                for (int i = 0; i < resultsCity.getEnergyProduction().length; i++) {

                    results.getProduction()[i] += resultsCity.getProduction()[i];
                    results.getConsumption()[i] += resultsCity.getConsumption()[i];

                }
            }

        }
        results.setEnergyProduction(getEnergy(results.getProduction(), SimType.YEAR));
        results.setEnergyConsumption(getEnergy(results.getConsumption(), SimType.YEAR));
        return results;

    }

    /**
     * @param map
     * @param distanceMap
     * @return Results
     */
    public Results yearMapSimulate(HashMap<String, Double> distanceMap) {

        Simulator sim = new Simulator("outputs");

        Results results = new Results(SimType.YEAR);
        results.setConsumption(new double[365]);
        results.setProduction(new double[365]);
        results.setEnergyConsumption(new double[365]);
        results.setEnergyProduction(new double[365]);
        results.setPowerLoss(new double[365]);

        results.setPowerLoss(getEnergy(costMap(distanceMap), SimType.YEAR));

        for (CityWithPosition city : myMap.keySet()) {
            Results resultsCity = sim.yearSimulate(city);
            for (int i = 0; i < resultsCity.getEnergyProduction().length; i++) {

                results.getProduction()[i] += resultsCity.getProduction()[i];
                results.getConsumption()[i] += resultsCity.getConsumption()[i];

            }

        }
        results.setEnergyProduction(getEnergy(results.getProduction(), SimType.YEAR));
        results.setEnergyConsumption(getEnergy(results.getConsumption(), SimType.YEAR));
        return results;

    }

    /**
     * @param distanceMap
     * @return HashMap<String, Double[]>
     */
    public HashMap<String, double[]> lossDay(HashMap<String, Double> distanceMap) {

        HashMap<String, double[]> loss = new HashMap<String, double[]>();
        loss.put("totalLoss", new double[1439]);
        double[] energyLoss = getEnergy(costMap(distanceMap), SimType.DAY);

        for (CityWithPosition city : myMap.keySet()) {
            loss.put(city.getId(), new double[1439]);

        }
        for (int i = 0; i < 1439; i++) {

            loss.get("totalLoss")[i] += energyLoss[i];

        }

        for (CityWithPosition city : myMap.keySet()) {
            double[] energyLossCity = getEnergy(costCity(city.getId(), distanceMap), SimType.DAY);
            for (int i = 0; i < 1439; i++) {
                loss.get(city.getId())[i] += energyLossCity[i];

            }

        }
        return loss;

    }

    public HashMap<String, double[]> lossYear(HashMap<String, Double> distanceMap) {

        HashMap<String, double[]> loss = new HashMap<String, double[]>();
        loss.put("totalLoss", new double[365]);
        double[] energyLoss = getEnergy(costMap(distanceMap), SimType.YEAR);

        for (CityWithPosition city : myMap.keySet()) {
            loss.put(city.getId(), new double[365]);

        }
        for (int i = 0; i < 365; i++) {

            loss.get("totalLoss")[i] += energyLoss[i];

        }

        for (CityWithPosition city : myMap.keySet()) {
            double[] energyLossCity = getEnergy(costCity(city.getId(), distanceMap), SimType.YEAR);
            for (int i = 0; i < 365; i++) {
                loss.get(city.getId())[i] += energyLossCity[i];

            }

        }
        return loss;
    }

}
