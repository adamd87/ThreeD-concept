package pl.adamd;

public class Matrix {
    double[] values;


    public Matrix(double[] values) {
        this.values = values;
    }

    /**
     * The multiplication between matrices and matrices
     * @param otherMatrix Matrix to multiply
     * @return multiplied Matrix
     */
    public Matrix multiply(Matrix otherMatrix) {
        double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] += this.values[row * 3 + i] * otherMatrix.values[i * 3 + col];
                }
            }
        }
        return new Matrix(result);
    }

    public Vertex transform(Vertex in) {
        return new Vertex(
                in.x * values[0] + in.y * values[3] + in.z * values[6],
                in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }

    /**
     * Rotate xz plane and yz plane
     * @param x axis is used to distinguish up and down
     * @param y axis is used to distinguish left and right
     * @return Transformed Matrix
     */
    public static Matrix getTransform(double x, double y) {

        double heading = Math.toRadians(x);
        Matrix headingTransform = new Matrix(new double[]{
                Math.cos(heading), 0, -Math.sin(heading),
                0, 1, 0,
                Math.sin(heading), 0, Math.cos(heading)
        });

        double pitch = Math.toRadians(y);
        Matrix pitchTransform = new Matrix(new double[]{
                1, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch),
                0, -Math.sin(pitch), Math.cos(pitch)
        });

        return headingTransform.multiply(pitchTransform);
    }


}
