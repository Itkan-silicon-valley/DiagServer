# DiagServer

DiagServer is the robot-side diagnostics library for FTC robots. The published Android library is `diaglib`.

## Installation

Add Maven Central to the FTC project repositories (It is present by default by FTC SDK):

```gradle
repositories {
    google()
    mavenCentral()
}
```

Add the library dependency in `TeamCode/build.gradle`:

```gradle
dependencies {
    implementation "com.eclipse30618.ftc:diaglib:0.x.x"
}
```

Add a robot-specific schema file at:

```text
TeamCode/src/main/assets/configs/diag_schema.json
```

Use the diagnostics bus from an OpMode:

```java
import com.eclipse30618.ftc.diaglib.DiagBus;
import com.eclipse30618.ftc.diaglib.SchemaDiagBus;

DiagBus diagBus = new SchemaDiagBus(hardwareMap, "configs/diag_schema.json");
diagBus.start();

diagBus.begin();
diagBus.put("robot_ts_ms", System.currentTimeMillis());
diagBus.put("front_left_power", frontLeft.getPower(), "%.3f");
diagBus.publish();
```

## Project Layout

```text
diaglib/   Android library published as com.eclipse30618.ftc:diaglib
examples/  Copyable FTC OpMode examples
```
