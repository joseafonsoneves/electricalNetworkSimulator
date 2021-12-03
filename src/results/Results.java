package results;

/**
 * Class to shelter the results of a given simulation by a city. It also
 * performs data treatment so that it makes available more data than what is
 * computed in city
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class Results {
    /** String containing the name of city */
    private String city;
    /** String containing the date and time of the simulation */
    private String date;
    /** Relative path to the folder of the simulation */
    private String folder;
    /** If it was obtained for a day or a year */
    private SimType type;
    /** Boolean value to save if the provided consumption is or is not enough */
    private boolean isProductionEnough;
    /** Joint power produced of the producers */
    private double[] production;
    /** Joint energy produced */
    private double[] energyProduction;
    /** Joint power consumed of the consumers */
    private double[] consumption;
    /** Joint energy consumed */
    private double[] energyConsumption;
    /** Joint power loss */
    private double[] powerLoss;

    /**
     * Simpler constructor which only takes the type of simulation as argument
     * 
     * @param type type of th simulation to be created
     */
    public Results(SimType type) {
        this.type = type;
    }

    /**
     * Creates a variable that groups all the results that are provided by the
     * simulator and treats them to create new overviews over them
     * 
     * @param city          name of the city for which the results were obtained
     * @param type          wether the simulation was obtained for a day of a year
     * @param date          String describing the date of the simulation
     * @param outputsFolder String of the relative path to the folder of outputs
     * @param production    joint power produced in each time unit by the producers
     * @param consumption   joint power consumed in each time unit by the consumers
     */
    public Results(String city, SimType type, String outputsFolder, double[] production, double[] consumption) {
        this.city = city;
        this.type = type;
        this.production = production;
        this.consumption = consumption;

        this.date = SimType.nowDateTime();
        this.folder = outputsFolder + "/" + this.getSimName();

        this.energyConsumption = getEnergy(consumption, type);
        this.energyProduction = getEnergy(production, type);

        this.powerLoss = new double[production.length];
        for (int i = 0; i < powerLoss.length; i++) {
            powerLoss[i] = production[i] - consumption[i];
        }

        // to make sure the production is enough in a year it would be necessary to
        // check it for every minute of every day. Therefore it is still being
        // considered a good way of doing so and now we are only verifying for a year if
        // the medium power produced was bigger than the consumed everyday
        if (type == SimType.DAY) {
            this.isProductionEnough = checkProduction(production, consumption);
            if (!this.isProductionEnough) {
                System.out.println("In the simulation " + getSimName() + ", the production was not enough");
            }
        } else {
            this.isProductionEnough = true;
        }
    }

    /**
     * From a array of powers and the type of simulation, gets the corresponding
     * sampling interval and gives the corresponding energy since the beginning of
     * the sampling. Power is used to be generic.
     * 
     * @param power array of powers obtained during a specific period with a
     *              specific sampling time
     * @param type  to get the sampling interval
     * @return energy obtained since the beginning of the period considered in each
     *         sampling instant in Wh
     */
    private static double[] getEnergy(double[] power, SimType type) {
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
     * Checks if the power production is always enough for the power consumption
     * needed
     * 
     * @param production  produced power at sampling instants
     * @param consumption consumed power at sampling instants
     * @return true if the production is enough and false otherwise
     */
    private boolean checkProduction(double[] production, double[] consumption) {
        for (int i = 0; i < production.length; i++) {
            if (production[i] < consumption[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * The simulation name is obtained in this fashion so that it contains a brief
     * description of the conditions in which it was made
     * 
     * @return String description of the simulation
     */
    public String getSimName() {
        return this.date + "_" + this.city + "_" + this.type.getId();
    }

    /**
     * The name of the folder that must be created for the simulation or is already
     * created
     * 
     * @return String describing its relative path
     */
    public String getSimFolder() {
        return this.folder;
    }

    /**
     * Gets the name of the city for which the results were obtained
     * 
     * @return name String of the city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Gets the real date and time in which the results were obtained
     * 
     * @return date and time of the simulation
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Gets if the production was bigger or equal to the consumption in each time
     * unit
     * 
     * @return boolean showing if the production was enough
     */
    public boolean isProductionEnough() {
        return this.isProductionEnough;
    }

    /**
     * Gets the production at the index unit of time
     * 
     * @param index unit of time in which to get the joint value of the production
     * @return joint power produced at the unit of time index
     */
    public double getProductionItem(int index) {
        return this.production[index];
    }

    /**
     * Gets the energy produced at the index unit of time
     * 
     * @param index unit of time in which to get the joint value of the energy
     *              produced
     * @return joint energy produced at the unit of time index
     */
    public double getEnergyProducedItem(int index) {
        return this.energyProduction[index];
    }

    /**
     * Gets the consumed power at the index unit of time
     * 
     * @param index unit of time in which to get the joint value of the consumed
     *              power
     * @return joint consumed power at the unit of time index
     */
    public double getConsumptionItem(int index) {
        return this.consumption[index];
    }

    /**
     * Gets the energy consumed at the index unit of time
     * 
     * @param index unit of time in which to get the joint value of the energy
     *              consumed
     * @return joint energy consumed at the unit of time index
     */
    public double getEnergyConsumedItem(int index) {
        return this.energyConsumption[index];
    }

    /**
     * Gets the array of values of production
     * 
     * @return array of production
     */
    public double[] getProduction() {
        return this.production;
    }

    /**
     * Gets the array of values of consumption
     * 
     * @return array of consumption
     */
    public double[] getConsumption() {
        return this.consumption;
    }

    /**
     * Gets the array of values of produced energy
     * 
     * @return array of produced energy
     */
    public double[] getEnergyProduction() {
        return this.energyProduction;
    }

    /**
     * Gets the array of values of consumed energy
     * 
     * @return array of consumed energy
     */
    public double[] getEnergyConsumption() {
        return this.energyConsumption;
    }

    /**
     * Gets the array of values of lost power
     * 
     * @return array of lost power
     */
    public double[] getPowerLoss() {
        return this.powerLoss;
    }

    /**
     * Instead of considering the Results object as a group of attributes, we can
     * consider it as a table in which each attribute is a column. Therefore, by
     * attributing each one of them a number of column, we can access them by their
     * column number
     * 
     * @return array of data corresponding to the column index given
     */
    public double[] getColumn(int index) {
        switch (index) {
            case 0:
                return getConsumption();
            case 1:
                return getProduction();
            case 2:
                return getEnergyConsumption();
            case 3:
                return getEnergyProduction();
            case 4:
                return getPowerLoss();
            default:
                return null;
        }
    }

    /**
     * Gets the number of time units considered in the production
     * 
     * @return number of time units considered in the production
     */
    public int getProductionLen() {
        return this.production.length;
    }

    /**
     * Gets the id of the type of simulation obtained
     * 
     * @return id of the type of simulation obtained
     */
    public String getTypeId() {
        return this.type.getId();
    }

    /**
     * Gets the label of the x axis corresponding to the type of simulation
     * 
     * @return label of the x axis corresponding to the type of simulation
     */
    public String getXLabel() {
        return this.type.getXLabel();
    }

    /**
     * Sets the production value of a results object
     * 
     * @param production new value of production
     */
    public void setProduction(double[] production) {
        this.production = production;
    }

    /**
     * Sets the produced energy value of a results object
     * 
     * @param energyProduction new value of produced energy
     */
    public void setEnergyProduction(double[] energyProduction) {
        this.energyProduction = energyProduction;
    }

    /**
     * Sets the consumption value of a results object
     * 
     * @param consumption new value of consumption
     */
    public void setConsumption(double[] consumption) {
        this.consumption = consumption;
    }

    /**
     * Sets the consumed energy value of a results object
     * 
     * @param energyConsumption new value of consumed energy
     */
    public void setEnergyConsumption(double[] energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    /**
     * Sets the power loss value of a results object
     * 
     * @param powerLoss new value of power loss
     */
    public void setPowerLoss(double[] powerLoss) {
        this.powerLoss = powerLoss;
    }
}