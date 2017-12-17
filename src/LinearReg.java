/**
 * LinearReg
implements Regression */
public class LinearReg implements Regression {

    double size = 0;
    double ySum = 0;
    double xSum = 0;
    double xSqSum = 0;
    double xySum = 0;
    boolean finalize = false;
    double slope;
    double inter;

    public void inputXY(double x, double y) {
        this.xSum += x;
        this.ySum += y;
        this.xSqSum += (x * x);
        this.xySum += (x * y);
        this.size++;
    }

    public synchronized void fit() {
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
        finalize = true;
    }

    public double predictY(double xVal) {
        if (finalize == false)
            this.fit();
        if (size < 4)
            return ySum / size;
        return (xVal * this.slope) + this.inter;
    }
}