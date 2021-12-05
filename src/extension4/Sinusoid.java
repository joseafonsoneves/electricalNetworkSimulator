package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;
/**
 * La classe du modèle sinusoidale
 */
public class Sinusoid extends Model {

    /**
     * la valeur de l'amplitude
     */
    public double valueAmplitude;
    /**
     * la valeur de la fréquence
     */
    public double valueFrequency;
    /**
     * la valeur de la phase à l'origine
     */
    public double valuePhase;

    /**
     * crée un sinus basique (nul)
     */
    public Sinusoid() {
        super();
        this.valueAmplitude = 0.0;
        this.valueFrequency = 0.0;
        this.valuePhase = 0.0;
        this.startMin = 0;
        this.endMin = 0;
        this.curve = i -> 0.0;
    }

    /**
     * Crée le sinus demandé
     * @param id0
     * @param weekVar0
     * @param yearVar0
     * @param valueAmplitude0
     * @param valueFrequency0
     * @param valuePhase0
     * @param startMin0
     * @param endMin0
     */
    public Sinusoid(String id0, WeekVariation weekVar0, YearVariation yearVar0, double valueAmplitude0,
            double valueFrequency0, double valuePhase0, int startMin0, int endMin0) {
        super(id0, weekVar0, yearVar0);
        this.valueAmplitude = valueAmplitude0;
        this.valueFrequency = valueFrequency0;
        this.valuePhase = valuePhase0;
        this.startMin = startMin0;
        this.endMin = endMin0;
        this.curve = i -> valueAmplitude * Math.sin(valueFrequency * i * 60 + valuePhase);
    }
}
