import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

/**
 * 2DBlockMatrix
 */
public class SparseTwoDBlockMatrix {

    public float[][] matrix;
    public int row;
    public int column;
    public Float[][] arr;

    public SparseTwoDBlockMatrix(float[][] array) {
        this.matrix = array;
        this.row = array.length;
        this.column = array[0].length;

    }

    public static float[] convert(String[] ar) {
        float[] f = new float[ar.length];
        for (int i = 0; i < ar.length; i++) {
            f[i] = Float.valueOf(ar[i]);
        }

        return f;
    }

    public static SparseTwoDBlockMatrix buildTwoDBlockMatrix(InputStream in) throws IOException {
        Scanner s = new Scanner(in);
        Vector<Float[]> a = new Vector<Float[]>();

        int columns = 0;
        int rows = 0;

        while (s.hasNextLine()) {
            int x = s.nextInt();
            int y = s.nextInt();
            s.nextLine();

            String CheckHash = s.nextLine();
            int row = x;
            while (!CheckHash.equals("#")) {
                String[] split = CheckHash.split(" ");
                split[split.length - 1] = split[split.length - 1].replace(";", "");

                float[] f1 = convert(split);
                int f1size = f1.length;

                for (int i = 0; i < f1size; i++) {
                    Float[] newv = new Float[3];
                    float r = row;
                    float yy = y;
                    newv[0] = r;
                    newv[1] = yy + i;
                    newv[2] = f1[i];
                    a.add(newv);
                }

                if (rows < row) {
                    rows = row;
                }

                if (columns < f1size + y) {
                    columns = f1size + y - 1;
                }

                CheckHash = s.nextLine();
                row++;

            }

        }

        float[][] mat = new float[rows][columns];

        for (int i = 0; i < a.size(); i++) {
            Float[] b = a.get(i);
            mat[b[0].intValue() - 1][b[1].intValue() - 1] = b[2];
        }

        s.close();

        SparseTwoDBlockMatrix d = new SparseTwoDBlockMatrix(mat);
        return d;
    }

    public SparseTwoDBlockMatrix multiply(SparseTwoDBlockMatrix other) throws IncompatibleDimensionException {

        if (this.column == other.row) {
            float[][] omat = other.matrix;
            float[][] thismat = this.matrix;
            float[][] arr = new float[this.row][other.column];
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < other.column; j++) {
                    float sum = 0;
                    for (int k = 0; k < this.column; k++) {
                        sum += thismat[i][k] * omat[k][j];
                    }
                    arr[i][j] = sum;
                }
            }

