package com.eclipse30618.ftc.diaglib;

public interface DiagBus extends AutoCloseable {
    void start();

    void begin();

    void put(String name, String value);

    void put(String name, double value, String format);

    void put(String name, long value);

    void publish();

    LiveDiagConfigRegistry config();

    boolean isEnabled();

    @Override
    void close();
}
