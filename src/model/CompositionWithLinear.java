package model;

import profiles.WeekVariation;
import profiles.YearVariation;

public class CompositionWithLinear extends Model {

    double[] informationCoeff;
    double[] informationOrigin;
    int[] informationStart;
    int[] informationEnd;
    private double valueCoefficient;
    private double valueOrigin;

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

    public double[] getDayPower(int day) {
        Linear lin = new Linear("modèle jour" + day, weekVar, yearVar, informationCoeff[day] + valueCoefficient,
                informationOrigin[day] + valueOrigin, informationStart[day], informationEnd[day]);
        return lin.getDayPower(day);
    }

}
