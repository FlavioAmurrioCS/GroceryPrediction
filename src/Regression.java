/**
 * Regression
 */
public interface Regression {
    public void inputXY(double x, double y);

    public void fit();

    public double predictY(double x);
}