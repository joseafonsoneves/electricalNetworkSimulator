package extension4;

public class Accumulate extends Model {

    public double topValue;
    public double capacity;
    public Model model;

    public Accumulate(Model model, double topValue, double capacity) {
        super("acuumulate" + topValue + model.id, model.weekVar, model.yearVar);
        this.startMin = model.startMin;
        this.endMin = model.endMin;
        this.capacity = capacity;
        this.topValue = topValue;
        this.model = model;
    }

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
