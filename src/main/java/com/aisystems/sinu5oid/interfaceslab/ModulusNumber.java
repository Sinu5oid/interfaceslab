package com.aisystems.sinu5oid.interfaceslab;

import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleTypeException;
import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleValueException;
import com.aisystems.sinu5oid.interfaceslab.exceptions.ModulusArithmeticException;
import com.aisystems.sinu5oid.interfaceslab.exceptions.UnsupportedModulusException;
import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;
import org.jetbrains.annotations.NotNull;

public class ModulusNumber implements INumber {
    private final int value;
    private final int N;

    public ModulusNumber(int value, int modulus) throws UnsupportedModulusException {
        if (modulus < 2) {
            throw new UnsupportedModulusException();
        }

        value = value % modulus;
        this.N = modulus;
        this.value = isNormalized(this) ? value : getSymmetricalValue(this);
    }

    public int getValue() {
        return value;
    }

    public int getModulus() {
        return N;
    }

    public static int getSymmetricalValue(ModulusNumber src) {
        return (src.value + src.N * src.value > 0 ? -1 : 1) % src.N;
    }

    public static boolean isNormalized(ModulusNumber src) {
        return 2 * Math.abs(src.value) <= src.N;
    }

    @Override
    public INumber add(@NotNull INumber b) throws IncompatibleTypeException {
        ModulusNumber bCasted = getModulusNumber(b);

        try {
            return new ModulusNumber(this.value + bCasted.value, this.N);
        } catch (UnsupportedModulusException e) {
            // should never happen but anyways
            throw new RuntimeException("illegal instance state");
        }
    }

    @Override
    public INumber subtract(@NotNull INumber b) throws IncompatibleTypeException, ModulusArithmeticException {
        ModulusNumber bCasted = getModulusNumber(b);


        try {
            return new ModulusNumber(this.value - bCasted.value, this.N);
        } catch (UnsupportedModulusException e) {
            // should never happen but anyways
            throw new RuntimeException("illegal instance state");
        }
    }

    @Override
    public INumber multiply(@NotNull INumber b) throws IncompatibleTypeException {
        ModulusNumber bCasted = getModulusNumber(b);

        try {
            return new ModulusNumber(this.value * bCasted.value, this.N);
        } catch (UnsupportedModulusException e) {
            // should never happen but anyways
            throw new RuntimeException("illegal instance state");
        }
    }

    @Override
    public INumber divide(@NotNull INumber b) throws IncompatibleTypeException, IncompatibleValueException {
        if (!(b instanceof ModulusNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        ModulusNumber bCasted = getModulusNumber(b);

        if (!checkIfPrime(bCasted)) {
            throw new IncompatibleValueException(b);
        }

        try {
            return new ModulusNumber(this.value * invertElement(b) % this.N, this.N);
        } catch (UnsupportedModulusException e) {
            // should never happen but anyways
            throw new RuntimeException("illegal instance state");
        }
    }

    private static boolean checkIfPrime(ModulusNumber v) {
        for (int i = 2; i <= v.value / 2; i++) {
            if (v.value % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static int pow(int base, int power, int modulus) {
        if (power == 1) {
            return base;
        }

        if (power % 2 == 0) {
            int t = pow(base, power / 2, modulus);
            return t * t % modulus;
        } else {
            return pow(base, power - 1, modulus) * base % modulus;
        }
    }

    private static int invertElement(@NotNull INumber number) {
        ModulusNumber casted = ((ModulusNumber) number);

        return pow(casted.value, casted.N - 2, casted.N);
    }

    @NotNull
    private ModulusNumber getModulusNumber(@NotNull INumber b) throws IncompatibleTypeException, ModulusArithmeticException {
        if (!(b instanceof ModulusNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        ModulusNumber bCasted = (ModulusNumber) b;
        if (this.N != bCasted.N) {
            throw new ModulusArithmeticException("b has different modulus");
        }

        return bCasted;
    }
}
