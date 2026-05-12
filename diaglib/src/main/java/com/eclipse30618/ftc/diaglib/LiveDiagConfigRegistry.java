package com.eclipse30618.ftc.diaglib;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public interface LiveDiagConfigRegistry {
    void registerDouble(
            String name, DoubleSupplier getter, DoubleConsumer setter, double min, double max);
}
