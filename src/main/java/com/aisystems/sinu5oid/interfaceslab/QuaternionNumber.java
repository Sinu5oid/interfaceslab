package com.aisystems.sinu5oid.interfaceslab;

import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleTypeException;
import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;
import org.jetbrains.annotations.NotNull;

/**
 * Complex-based matrix representation quaternion
 **/
public class QuaternionNumber implements INumber {
    ComplexNumber[][] state;

    public QuaternionNumber(ComplexNumber[][] state) {
        this.state = state;
    }

    public QuaternionNumber(ComplexNumber alpha, ComplexNumber beta) {
        this.state = new ComplexNumber[][]{
                {alpha, beta},
                {alpha.getConjugate(), beta.getConjugate()}
        };
    }

    public QuaternionNumber(ComplexNumber alpha, ComplexNumber beta, ComplexNumber alphaConjugated, ComplexNumber betaConjugated) {
        this.state = new ComplexNumber[][]{
                {alpha, beta},
                {alphaConjugated, betaConjugated}
        };
    }

    public ComplexNumber[][] asComplexMatrix() {
        return this.state;
    }

    @Override
    public INumber add(@NotNull INumber b) throws IncompatibleTypeException {
        QuaternionNumber bCasted = getCastedValue(b);

        return new QuaternionNumber(
                (ComplexNumber) this.state[0][0].add(bCasted.state[0][0]),
                (ComplexNumber) this.state[0][1].add(bCasted.state[0][1])
        );
    }

    @Override
    public INumber subtract(@NotNull INumber b) throws IncompatibleTypeException {
        QuaternionNumber bCasted = getCastedValue(b);

        return new QuaternionNumber(
                (ComplexNumber) this.state[0][0].subtract(bCasted.state[0][0]),
                (ComplexNumber) this.state[0][1].subtract(bCasted.state[0][1])
        );
    }

    // Grassman's multiplication
    @Override
    public INumber multiply(@NotNull INumber b) throws IncompatibleTypeException {
        QuaternionNumber bCasted = getCastedValue(b);

        return new QuaternionNumber(multiplyMatrices(this.state, bCasted.state));
    }

    @Override
    public INumber divide(@NotNull INumber b) throws IncompatibleTypeException {
        QuaternionNumber bCasted = getCastedValue(b);

        return this.multiply(bCasted.invert());
    }

    @NotNull
    private QuaternionNumber getCastedValue(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof QuaternionNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        return (QuaternionNumber) b;
    }

    private ComplexNumber[][] multiplyMatrices(ComplexNumber[][] firstMatrix, ComplexNumber[][] secondMatrix) {
        ComplexNumber[][] result = new ComplexNumber[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    private ComplexNumber multiplyMatricesCell(ComplexNumber[][] firstMatrix, ComplexNumber[][] secondMatrix, int row, int col) {
        ComplexNumber cell = new ComplexNumber(0);
        for (int i = 0; i < secondMatrix.length; i++) {
            cell = (ComplexNumber) cell.add(firstMatrix[row][i].multiply(secondMatrix[i][col]));
        }
        return cell;
    }

    public QuaternionNumber getConjugate() {
        return getConjugate(this);
    }

    public static QuaternionNumber getConjugate(QuaternionNumber v) {
        ComplexNumber[][] transposed = transposeMatrix(v.state);

        for (int i = 0; i < transposed.length; i++)
            for (int j = 0; j < transposed[i].length; j++)
                transposed[i][j] = transposed[i][j].getConjugate();

        return new QuaternionNumber(transposed[0][0], transposed[0][1], transposed[1][0], transposed[1][1]);
    }

    private static ComplexNumber[][] transposeMatrix(ComplexNumber[][] matrix) {
        ComplexNumber[][] transpose = new ComplexNumber[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                transpose[j][i] = matrix[i][j];
        return transpose;
    }

    private static ComplexNumber getDeterminant(ComplexNumber[][] matrix) {
        if (matrix.length != matrix[0].length)
            throw new IllegalStateException("invalid dimensions");

        if (matrix.length == 2)
            return (ComplexNumber) matrix[0][0].multiply(matrix[1][1]).subtract(matrix[0][1].multiply(matrix[1][0]));

        ComplexNumber det = new ComplexNumber(0);
        for (int i = 0; i < matrix[0].length; i++)
            det.add(
                    matrix[0][i]
                            .multiply(new ComplexNumber(Math.pow(-1, i), 0))
                            .multiply(getDeterminant(getMinor(matrix, 0, i))));
        return det;
    }

    private static ComplexNumber[][] getMinor(ComplexNumber[][] matrix, int row, int column) {
        ComplexNumber[][] minor = new ComplexNumber[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }

    private QuaternionNumber invert() {
        return invert(this);
    }

    private static QuaternionNumber invert(QuaternionNumber v) {
        ComplexNumber det = getDeterminant(v.state);
        det = (ComplexNumber) det.multiply(det); // because it is squared

        ComplexNumber[][] identityIntermediate = new ComplexNumber[][]{
                {det, new ComplexNumber(0)},
                {new ComplexNumber(0), det}
        };

        return new QuaternionNumber(v.multiplyMatrices(v.getConjugate().state, identityIntermediate));
    }
}
