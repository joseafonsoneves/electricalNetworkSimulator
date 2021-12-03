package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;

public class Square extends Model {

    public double valueAmplitude;
    public int valuePeriodInMinute;

    public Square() {
        super();
        this.valueAmplitude = 0.0;
        this.valuePeriodInMinute = 0;

    }

    public Square(String id0, WeekVariation weekVar0, YearVariation yearVar0, double valueAmplitude0,
            int valuePeriodInMinute0, int startMin0, int endMin0) {
        super(id0, weekVar0, yearVar0);
        this.valueAmplitude = valueAmplitude0;
        this.valuePeriodInMinute = valuePeriodInMinute0;
        this.startMin = startMin0;
        this.endMin = endMin0;

    }

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
