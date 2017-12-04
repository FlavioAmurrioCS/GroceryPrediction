import java.sql.Date;

/**
 * LinReg
 */
public class LinReg {

    double inter; //a
    double slope; //b

    public LinReg(HashMap<Long, Double> salesMap) {
        this.a = getIntercept(salesMap);
        this.b = getSlope(salesMap);
    }

    public LinReg(HashMap<Date, Double> salesMap) {
        this.a = getIntercept(salesMap);
        this.b = getSlope(salesMap);
    }

    public double getY(double xVal) {
        return (xVal * this.slope) + this.inter;
    }

    public double getY(Date date)
    {
        return this.getY((double)date.getTime());
    }

    public void getEquation(HashMap<> salesMap)
    {}



}