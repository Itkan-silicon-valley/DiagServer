package com.eclipse30618.ftc.diaglib;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class NoopDiagBus implements DiagBus {
    private static final LiveDiagConfigRegistry NOOP_CONFIG =
            new LiveDiagConfigRegistry() {
                @Override
                public void registerDouble(
                        String name,
                        DoubleSupplier getter,
                        DoubleConsumer setter,
                        double min,
                        double max) {}
            };

    @Override
    public void start() {}

    @Override
    public void begin() {}

    @Override
    public void put(String name, String value) {}

    @Override
    public void put(String name, double value, String format) {}

    @Override
    public void put(String name, long value) {}

    @Override
    public void publish() {}

    @Override
    public LiveDiagConfigRegistry config() {
        return NOOP_CONFIG;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void close() {}
}
