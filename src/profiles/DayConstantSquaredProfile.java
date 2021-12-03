package profiles;

import profiles.parameters.Parameter;

/**
 * Profile that is similar to a square wave. It may serve to model devices or
 * producers that have constant power when turned on but that are not always
 * turned on. It is based on the squared profile abstract class so it also
 * models periodicity
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class DayConstantSquaredProfile extends SquaredProfile implements Profile {
    /** Constant value of power during the square */
    private Parameter value;

    /**
     * Constructor of a constant squared profile during the day without
     * specification of week or year variations
     * 
     * @param id     Desired String id of the profile
     * @param value  Desired constant value of power
     * @param square Desired descriptor of the square behavior of the profile
     */
    public DayConstantSquaredProfile(String id, Parameter value, Square square) {
        super(id, square, new WeekVariation(), new YearVariation());
        this.setValue(value);
    }

    /**
     * Constructor of a constant squared profile during the day with only
     * specification of year variation
     * 
     * @param id            Desired String id of the profile
     * @param value         Desired constant value of power
     * @param square        Desired descriptor of the square behavior of the profile
     * @param yearVariation Desired year variation
     */
    public DayConstantSquaredProfile(String id, Parameter value, Square square, YearVariation yearVar) {
        super(id, square, new WeekVariation(), yearVar);
        this.setValue(value);
    }

    /**
     * Constructor of a constant squared profile during the day with only
     * specification of week variation
     * 
     * @param id            Desired String id of the profile
     * @param value         Desired constant value of power
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     */
    public DayConstantSquaredProfile(String id, Parameter value, Square square, WeekVariation weekVar) {
        super(id, square, weekVar, new YearVariation());
        this.setValue(value);
    }

    /**
     * Constructor of a constant squared profile during the day with specification
     * of year and week variations
     * 
     * @param id            Desired String id of the profile
     * @param value         Desired constant value of power
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     * @param yearVariation Desired year variation
     */
    public DayConstantSquaredProfile(String id, Parameter value, Square square, WeekVariation weekVar,
            YearVariation yearVar) {
        super(id, square, weekVar, yearVar);
        this.setValue(value);
    }

    /**
     * Sets the value to the given parameter and throws an exception if not valid
     * 
     * @param value value to set
     */
    private void setValue(Parameter value) {
        if (!value.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("A non positive power value is not valid");
        }
        this.value = value;
    }

    public double[] getDayPower(int day) {
        // uses the super method by giving it an expression of the power at the square
        return super.getDayPower(day, t -> this.value.getValueAtDay(day));
    }

    public double[] getYearPower() {
        // uses the super method by giving it an expression of the power at the square
        return super.getYearPower(t -> this.value.getValueAtDay(t));
    }

    public String getDescription() {
        return super.getId() + " - Constant squared profile with power " + this.value.getDescription() + "W. "
                + super.toString();
    }
}
