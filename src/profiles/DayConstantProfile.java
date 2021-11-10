package profiles;

import profiles.parameters.Parameter;
import results.SimType;

/**
 * Simplest day profile possible. It has a constant power throughout the day
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class DayConstantProfile implements Profile {
    /** Constant value of power during the square */
    private Parameter constant;
    /** String containing the identifier name of the profile */
    private String id;
    /** Week variation descriptor */
    private WeekVariation weekVar;
    /** Year variation descriptor */
    private YearVariation yearVar;

    /**
     * Constructor of a constant profile during the day without specification of
     * week or year variations
     * 
     * @param id       Desired String id of the profile
     * @param constant Desired constant value of power
     */
    public DayConstantProfile(String id, Parameter constant) {
        // it does not make sense to allow negative powers
        if (!constant.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Power " + String.valueOf(constant) + " is not positive");
        }

        this.id = id;
        this.constant = constant;
        this.weekVar = new WeekVariation();
        this.yearVar = new YearVariation();
    }

    /**
     * Constructor of a constant profile during the day with only specification of
     * week variation
     * 
     * @param id       Desired String id of the profile
     * @param constant Desired constant value of power
     * @param weekVar  Desired week variation
     */
    public DayConstantProfile(String id, Parameter constant, WeekVariation weekVar) {
        // it does not make sense to allow negative powers
        if (!constant.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Power " + String.valueOf(constant) + " is not positive");
        }

        this.id = id;
        this.constant = constant;
        this.weekVar = weekVar;
        this.yearVar = new YearVariation();
    }

    /**
     * Constructor of a constant profile during the day with only specification of
     * year variation
     * 
     * @param id       Desired String id of the profile
     * @param constant Desired constant value of power
     * @param yearVar  Desired year variation
     */
    public DayConstantProfile(String id, Parameter constant, YearVariation yearVar) {
        // it does not make sense to allow negative powers
        if (!constant.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Power " + String.valueOf(constant) + " is not positive");
        }

        this.id = id;
        this.constant = constant;
        this.yearVar = yearVar;
        this.weekVar = new WeekVariation();
    }

    /**
     * Constructor of a constant profile during the day with specification of year
     * and week variations
     * 
     * @param id       Desired String id of the profile
     * @param constant Desired constant value of power
     * @param weekVar  Desired week variation
     * @param yearVar  Desired year variation
     */
    public DayConstantProfile(String id, Parameter constant, WeekVariation weekVar, YearVariation yearVar) {
        // it does not make sense to allow negative powers
        if (!constant.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Power " + String.valueOf(constant) + " is not positive");
        }

        this.id = id;
        this.constant = constant;
        this.weekVar = weekVar;
        this.yearVar = yearVar;
    }

    public String getId() {
        return this.id;
    }

    public double[] getDayPower(int day) {
        if (!SimType.checkDay(day)) {
            throw new IllegalArgumentException("Day " + String.valueOf(day) + " is not valid");
        }

        double[] res = new double[SimType.DAY.getLength()];

        // if the day checks in the week and in the year
        if (weekVar.checkDayInWeek(day) && yearVar.checkDayInYear(day)) {
            // fills in the array with the constant value
            for (int i = 0; i < res.length; i++) {
                res[i] = this.constant.getValueAtDay(day);
            }
        }

        return res;
    }

    public double[] getYearPower() {
        double[] res = new double[SimType.YEAR.getLength()];

        // fills in the array with the constant value
        for (int i = 0; i < res.length; i++) {
            if (weekVar.checkDayInWeek(i) && yearVar.checkDayInYear(i)) {
                res[i] = this.constant.getValueAtDay(i);
            }
        }

        return res;
    }

    public String getDescription() {
        return this.id + " - Day constant profile with power in W " + this.constant.getDescription() + ". "
                + yearVar.toString() + ". " + weekVar.toString();
    }

}
