package extension4;

import profiles.WeekVariation;
import profiles.YearVariation;

public class Sinusoid extends Model {

    public double valueAmplitude;
    public double valueFrequency;
    public double valuePhase;

    public Sinusoid() {
        super();
        this.valueAmplitude = 0.0;
        this.valueFrequency = 0.0;
        this.valuePhase = 0.0;
        this.startMin = 0;
        this.endMin = 0;
        this.curve = i -> 0.0;
    }

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
