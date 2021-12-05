package extension4;

import java.util.function.IntFunction;

import profiles.Profile;
import profiles.WeekVariation;
import profiles.YearVariation;

/**
 * Model est la classe basique qui represente tout les appareils producteurs ou consomateurs
 * 
 * Ils sont tous caractérisés par un id , leurs variartions, leurs instants de debut et de fin et enfin une fonction de calcul
 * 
 * Elle implémente profil de manière à pouvoir être utilisé par les autres extension via les getDayPower et les getYearPower
 */


public class Model implements Profile {
    /**
     * L'ID du model
     * 
     */
    public String id;

    /**
     * Les jours de la semaines ou le modele est actif
     */
    public WeekVariation weekVar;
    /**
     * Les jours de l'année ou le profil est actif
     */
    public YearVariation yearVar;
    /**
     * minute de depart du profil
     */
    public int startMin;
    /**
     * minute de fin du profil
     * 
     */
    public int endMin;
    /**
     * Fonction qui prends en entrée un entier et qui renvoie un double
     */
    public IntFunction<Double> curve;

    /**
     * Construteur d'un profil basique actif toute l'année et renvoyant ayant une consomation/production nulle.
     */
    public Model() {
        this.id = "None";
        this.weekVar = new WeekVariation();
        this.yearVar = new YearVariation();
        this.curve = i -> 0.0;
    }

    /**
     * 
     * Création d'un profil en fonction de ses jours de variations.
     * 
     * @param id0 
     * @param weekVar0
     * @param yearVar0
     */
    public Model(String id0, WeekVariation weekVar0, YearVariation yearVar0) {
        this.id = id0;
        this.weekVar = weekVar0;
        this.yearVar = yearVar0;
    }

    /**
     * renvoie l'id du model
     */
    public String getId() {

        return id;
    }

    /**
     * Renvoie la liste des puissance en fonction des minutes d'un profil
     * 
     * @param day
     * 
     */
    public double[] getDayPower(int day) {
        double[] cycle = new double[1440];
        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
            for (int i = 0; i < cycle.length; i++) {
                if (startMin <= i && i < endMin) {
                    cycle[i] = curve.apply(i);
                } else {
                    cycle[i] = 0;
                }
            }
        }
        return cycle;
    }

    /**
     * renvoie la liste des puissance en fonction des jours de l'année d'un profil
     */
    public double[] getYearPower() {
        double[] res = new double[365];

        for (int i = 0; i < res.length; i++) {
            double power = 0.0;
            if (weekVar.checkDayInWeek(i) && yearVar.checkDayInYear(i)) {
                double[] powerDay = getDayPower(i);
                for (double j : powerDay) {
                    power = power + j;
                }
                res[i] = power;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    public String getDescription() {
        return id;
    }

}
