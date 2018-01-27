import java.util.Arrays;

/**
 * Vector
 */
public class Vector {

    private double[] arr;

    public Vector(int size) {
        if (size < 1)
            throw new Exception();
        this.arr = new double[size];
    }

    public Vector(double... values) {
        this.arr = values;
    }

    public double getValue(int pos) {
        return this.arr[pos];
    }

    public void setValue(int pos, double val) {
        this.arr[pos] = val;
    }

    public int size() {
        return this.arr.length;
    }

    public String toString() {
        return Arrays.toString(this.arr);
    }

    public void print() {
        System.out.println(this.toString());
    }

    public Vector add(Vector v2) {
        return new Vector(VecMat.add(this.arr, v2.arr));
    }

    public Vector subtract(Vector v2) {
        return new Vector(VecMat.subtract(this.arr, v2.arr));
    }

    public boolean equals(Vector v2) {
        return VecMat.equals(this.arr, v2.arr);
    }

    public boolean notEquals(Vector v2) {
        return !this.equals(v2);
    }

    public double length() {
        return VecMat.length(this.arr);
    }

    public Vector multiply(double scalar) {
        return new Vector(VecMat.multiply(this.arr, scalar));
    }

    public Vector divide(double div) {
        return new Vector(VecMat.divide(this.arr, div));
    }

    public double dotProduct(Vector v2) {
        return VecMat.dotProduct(this.arr, v2.arr);
    }

    public double[] getArr() {
        return this.arr;
    }
}