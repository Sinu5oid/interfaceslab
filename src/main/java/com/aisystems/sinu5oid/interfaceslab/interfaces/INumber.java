package com.aisystems.sinu5oid.interfaceslab.interfaces;

import org.jetbrains.annotations.NotNull;

public interface INumber {
    INumber add(@NotNull INumber b);

    INumber subtract(@NotNull INumber b);

    INumber multiply(@NotNull INumber b);

    INumber divide(@NotNull INumber b);
}
