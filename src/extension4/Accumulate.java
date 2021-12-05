package extension4;

/**
 * Classe permettant de transformer un model en un model avec un stocakge d'ernergie au dela d'une valeur.
 * le stockage à une valeur max.
 */
public class Accumulate extends Model {
    /**
     * La valeur à partir de laquelle l'energie est stockée.
     */
    public double topValue;
    /**
     * la capacité maximum d'energie que on peut stocker.
     */
    public double capacity;
    /**
     * le modèle sur lequel on met un accumulateur.
     */
    public Model model;

    /**
     * Créer l'acumulateur
     * @param model
     * @param topValue
     * @param capacity
     */
    public Accumulate(Model model, double topValue, double capacity) {
        super("accumulate" + topValue + model.id, model.weekVar, model.yearVar);
        this.startMin = model.startMin;
        this.endMin = model.endMin;
        this.capacity = capacity;
        this.topValue = topValue;
        this.model = model;
    }
    /**
     * La méthode getDayPower redéfini pour cette classe
     * @param day
     */
    public double[] getDayPower(int day) {
        double[] power = new double[1440];
        double stored = 0.0;
        double[] buff = model.getDayPower(day);
        for (int i = 0; i < 1440; i++) {

            if ((buff[i] + stored) > topValue) {
                power[i] = topValue;
                stored = stored + buff[i] - topValue;
                if (stored > capacity) {
                    stored = capacity;
                }
            }

            else {
                power[i] = buff[i];
            }
        }

        return power;
    }

}
