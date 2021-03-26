package com.aisystems.sinu5oid.interfaceslab;

import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleTypeException;
import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;
import org.jetbrains.annotations.NotNull;

public class RealNumber implements INumber {
    private final double value;

    public RealNumber(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public INumber add(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof RealNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        RealNumber bCasted = (RealNumber) b;

        return new RealNumber(value + bCasted.value);
    }

    @Override
    public INumber subtract(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof RealNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        RealNumber bCasted = (RealNumber) b;

        return new RealNumber(value - bCasted.value);
    }

    @Override
    public INumber multiply(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof RealNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        RealNumber bCasted = (RealNumber) b;

        return new RealNumber(value * bCasted.value);
    }

    @Override
    public INumber divide(@NotNull INumber b) throws IncompatibleTypeException {
        if (!(b instanceof RealNumber)) {
            throw new IncompatibleTypeException(this, b);
        }

        RealNumber bCasted = (RealNumber) b;

        return new RealNumber(value / bCasted.value);
    }
}
