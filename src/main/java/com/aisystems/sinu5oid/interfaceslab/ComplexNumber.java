package com.aisystems.sinu5oid.interfaceslab;

import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleTypeException;
import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ComplexNumber implements INumber {
    double re;
    double im;

    public ComplexNumber(double re) {
        this.re = re;
        this.im = 0;
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.re, re) == 0 &&
                Double.compare(that.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public INumber add(@NotNull INumber b) throws IncompatibleTypeException {
        ComplexNumber bCasted = getCastedValue(b);

        return new ComplexNumber(this.re + bCasted.re, this.im + bCasted.im);
    }

    @Override
    public INumber subtract(@NotNull INumber b) throws IncompatibleTypeException {
        ComplexNumber bCasted = getCastedValue(b);

        return new ComplexNumber(this.re - bCasted.re, this.im - bCasted.im);
    }

    @Override
    public INumber multiply(@NotNull INumber b) throws IncompatibleTypeException {
        ComplexNumber bCasted = getCastedValue(b);

        return new ComplexNumber(
                this.re * bCasted.re - this.im * bCasted.im,
                this.im * bCasted.re + this.re * bCasted.re
        );
    }

    @Override
    public INumber divide(@NotNull INumber b) throws IncompatibleTypeException {
        ComplexNumber bCasted = getCastedValue(b);

        double reDividend = this.re * bCasted.re + this.im * bCasted.im;
        double imDividend = this.im * bCasted.re - this.re * bCasted.im;
        double divisor = Math.pow(bCasted.re, 2) + Math.pow(bCasted.im, 2);

        return new ComplexNumber(reDividend / divisor, imDividend / divisor);
    }

    public ComplexNumber getConjugate() {
        return getConjugate(this);
    }

    public static ComplexNumber getConjugate(@NotNull ComplexNumber v) {
        return new ComplexNumber(v.re, -v.im);
    }

    @NotNull
    private ComplexNumber getCastedValue(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof ComplexNumber || b instanceof RealNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        if (b instanceof RealNumber) {
            return new ComplexNumber(((RealNumber) b).getValue(), 0);
        }

        return (ComplexNumber) b;

    }
}
