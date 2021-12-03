package executables;

import java.util.ArrayList;
import java.util.function.BiFunction;

import extension4.Accumulate;
import extension4.Linear;
import profiles.WeekVariation;
import profiles.YearVariation;

public class SimComplex1 {
    public static void main(String[] args) {

        YearVariation yearVar0 = new YearVariation(0, 364);
        WeekVariation weekVar0 = new WeekVariation(0, 1, 2, 3, 4, 5, 6);

        // Scenario profil Constant
        /*
         * Linear constanttest = new Linear("test constant", weekVar0, yearVar0, 0.0,
         * 40.0, 1340, 1440);
         * double[] power = constanttest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " +
         * constanttest.getDayPower(165)[i]);
         * }
         * 
         * // Scenario profil Linear
         * /*
         * Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 1.0, 0.0,
         * 1340, 1440);
         * double[] power = linearTest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario profil Sinusoid
        /*
         * Sinusoid sinusTest =new Sinusoid("test sinus", weekVar0, yearVar0, 1.0, 1.0,
         * 1.0, 0, 1440);
         * double[] power = sinusTest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario profil bruit blanc gaussien
        /*
         * WhiteNoise whiteNoiseTest = new WhiteNoise("test bruit blanc gaussien",
         * weekVar0, yearVar0, 1, 1, 0, 1440);
         * double[] power = whiteNoiseTest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario profil square
        /*
         * Square squareTest = new Square("test square", weekVar0, yearVar0, 10.0, 5, 0,
         * 1440);
         * double[] power = squareTest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario delayer de 10 minutes ici
        /*
         * Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 1.0, 0.0,
         * 1340, 1440);
         * Delayer delayedLinearTest = new Delayer(10, linearTest);
         * double[] power = delayedLinearTest.getDayPower(0);
         * for (int i=0; i<1440;i++){
         * System.out.println("Min: "+i+";"+"Power: " + power[i]);
         * }
         */

        // Scenario accumulateur avec valeur et capacité
        Linear linearTest = new Linear("test lineaire", weekVar0, yearVar0, 10.0, 0.0, 1140, 1340);
        Accumulate accumulateWithLinearTest = new Accumulate(linearTest, 500, 100000);
        double[] power = accumulateWithLinearTest.getDayPower(0);
        for (int i = 0; i < 1440; i++) {
            System.out.println("Min: " + i + ";" + "Power: " + power[i]);
        }

    }
}
