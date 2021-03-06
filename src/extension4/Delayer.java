package extension4;
/**
 * Permet de mettre un retard sur le Model choisis
 */
public class Delayer extends Model {
    /**
     * le delay à rajouter
     */
    public int delay;
    /**
     * Le model à retarder
     */
    public Model model;
    
    /**
     * Creer le model avec delay
     * @param delay
     * @param model
     */
    public Delayer(int delay, Model model) {
        super("delayed" + model.id, model.weekVar, model.yearVar);
        this.startMin = model.startMin;
        this.endMin = model.endMin;
        this.delay = delay;
        this.model = model;
    }
    /**
     * La méthode getDayPower du profil
     * @param day
     */
    public double[] getDayPower(int day) {
        double[] cycle = new double[1440];
        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
            for (int i = 0; i < cycle.length; i++) {
                if ((startMin + delay) <= i && i < endMin) {
                    cycle[i] = model.curve.apply(i - delay);
                } else {
                    cycle[i] = 0;
                }
            }
        }
        return cycle;
    }
}
