package extension4;

import java.util.Random;

import profiles.WeekVariation;
import profiles.YearVariation;
/**
 *  La classe du modèle bruit blanc
 */

public class WhiteNoise extends Model {
    /**
     * valeur de la moyenne
     */
    public double mu;
    /**
     * valeur de l'écart type
     */
    public double sigma;
    /** 
     * variable nécessaire à l'uitilisation de la librairie random
    */
    public Random rand;

    /**
     * crée bruit blanc gaussien basique (null)
     */
    public WhiteNoise() {
        super();
        this.mu = 1;
        this.sigma = 1;
        this.startMin = 0;
        this.endMin = 0;

    }

    /**
     * crée bruit blanc gaussien demandé
     * @param id0
     * @param weekVar0
     * @param yearVar0
     * @param mu0
     * @param sigma0
     * @param startMin0
     * @param endMin0
     */
    public WhiteNoise(String id0, WeekVariation weekVar0, YearVariation yearVar0, double mu0, double sigma0,
            int startMin0, int endMin0) {
        super(id0, weekVar0, yearVar0);
        this.mu = mu0;
        this.sigma = sigma0;
        this.startMin = startMin0;
        this.endMin = endMin0;
        this.rand = new Random();
        this.curve = i -> rand.nextGaussian() * sigma0 + mu0;
    }

}
