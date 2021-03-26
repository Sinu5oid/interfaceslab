package com.aisystems.sinu5oid.interfaceslab.exceptions;

import com.aisystems.sinu5oid.interfaceslab.interfaces.INumber;

public class IncompatibleValueException extends ArithmeticException {
    INumber a;

    public IncompatibleValueException(INumber a) {
        super();
        this.a = a;
    }
}
