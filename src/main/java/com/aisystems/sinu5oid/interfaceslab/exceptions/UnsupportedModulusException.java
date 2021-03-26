package com.aisystems.sinu5oid.interfaceslab.exceptions;

import com.sun.jdi.InvalidTypeException;

public class UnsupportedModulusException extends InvalidTypeException {
    public UnsupportedModulusException() {
        super();
    }

    public UnsupportedModulusException(String message) {
        super(message);
    }
}
