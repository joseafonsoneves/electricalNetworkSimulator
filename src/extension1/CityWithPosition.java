package extension1;

import simulator.City;

//test
/**
 * A class that represents a city with all its producers and consumers
 * 
 */
public class CityWithPosition extends City {

    private Position pos;

    /**
     * Creates city with only the identifier of the city and initializes the lists
     * of producers and consumers
     * 
     * @param id the identifier of the city
     */

    public CityWithPosition(String id, Position pos) {
        super(id);
        this.pos = pos;

    }

    public CityWithPosition(String id) {
        super(id);
    }

    public CityWithPosition(City city) {
        super(city);
        this.pos = new Position();
    }

    public void moveTo(int x, int y) {
        this.pos.moveTo(x, y);
    }

    public Position getPos() {
        return this.pos;
    }

}
