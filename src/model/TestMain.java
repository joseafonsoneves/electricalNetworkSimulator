package model;

import java.util.ArrayList;
import java.util.function.BiFunction;

import profiles.WeekVariation;
import profiles.YearVariation;

public class TestMain {
    public static void main(String[] args) {

        YearVariation yearVar0 = new YearVariation(0, 364);
        WeekVariation weekVar0 = new WeekVariation(0, 5);
        WeekVariation weekVar1 = new WeekVariation(0, 6);

        Linear lin = new Linear("test1", weekVar0, yearVar0, 1.0, 0.0, 0, 1440);
        Linear lin2 = new Linear("test2", weekVar1, yearVar0, -1.0, 0.0, 0, 1440);

        ArrayList<Model> models = new ArrayList<>();
        models.add(lin);
        models.add(lin2);

        ArrayList<BiFunction<Double, Double, Double>> ops = new ArrayList<>();
        ops.add((v1, v2) -> v1 - v2);

        ArrayList<String> opSymbols = new ArrayList<>();
        opSymbols.add("min");

        Operations operations0 = new Operations(ops, opSymbols);

        // BiModelComposer composed = new BiModelComposer(lin,lin2,(v1, v2) ->
        // Math.min(v1,v2),"min");
        ModelComposer composedN = new ModelComposer("complexModel", models, operations0);

        double[] test = composedN.getDayPower(9);
        System.out.println(test[1365]);
    }
}

/*
 * Op Somme = new Op((vi,v2) -> v1+v2, "+");
 * 
 * ops.add(Somme)
 * ops.add(Min)
 */
