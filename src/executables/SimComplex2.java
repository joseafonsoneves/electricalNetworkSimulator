package executables;

import java.util.ArrayList;
import java.util.function.BiFunction;

import extension4.CompositionWithLinear;
import extension4.Linear;
import extension4.Model;
import extension4.ModelComposer;
import extension4.Operations;
import extension4.WhiteNoise;
import profiles.WeekVariation;
import profiles.YearVariation;

public class SimComplex2 {
    public static void main(String[] args) {
        YearVariation yearVar0 = new YearVariation(0, 364);
        WeekVariation weekVar0 = new WeekVariation(0, 1, 2, 3, 4, 5, 6);

        // Scenario somme ici par exemple 3 modeles mais on peut en mettre N

        /*
         * Square squareTest = new Square("test square", weekVar0, yearVar0, 10.0, 5,
         * 0,
         * 1440);
         * WhiteNoise whiteNoiseTest = new WhiteNoise("test bruit blanc gaussien",
         * weekVar0, yearVar0, 1, 1, 0, 1440);
         * Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 1.0, 0.0,
         * 1340, 1440);
         * 
         * ArrayList<Model> models = new ArrayList<>();
         * models.add(squareTest);
         * models.add(whiteNoiseTest);
         * models.add(linearTest);
         * 
         * ArrayList<BiFunction<Double, Double, Double>> ops = new ArrayList<>();
         * ops.add((v1, v2) -> v1 + v2);
         * 
         * ArrayList<String> opSymbols = new ArrayList<>();
         * opSymbols.add("+");
         * 
         * ops.add((v1, v2) -> v1 + v2);
         * opSymbols.add("+");
         * 
         * Operations operations0 = new Operations(ops, opSymbols);
         * ModelComposer composedN = new ModelComposer(models, operations0);
         * 
         * double[] power = composedN.getDayPower(0);
         * for (int i = 0; i < 1440; i++) {
         * System.out.println("Min: " + i + ";" + "Power: " + power[i]);
         * }
         */

        // Scenario minimun ici par exemple 3 modeles mais on peut en mettre N

        /*
         * Square squareTest = new Square("test square", weekVar0, yearVar0, 10.0, 5, 0,
         * 1440);
         * WhiteNoise whiteNoiseTest = new WhiteNoise("test bruit blanc gaussien",
         * weekVar0, yearVar0, 2, 1, 0, 1440);
         * Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 0.0, 2.0,
         * 0, 1440);
         * 
         * ArrayList<Model> models = new ArrayList<>();
         * models.add(squareTest);
         * models.add(whiteNoiseTest);
         * models.add(linearTest);
         * 
         * 
         * ArrayList<BiFunction<Double,Double,Double>> ops = new ArrayList<>();
         * ops.add((v1, v2) -> Math.min(v1, v2));
         * 
         * ArrayList<String> opSymbols = new ArrayList<>();
         * opSymbols.add("min");
         * 
         * ops.add((v1, v2) -> Math.min(v1, v2));
         * opSymbols.add("min");
         * 
         * Operations operations0 = new Operations(ops, opSymbols);
         * ModelComposer composedN = new ModelComposer(models,operations0);
         * 
         * double[] power = composedN.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario panneau solaire avec modèle d'ensoleillement et nuage :

        // modèle enuagement en focntion de la journée génére comme un bruit blanc
        // gaussien alèatoire

        WhiteNoise cloudNoise = new WhiteNoise("cloud", weekVar0, yearVar0, 1, 1, 0,
                1440);

        // création du modèle d'ensoleillement

        double[] suncoeffrising = new double[365];
        double[] suncoeffplummet = new double[365];
        double[] sunOrigins = new double[365];
        int[] informationStart = new int[365];
        int[] informationEnd = new int[365];
        double[] zeros = new double[365];
        int[] midday = new int[365];

        for (int i = 0; i < suncoeffrising.length; i++) {
            if (0 <= i && i < 182) {
                informationStart[i] = 510 - i % 91;
                informationEnd[i] = 1050 + i % 91;
                suncoeffrising[i] = Math.exp(1 - i / 182);
                suncoeffplummet[i] = -1 * suncoeffrising[i];
            }

            if (182 <= i && i < 365) {
                informationStart[i] = informationStart[181] + i % 91;
                informationEnd[i] = informationEnd[181] - i % 91;
                suncoeffrising[i] = Math.exp((182 - i) / 2);
                suncoeffplummet[i] = -1 * suncoeffrising[i];
            }
            Linear buff = new Linear("buff", weekVar0, yearVar0, suncoeffrising[i], 0,
                    informationStart[i], informationEnd[i]);
            sunOrigins[i] = buff.getDayPower(i)[720];

            zeros[i] = 0.0;
            midday[i] = 720;
        }

        CompositionWithLinear linearRising = new CompositionWithLinear("soleil",
                weekVar0, yearVar0, 0, 0, 0, 720, suncoeffrising, zeros, informationStart,
                midday);
        CompositionWithLinear linearplummet = new CompositionWithLinear("soleil",
                weekVar0, yearVar0, 0, 0, 720, 1440, suncoeffplummet, sunOrigins, midday,
                informationEnd);
        Linear Constant0 = new Linear("min0", weekVar0, yearVar0, 0.0, 0.0, 0, 1440);
        ArrayList<Model> models = new ArrayList<>();
        models.add(linearRising);
        models.add(linearplummet);
        models.add(Constant0);
        models.add(cloudNoise);

        ArrayList<BiFunction<Double, Double, Double>> ops = new ArrayList<>();
        ops.add((v1, v2) -> v1 + v2);
        ops.add((v1, v2) -> -1 * Math.min(v2, -1 * v1));
        ops.add((v1, v2) -> v1 * (1 + v2 / 5));

        ArrayList<String> opSymbols = new ArrayList<>();
        opSymbols.add("+");
        opSymbols.add("levelMin0");
        opSymbols.add("clouds modifier");

        Operations operations0 = new Operations(ops, opSymbols);
        ModelComposer composedN = new ModelComposer("complex model", models, operations0);

        double[] power = composedN.getDayPower(0);
        for (int i = 0; i < 1440; i++) {
            System.out.println("Min: " + i + ";" + "Power: " + power[i]);
        }

    }
}
