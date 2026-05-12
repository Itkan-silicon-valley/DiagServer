package com.eclipse30618.ftc.diaglib;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiagSnapshotTest {
    @Test
    public void builderSanitizesStringValuesForCsv() {
        DiagSnapshotBuilder builder = new DiagSnapshotBuilder(1);

        builder.set(0, "ready,aim\nfire\rnow");

        assertEquals("ready;aim fire now", builder.build().toCsv(new int[] {0}));
    }

    @Test
    public void buildReturnsImmutableSnapshotCopy() {
        DiagSnapshotBuilder builder = new DiagSnapshotBuilder(1);
        builder.set(0, "first");
        DiagSnapshot snapshot = builder.build();

        builder.set(0, "second");

        assertEquals("first", snapshot.get(0));
    }

    @Test
    public void constructorDefensivelyCopiesValues() {
        String[] values = new String[] {"first"};
        DiagSnapshot snapshot = new DiagSnapshot(values);

        values[0] = "second";

        assertEquals("first", snapshot.get(0));
    }
}
