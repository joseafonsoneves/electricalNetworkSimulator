package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;
/**
 * La classe du modèle rectangulaire
 */
public class Square extends Model {
    /**
     * l'amplitude du carré
     */
    public double valueAmplitude;
    /**
     * valeur de la période
     */
    public int valuePeriodInMinute;

    /**
     * crée le modèle du carré nul
     */
    public Square() {
        super();
        this.valueAmplitude = 0.0;
        this.valuePeriodInMinute = 0;

    }

    /**
     * crée le modele demandé
     * @param id0
     * @param weekVar0
     * @param yearVar0
     * @param valueAmplitude0
     * @param valuePeriodInMinute0
     * @param startMin0
     * @param endMin0
     */
    public Square(String id0, WeekVariation weekVar0, YearVariation yearVar0, double valueAmplitude0,
            int valuePeriodInMinute0, int startMin0, int endMin0) {
        super(id0, weekVar0, yearVar0);
        this.valueAmplitude = valueAmplitude0;
        this.valuePeriodInMinute = valuePeriodInMinute0;
        this.startMin = startMin0;
        this.endMin = endMin0;

    }
    
    /**
     * la méthode getDayPower associé
     */
    public double[] getDayPower(int day) {

        double[] cycle = new double[1440];

        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {

            int periodcounter = 0;

            for (int i = 0; i < cycle.length; i++) {

                if (startMin <= i && i <= endMin) {

                    periodcounter = periodcounter + 1;

                    if (periodcounter >= 0 && periodcounter <= (valuePeriodInMinute + 1)) {
                        cycle[i] = valueAmplitude;
                    }

                    if (periodcounter > (valuePeriodInMinute) && periodcounter < (2 * valuePeriodInMinute)) {
                        cycle[i] = 0;
                    }

                    if (periodcounter == (2 * valuePeriodInMinute)) {

                        cycle[i] = 0;
                        periodcounter = 1;
                    }
                }

                else {
                    cycle[i] = 0;
                }

            }
        }

        return cycle;
    }

}
