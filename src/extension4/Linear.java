package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;

public class Linear extends Model {

    public double valueCoefficient;
    public double valueOrigin;

    public Linear() {
        super();
        this.valueCoefficient = 0.0;
        this.valueOrigin = 0.0;
        this.startMin = 0;
        this.endMin = 0;
    }

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
