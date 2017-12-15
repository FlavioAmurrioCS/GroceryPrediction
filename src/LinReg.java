import java.util.Date;
import java.util.HashMap;

/**
 * LinReg
 */
public class LinReg {

    double inter; //a
    double slope; //b

    public LinReg(HashMap<Date, Double> salesMap) {
        getEquation(salesMap);
    }

    public double getY(double xVal) {
        return (xVal * this.slope) + this.inter;
    }

    public double getY(Date date) {
        return this.getY((double) date.getTime());
    }

    public void getEquation(HashMap<Date, Double> salesMap) {
        double size = salesMap.size();
        double ySum = 0;
        double xSum = 0;
        double xSqSum = 0;
        double xySum = 0;
        for (Date dt : salesMap.keySet()) {
            double x = dt.getTime();
            double y = salesMap.get(dt);
            xSum += x;
            ySum += y;
            xSqSum = (x * x);
            xySum = (x * y);
        }
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
    }

}