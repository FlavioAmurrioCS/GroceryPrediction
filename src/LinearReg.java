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
    boolean whole = true;
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
        this.whole = (xySum % 1 == 0);
        double bottom = (size * xSqSum) - (xSum * xSum);
        this.inter = ((ySum * xSqSum) - (xSum * xySum)) / (bottom);
        this.slope = ((size * xySum) - (xSum * ySum)) / (bottom);
    }

    public double predictY(double xVal) {
        Integer count = sizeTracker.get((int) size);
        if (count == null)
            count = 0;
        sizeTracker.put((int) size, count + 1);
        if (size == 0)
            return 0;
        if (size < 4)
            return ySum / size;
        double ans = (xVal * this.slope) + this.inter;
        // if (ans < 0)
        //     return 0;
        return this.whole ? (double) Math.round(ans) : ((double) Math.round(ans * 100) / 100);
    }
}