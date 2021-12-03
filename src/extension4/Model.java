package extension4;

import java.util.function.IntFunction;

import profiles.Profile;
import profiles.WeekVariation;
import profiles.YearVariation;

public class Model implements Profile {

    public String id;
    public WeekVariation weekVar;
    public YearVariation yearVar;
    public int startMin;
    public int endMin;
    public IntFunction<Double> curve;

    public Model() {
        this.id = "None";
        this.weekVar = new WeekVariation();
        this.yearVar = new YearVariation();
        this.curve = i -> 0.0;
    }

    public Model(String id0, WeekVariation weekVar0, YearVariation yearVar0) {
        this.id = id0;
        this.weekVar = weekVar0;
        this.yearVar = yearVar0;
    }

    public String getId() {

        return id;
    }

    public double[] getDayPower(int day) {
        double[] cycle = new double[1440];
        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
            for (int i = 0; i < cycle.length; i++) {
                if (startMin <= i && i < endMin) {
                    cycle[i] = curve.apply(i);
                } else {
                    cycle[i] = 0;
                }
            }
        }
        return cycle;
    }

    public double[] getYearPower() {
        double[] res = new double[365];

        for (int i = 0; i < res.length; i++) {
            double power = 0.0;
            if (weekVar.checkDayInWeek(i) && yearVar.checkDayInYear(i)) {
                double[] powerDay = getDayPower(i);
                for (double j : powerDay) {
                    power = power + j;
                }
                res[i] = power;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    public String getDescription() {
        return id;
    }

}
