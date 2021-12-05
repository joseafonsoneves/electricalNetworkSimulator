package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;
/**
 * Permet de composer un modèle linéaire avec d'autre fonction 
 */
public class CompositionWithLinear extends Model {
    /**
     * liste des coefficients du modele en fonction des jours de l'année
     */
    double[] informationCoeff;
    /**
     * liste des origins du modele en fonction des jours de l'année
     */
    double[] informationOrigin;
    /**
     * liste des minutes  de debut en fonction des jours de l'année
     */
    int[] informationStart;
   /**
    *  liste des minutes  de fin en fonction des jours de l'année  
    */ 
    int[] informationEnd;
    /**
     * coefficient directeur du profil basique 
     */
    private double valueCoefficient;
    /**
     * coefficient d'origine du profil basique
     */
    private double valueOrigin;

    /**
     * Créer un profil linéaire composé avec d'autre modèle
     * @param id0
     * @param weekVar0
     * @param yearVar0
     * @param valueCoefficient0
     * @param valueOrigin0
     * @param startMin0
     * @param endMin0
     * @param informationCoeff
     * @param informationOrigin
     * @param informationStart
     * @param informationEnd
     */
    public CompositionWithLinear(String id0, WeekVariation weekVar0, YearVariation yearVar0, double valueCoefficient0,
            double valueOrigin0, int startMin0, int endMin0, double[] informationCoeff, double[] informationOrigin,
            int[] informationStart, int[] informationEnd) {
        super(id0, weekVar0, yearVar0);
        this.startMin = startMin0;
        this.endMin = endMin0;
        this.informationCoeff = informationCoeff;
        this.informationOrigin = informationOrigin;
        this.informationStart = informationStart;
        this.informationEnd = informationEnd;
        this.valueCoefficient = valueCoefficient0;
        this.valueOrigin = valueOrigin0;
    }
    /**
     * La méthode getDayPower associer
     */
    public double[] getDayPower(int day) {
        Linear lin = new Linear("modèle jour" + day, weekVar, yearVar, informationCoeff[day] + valueCoefficient,
                informationOrigin[day] + valueOrigin, informationStart[day], informationEnd[day]);
        return lin.getDayPower(day);
    }

}
