package extension4;

import java.util.ArrayList;
import java.util.function.BiFunction;
/**
 * Classe qui contient les informations des opérations pour un modèle complexe
 */
public class Operations {
    /**
     * Liste des fonctions qui vont opérer entre les model
     */
    public ArrayList<BiFunction<Double, Double, Double>> op;
    /**
     * symbole associer à l'operation
     */
    public ArrayList<String> opSymbol;

    /**
     * créer le système d'opération associé
     * @param op
     * @param opSymbol
     */
    public Operations(ArrayList<BiFunction<Double, Double, Double>> op, ArrayList<String> opSymbol) {
        this.op = op;
        this.opSymbol = opSymbol;
    }

}