            SparseTwoDBlockMatrix n = new SparseTwoDBlockMatrix(arr);
            return n;
        } else
            throw new IncompatibleDimensionException("Matrix Dimension are Incompatible for Multiplication");
    }

    public SparseTwoDBlockMatrix transpose() {
        float[][] th = this.matrix;
        float[][] arr = new float[this.column][this.row];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = th[j][i];
            }
        }
        SparseTwoDBlockMatrix trans = new SparseTwoDBlockMatrix(arr);
        return trans;
    }

    public SparseTwoDBlockMatrix getSubBlock(int row_start, int col_start, int row_end, int col_end)
            throws SubBlockNotFoundException {
        if (row_start >= 1 && col_start >= 1 && row_end > row_start && col_end > col_start && row_end <= this.row + 1
                && col_end <= this.column + 1) {

            float[][] arr = new float[row_end - row_start][col_end - col_start];
            float[][] th = this.matrix;
            for (int i = 0; i < row_end - row_start; i++) {
                for (int j = 0; j < col_end - col_start; j++) {
                    arr[i][j] = th[row_start + i - 1][col_start + j - 1];
                }
            }

            SparseTwoDBlockMatrix sub = new SparseTwoDBlockMatrix(arr);
            return sub;
        } else {
            throw new SubBlockNotFoundException("Sub Block not Found");
        }
    }

    public boolean checkInt(float a) {
        if ((int) a == a) {
            return true;
        } else
            return false;
    }

    public float roun(float f) {

        if ((int) (f * 1000 % 10) == 5) {
            if ((int) (f * 100 % 10) % 2 == 0) {
                return (float) Math.round(f * 100.0f - 1.0f) / 100f;
            }
        }
        return (float) Math.round(f * 100) / 100f;
    }

    public String toString() {

        String s = "";
        boolean[][] b = new boolean[this.row][this.column];

        float[][] g = this.matrix;

        for (int i = 0; i < this.row; i++) {

            for (int j = 0; j < this.column; j++) {

                if (b[i][j] != true && g[i][j] != 0.0) {

                    s += Integer.toString(i + 1) + " " + Integer.toString(j + 1) + "\n";
                    int checkint = (int) g[i][j];
                    if (checkint == g[i][j]) {
                        s += checkint;
                    } else
                        s += roun(g[i][j]);

                    b[i][j] = true;
                    int k = j + 1;
                    boolean checkzero = false;
                    int l = i;
                    int blockcolumn = 1;
                    while (k < this.column && g[l][k] != 0 && b[l][k] != true) {
                        checkint = (int) g[l][k];
                        if (checkint == g[l][k]) {
                            s += " " + checkint;
                        } else
                            s += " " + roun(g[l][k]);
                        b[l][k] = true;
                        k++;
                        blockcolumn++;
                    }
                    s += ";\n";
                    k = j;
                    l++;
                    String below1row = "";
                    while (checkzero == false && l < this.row) {
                        if (k < blockcolumn + j && g[l][k] != 0 && b[l][k] != true) {
                            checkint = (int) g[l][k];
                            if (checkint == g[l][k]) {
                                below1row += checkint;
                            } else {
                                below1row += roun(g[l][k]);
                            }

                            b[l][k] = true;
                            k++;

                        }

                        while (k < blockcolumn + j && g[l][k] != 0 && b[l][k] != true) {
                            checkint = (int) g[l][k];
                            if (checkint == g[l][k]) {
                                below1row += " " + checkint;
                            } else
                                below1row += " " + roun(g[l][k]);
                            b[l][k] = true;
                            k++;

                        }

                        if (k == blockcolumn + j) {
                            l++;
                            k = j;
                            s += below1row + ";\n";
                            below1row = "";

                        } else {
                            checkzero = true;
                            for (int m = j; m < k; m++) {
                                b[l][m] = false;
                            }
                        }
                    }
                    s += "#\n";
                    j = k - 1;
                }
            }
        }

        return s;

    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    public float[][] subblockDeter(float[][] ar, int x, int y) {
        // x and y are indexed from 1
        int l = ar.length;
        float[][] f = new float[l - 1][l - 1];
        for (int i = 0; i < x - 1; i++) {
            for (int j = 0; j < y - 1; j++) {
                f[i][j] = ar[i][j];
            }
        }
        for (int i = x; i < l; i++) {
            for (int j = 0; j < y - 1; j++) {
                f[i - 1][j] = ar[i][j];
            }
        }
        for (int i = 0; i < x - 1; i++) {
            for (int j = y; j < l; j++) {
                f[i][j - 1] = ar[i][j];
            }
        }
        for (int i = x; i < l; i++) {
            for (int j = y; j < l; j++) {
                f[i - 1][j - 1] = ar[i][j];
            }
        }
        return f;
    }

    public float determinant(float[][] mat) {

        int l = mat.length;
        if (l == 1) {
            return mat[0][0];
        }
        if (l == 2) {
            return (mat[0][0] * mat[1][1]) - (mat[1][0] * mat[0][1]);
        }
        float sum = 0;
        for (int i = 0; i < l; i++) {
            if (mat[0][i] != 0) {
                sum += (float) Math.pow(-1, i) * mat[0][i] * determinant(subblockDeter(mat, 1, i + 1));
            }
        }
        return sum;
    }

    public SparseTwoDBlockMatrix inverse() throws InverseDoesNotExistException {
        if (this.column == this.row) {
            float[][] arr = new float[this.row][this.row];
            float deter = 0;
            for (int i = 0; i < arr.length; i++) {
                float det = determinant(subblockDeter(this.matrix, 1, i + 1));
                deter += Math.pow(-1, i) * this.matrix[0][i] * det;
                arr[i][0] = (float) Math.pow(-1, i) * det;
            }

            if (deter == 0) {
                throw new InverseDoesNotExistException("Non Invertible, Determinant of Matrix is Zero");
            }

            for (int i = 0; i < arr.length; i++) {
                for (int j = 1; j < arr[0].length; j++) {
                    arr[i][j] = (float) Math.pow(-1, i + j) * determinant(subblockDeter(this.matrix, j + 1, i + 1))
                            / deter;
                }
            }

            for (int i = 0; i < arr.length; i++) {
                arr[i][0] = arr[i][0] / deter;
            }

            SparseTwoDBlockMatrix d = new SparseTwoDBlockMatrix(arr);
            return d;
        } else
            throw new InverseDoesNotExistException("Non Invertible, Non Square Matrix");

    }
}