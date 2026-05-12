package com.eclipse30618.ftc.diaglib;

import java.util.Arrays;
import java.util.Locale;

/**
 * Builds a snapshot by filling a fixed list of string values.
 *
 * This is the "builder" you use each loop before sending data.
 */
public class DiagSnapshotBuilder {
    private final String[] values;

    /**
     * Prepare a builder sized to the total field count in the schema.
     */
    public DiagSnapshotBuilder(int fieldCount) {
        // Prepare a blank list of values for this loop.
        values = new String[fieldCount];
        Arrays.fill(values, "");
    }

    /**
     * Set a raw string value at the given field index.
     */
    public void set(int index, String value) {
        // Save a string value at a specific index.
        values[index] = DiagSchema.sanitizeCsv(value);
    }

    /**
     * Set a numeric value at the given field index with formatting.
     */
    public void set(int index, double value, String format) {
        // Save a number using a specific format (ex: "%.2f").
        if (Double.isNaN(value)) {
            values[index] = "";
            return;
        }
        values[index] = String.format(Locale.US, format, value);
    }

    /**
     * Build an immutable snapshot from the current values.
     */
    public DiagSnapshot build() {
        // Turn the filled list into an immutable snapshot.
        return new DiagSnapshot(values);
    }
}
