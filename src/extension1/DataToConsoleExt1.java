package extension1;

import results.Results;

/**
 * Prints the obtained dataset into the console in a csv format minute;power
 * produced;power consumed;energy produced;energy consumed
 */
public class DataToConsoleExt1 {
    // it cannot be static because it has to implement the interface
    public static void outputData(Results dataset) {
        double consumption;
        double production;
        double consumedEnergy;
        double producedEnergy;

        for (int i = 0; i < dataset.getProductionLen(); i++) {
            consumption = Math.round(dataset.getConsumptionItem(i) * 100) / 100.0;
            production = Math.round(dataset.getProductionItem(i) * 100) / 100.0;
            consumedEnergy = Math.round(dataset.getEnergyConsumedItem(i) * 100) / 100.0;
            producedEnergy = Math.round(dataset.getEnergyProducedItem(i) * 100) / 100.0;

            System.out.println(
                    i + " ; " + consumption + " ; " + production + " ; " + consumedEnergy + " ; " + producedEnergy
                            + " ; " + dataset.getPowerLoss()[i]);
        }
    }
}
