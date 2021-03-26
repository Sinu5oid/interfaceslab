package com.aisystems.sinu5oid.interfaceslab.exceptions;

import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;

public class IncompatibleTypeException extends ArithmeticException {
    INumber a;
    INumber b;

    public IncompatibleTypeException(INumber a, INumber b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public String getMessage() {

        return "a and b have incompatible types: " +
                a.getClass().getName() +
                ", " +
                b.getClass().getName();
    }
}
