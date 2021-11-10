package dataOutputs;

import results.Results;

/**
 * Interface for all the data visualization tools that were created
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public interface DataOutput {
    /**
     * Outputs the results obtained in the way its object is designed for
     * 
     * @param destinationFolder descriptor of the executed simulation
     * @param dataset           Results object containing all the information
     *                          computed
     */
    void outputData(String destinationFolder, Results dataset);
}
