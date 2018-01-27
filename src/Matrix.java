import java.util.ArrayList;

/**
 * Matrix
 */
public class Matrix {

    private double[][] mat;

    public Matrix(int dim) {
        this.mat = new double[dim][dim];
    }

    public Matrix(double[]... arr) {
        this.mat = arr;
    }

    public Vector getRow(int row) {
        return new Vector(mat[row]);
    }

    public Vector getCol(int col) {
        return new Vector(VecMat.getCol(this.mat, col));
    }

    public double getValue(int row, int col) {
        return mat[row][col];
    }

    public void setValue(double val, int row, int col) {
        mat[row][col] = val;
    }

    public Matrix transpose() {
        return new Matrix(VecMat.transpose(mat));
    }

    public String toString() {
        return VecMat.toString(mat);
    }

    public static Matrix identity(int size) {
        return new Matrix(VecMat.identity(size));
    }
}