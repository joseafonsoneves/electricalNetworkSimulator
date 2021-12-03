package profiles;

import profiles.parameters.Parameter;

/**
 * Profile obtained by polynomial extension of a constant squared profile. It
 * also models periodicity. It has as additional parameters the values of the
 * power at the left and the right ends of the square and at the middle of it
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class DayQuadraticSquaredProfile extends SquaredProfile implements Profile {
    /** Power value at the left end of the interval */
    private Parameter leftPower;
    /** Power value at the center of the interval */
    private Parameter centerPower;
    /** Power value at the right end of the intervals */
    private Parameter rightPower;

    /**
     * Constructor of a quadratic squared profile during the day without
     * specification of year or week variation
     * 
     * @param id          Desired String id of the profile
     * @param leftPower   Value at the left of the interval
     * @param centerPower Value at the center of the interval
     * @param rightPower  Value at the right of the interval
     * @param square      Desired descriptor of the square behavior of the profile
     */
    public DayQuadraticSquaredProfile(String id, Parameter leftPower, Parameter centerPower, Parameter rightPower,
            Square square) {
        super(id, square, new WeekVariation(), new YearVariation());
        this.setValues(leftPower, centerPower, rightPower);
    }

    /**
     * Constructor of a quadratic squared profile during the day with specification
     * of only year variation
     * 
     * @param id            Desired String id of the profile
     * @param leftPower     Value at the left of the interval
     * @param centerPower   Value at the center of the interval
     * @param rightPower    Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param yearVariation Desired year variation
     */
    public DayQuadraticSquaredProfile(String id, Parameter leftPower, Parameter centerPower, Parameter rightPower,
            Square square, YearVariation yearVar) {
        super(id, square, new WeekVariation(), new YearVariation());
        this.setValues(leftPower, centerPower, rightPower);
    }

    /**
     * Constructor of a quadratic squared profile during the day with specification
     * of only week variation
     * 
     * @param id            Desired String id of the profile
     * @param leftPower     Value at the left of the interval
     * @param centerPower   Value at the center of the interval
     * @param rightPower    Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     */
    public DayQuadraticSquaredProfile(String id, Parameter leftPower, Parameter centerPower, Parameter rightPower,
            Square square, WeekVariation weekVar) {
        super(id, square, weekVar, new YearVariation());
        this.setValues(leftPower, centerPower, rightPower);
    }

    /**
     * Constructor of a quadratic squared profile during the day with specification
     * of year and week variations
     * 
     * @param id            Desired String id of the profile
     * @param leftPower     Value at the left of the interval
     * @param centerPower   Value at the center of the interval
     * @param rightPower    Value at the right of the interval
     * @param square        Desired descriptor of the square behavior of the profile
     * @param weekVariation Desired week variation
     * @param yearVariation Desired year variation
     */
    public DayQuadraticSquaredProfile(String id, Parameter leftPower, Parameter centerPower, Parameter rightPower,
            Square square, WeekVariation weekVar, YearVariation yearVar) {
        super(id, square, weekVar, yearVar);
        this.setValues(leftPower, centerPower, rightPower);
    }

    /**
     * Sets the values to the given parameters and throws an exception if not valid
     * 
     * @param leftPower   value to set the value at left
     * @param centerPower value to set the value at center
     * @param rightPower  value to set the value at right
     */
    private void setValues(Parameter leftPower, Parameter centerPower, Parameter rightPower) {
        if (!leftPower.isAlwaysBiggerThan(0) || !centerPower.isAlwaysBiggerThan(0)
                || !rightPower.isAlwaysBiggerThan(0)) {
            throw new IllegalArgumentException("Non positive power values are not valid");
        }
        this.leftPower = leftPower;
        this.centerPower = centerPower;
        this.rightPower = rightPower;
    }

    /**
     * Gets the coefficient of the quadratic term of the simple expression of the
     * profile in function of the day
     * 
     * @param day day in which to compute
     * @return coefficient of the quadratic term
     */
    private double getA(int day) {
        int duration = this.getDuration(day);

        return 2 * (this.leftPower.getValueAtDay(day) + this.rightPower.getValueAtDay(day)
                - 2 * this.centerPower.getValueAtDay(day)) / (duration * duration);
    }

    /**
     * Gets the coefficient of the linear term of the simple expression of the
     * profile in function of the day
     * 
     * @param day day in which to compute
     * @return coefficient of the linear term
     */
    private double getB(int day) {
        return (4 * this.centerPower.getValueAtDay(day) - 3 * this.leftPower.getValueAtDay(day)
                - this.rightPower.getValueAtDay(day)) / this.getDuration(day);
    }

    /**
     * Gets the constant coefficient of the simple expression of the profile in
     * function of the day
     * 
     * @param day day in which to compute
     * @return constant coefficient
     */
    private double getC(int day) {
        return this.leftPower.getValueAtDay(day);
    }

    public double[] getDayPower(int day) {
        // uses the super method by giving it an expression of the power at the square
        return super.getDayPower(day, t -> this.getA(day) * t * t + this.getB(day) * t + this.getC(day));
    }

    public double[] getYearPower() {
        // uses the super method by giving it an expression of the power at the square
        return super.getYearPower(t -> this.getA(t) * this.getDuration(t) * this.getDuration(t) / 3
                + this.getB(t) * this.getDuration(t) / 2 + this.getC(t));
    }

    public String getDescription() {
        return super.getId() + " - Quadratic squared profile with power in W at left " + this.leftPower.getDescription()
                + ", at center " + this.centerPower.getDescription() + " and at right "
                + this.rightPower.getDescription() + ". " + super.toString();
    }
}
