package extension4;

import java.util.Random;

import profiles.WeekVariation;
import profiles.YearVariation;

public class WhiteNoise extends Model {

    public double mu;
    public double sigma;
    public Random rand;

    public WhiteNoise() {
        super();
        this.mu = 1;
        this.sigma = 1;
        this.startMin = 0;
        this.endMin = 0;

    }

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
