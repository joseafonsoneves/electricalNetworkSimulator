package model;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class ModelComposer extends Model {

    private ArrayList<Model> models;
    private Operations operations;
    private String name;

    public ModelComposer(String name, ArrayList<Model> _models, Operations operations0) {
        if (operations0.op == null || _models == null)
            throw new IllegalArgumentException("None of the parameter can be null");
        for (Model model : _models) {
            if (model == null)
                throw new IllegalArgumentException("None of the model can be null");
        }
        for (BiFunction<Double, Double, Double> op : operations0.op) {
            if (op == null)
                throw new IllegalArgumentException("None of the operation can be null");
        }

        for (String s : operations0.opSymbol) {
            if (s.isEmpty()) {
                throw new IllegalArgumentException("operation nam cant be null");
            }
        }

        models = _models;
        operations = operations0;
        this.name = name;
    }

    public double[] getDayPower(int day) {
        double[] cycle = new double[1440];
        ArrayList<double[]> cycles = new ArrayList<>();
        for (int i = 0; i < models.size(); ++i) {
            cycles.add(models.get(i).getDayPower(day));
        }
        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
            // Initi cycle with first op and two first models : cycle = m1 op m2
            for (int i = 0; i < cycle.length; i++) {
                cycle[i] = operations.op.get(0).apply(cycles.get(0)[i], cycles.get(1)[i]);
            }
            // Pour tout autres models
            for (int opNumber = 1; opNumber < operations.op.size(); ++opNumber) {
                // cycle = cycle op modelSuivant
                for (int i = 0; i < cycle.length; i++) {
                    cycle[i] = operations.op.get(opNumber).apply(cycle[i], cycles.get(opNumber + 1)[i]);
                }
            }
        }
        return cycle;
    }

    public String getId() {
        return this.name;
    }
}