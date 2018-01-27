import java.util.Arrays;

/**
 * VecMat
 */
public class VecMat {

    // --------------------------------------------------
    //                          Vector
    // --------------------------------------------------

    public static double[] add(double[] v1, double[] v2) {
        lenCheck(v1, v2);
        double[] ret = new double[v1.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = v1[i] + v2[i];
        return ret;
    }

    public static double[] subtract(double[] v1, double[] v2) {
        lenCheck(v1, v2);
        double[] ret = new double[v1.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = v1[i] - v2[i];
        return ret;
    }

    public static double[] multiply(double[] v1, double scalar) {
        double[] ret = new double[v1.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = v1[i] * scalar;
        return ret;
    }

    public static double[] divide(double[] v1, double div) {
        return multiply(v1, 1 / div);
    }

    public static double length(double[] v1) {
        double sum = 0;
        for (double val : v1)
            sum += val * val;
        return Math.sqrt(sum);
    }

    public static double dotProduct(double[] v1, double[] v2) {
        lenCheck(v1, v2);
        double dp = 0;
        for (int i = 0; i < v1.length; i++)
            dp += v1[i] * v2[i];
        return dp;
    }

    public static boolean equals(double[] v1, double[] v2) {
        if (v1.length != v2.length)
            return false;
        for (int i = 0; i < v1.length; i++)
            if (v1[i] != v2[i])
                return false;
        return true;
    }

    public static void lenCheck(double[] v1, double[] v2) {
        if (v1.length != v2.length)
            throw new Exception();
    }

    // --------------------------------------------------
    //                          Matrix
    // --------------------------------------------------

    public static double[][] add(double[][] mat1, double[][] mat2) {
        if (mat1.length != mat2.length || mat1[0].length != mat2[0].length)
            throw new Exception();
        double[][] ret = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < ret.length; i++)
            for (int j = 0; j < ret[0].length; j++)
                ret[i][j] = mat1[i][j] + mat2[i][j];
        return ret;
    }

    public static double[][] subtract(double[][] mat1, double[][] mat2) {
        if (mat1.length != mat2.length || mat1[0].length != mat2[0].length)
            throw new Exception();
        double[][] ret = new double[mat1.length][mat1[0].length];
        for (int i = 0; i < ret.length; i++)
            for (int j = 0; j < ret[0].length; j++)
                ret[i][j] = mat1[i][j] - mat2[i][j];
        return ret;
    }

    public static double[][] multiply(double[][] mat, double scalar) {
        double[][] ret = new double[mat.length][mat[0].length];
        for (int i = 0; i < ret.length; i++)
            for (int j = 0; j < ret[0].length; j++)
                ret[i][j] = mat[i][j];
        return ret;
    }

    public static double[][] divide(double[][] mat, double div) {
        return multiply(mat, 1 / div);
    }

    public static double[][] multiply(double[][] mat1, double[][] mat2) {
        if (mat1[0].length != mat2.length)
            throw new Exception();
        double[][] ret = new double[mat1.length][mat2[0].length];
        for(int i = )
    }

    // Multiply by inverse
    public static double[][] divide(double[][] mat1, double[][] mat2) {

    }

    public static double[][] multiply(double[][] mat, double[] vec) {

    }

    public static double[][] multiply(double[] vec, double[][] mat) {

    }

    public static double[][] transpose(double[][] mat) {
        double[][] tran = new double[mat[0].length][mat.length];
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++)
                tran[j][i] = mat[i][j];
        return tran;
    }

    public static double[][] identity(int size) {
        double[][] mat = new double[size][size];
        for (int i = 0; i < size; i++)
            mat[i][i] = 1;
        return mat;
    }

    public static double[] getCol(double[][] mat, int col) {
        double[] vec = new double[mat.length];
        for (int i = 0; i < vec.length; i++)
            vec[i] = mat[col][i];
        return vec;
    }

    public static double[] getRow(double[][] mat, int row) {
        return mat[row];
    }

    public static String toString(double[]... arr) {
        StringBuilder sb = new StringBuilder();
        for (double[] vec : arr)
            sb.append(Arrays.toString(vec) + "\n");
        return sb.toString();
    }
}