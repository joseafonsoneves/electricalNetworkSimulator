package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;
/**
 * La classe du modèle linéaire
 */
public class Linear extends Model {
    /**
     * La valeur de la pente
     */
    public double valueCoefficient;
    /**
     * la valeur de l'origin
     */
    public double valueOrigin;

    /**
     *  Le constructeur du modele linaire de base
     */
    public Linear() {
        super();
        this.valueCoefficient = 0.0;
        this.valueOrigin = 0.0;
        this.startMin = 0;
        this.endMin = 0;
    }

    /**
     * Le constructeur du modele linaire personalise
     *
     * @param id0
     * @param weekVar0
     * @param yearVar0
     * @param valueCoefficient0
     * @param valueOrigin0
     * @param startMin0
     * @param endMin0
     */
    public Linear(String id0, WeekVariation weekVar0, YearVariation yearVar0, double valueCoefficient0,
            double valueOrigin0, int startMin0, int endMin0) {
        super(id0, weekVar0, yearVar0);
        this.valueCoefficient = valueCoefficient0;
        this.valueOrigin = valueOrigin0;
        this.startMin = startMin0;
        this.endMin = endMin0;
        this.curve = i -> valueCoefficient0 * (i - startMin0) + valueOrigin0;
    }

}
