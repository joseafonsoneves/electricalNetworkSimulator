package profiles;

import profiles.parameters.Parameter;

/**
 * Profile obtained by polynomial extension of a constant squared profile. It
 * also models periodicity
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class DayLinearSquaredProfile extends SquaredProfile implements Profile {
    /** power value at the left end of the interval */
    private Parameter valueAtLeft;
    /** power value at the right end of the interval */
    private Parameter valueAtRight;

    /**
     * Constructor of a linear squared profile during the day without specification
     * of year and week variations
     * 
     * @param id          Desired String id of the profile
     * @param valueAtLeft Value at the left of the interval
     * @param square      Desired descriptor of the square behavior of the profile
     */
    public DayLinearSquaredProfile(String id, Parameter valueAtLeft, Parameter valueAtRight, Square square) {
        super(id, square, new WeekVariation(), new YearVariation());
        this.setValues(valueAtLeft, valueAtRight);
    }

    /**
     * Constructor of a linear squared profile during the day with only
     * specification of year variation
     * 
     * @param id            Desired String id of the profile
     * @param valueAtLeft   Value at the left of the interval
     * @param valueAtRight  Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param yearVariation Desired yearVariation
     */
    public DayLinearSquaredProfile(String id, Parameter valueAtLeft, Parameter valueAtRight, Square square,
            YearVariation yearVar) {
        super(id, square, new WeekVariation(), yearVar);
        this.setValues(valueAtLeft, valueAtRight);
    }

    /**
     * Constructor of a linear squared profile during the day with only
     * specification of week variation
     * 
     * @param id            Desired String id of the profile
     * @param valueAtLeft   Value at the left of the interval
     * @param valueAtRight  Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     */
    public DayLinearSquaredProfile(String id, Parameter valueAtLeft, Parameter valueAtRight, Square square,
            WeekVariation weekVar) {
        super(id, square, weekVar, new YearVariation());
        this.setValues(valueAtLeft, valueAtRight);
    }

    /**
     * Constructor of a linear squared profile during the day with specification of
     * year and week variations
     * 
     * @param id            Desired String id of the profile
     * @param valueAtLeft   Value at the left of the interval
     * @param valueAtRight  Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     * @param yearVariation Desired year variation
     */
    public DayLinearSquaredProfile(String id, Parameter valueAtLeft, Parameter valueAtRight, Square square,
            WeekVariation weekVar, YearVariation yearVar) {
        super(id, square, weekVar, yearVar);
        this.setValues(valueAtLeft, valueAtRight);
    }

    /**
     * Sets the values to the given parameter and throws an exception if not valid
     * 
     * @param valueAtLeft  value to set the value at left
     * @param valueAtRight value to set the value at right
     */
    private void setValues(Parameter valueAtLeft, Parameter valueAtRight) {
        // if the values do not check, throws an argument exception
        if (!valueAtLeft.isAlwaysBiggerThan(0) || !valueAtRight.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Non positive power values are not valid");
        }
        this.valueAtLeft = valueAtLeft;
        this.valueAtRight = valueAtRight;
    }

    public double[] getDayPower(int day) {
        // uses the super method by giving it an expression of the power at the square
        return super.getDayPower(day, t -> (this.valueAtRight.getValueAtDay(day) - this.valueAtLeft.getValueAtDay(day))
                / this.getDuration(day) * t + this.valueAtLeft.getValueAtDay(day));
    }

    public double[] getYearPower() {
        // uses the super method by giving it an expression of the power at the square
        return super.getYearPower(t -> (this.valueAtLeft.getValueAtDay(t) + this.valueAtRight.getValueAtDay(t)) / 2);
    }

    public String getDescription() {
        return super.getId() + " - Linear squared profile with power in W at left " + this.valueAtLeft.getDescription()
                + " and power at right " + this.valueAtRight.getDescription() + ". " + super.toString();
    }
}