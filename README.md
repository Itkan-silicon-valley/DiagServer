# DiagServer

DiagServer is the robot-side diagnostics library for FTC robots. The published Android library is `diaglib`.

## Student Install

Add Maven Central to the FTC project repositories if it is not already present:

```gradle
repositories {
    google()
    mavenCentral()
}
```

Add the library dependency in `TeamCode/build.gradle`:

```gradle
dependencies {
    implementation "com.eclipse30618.ftc:diaglib:0.1.0"
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

## Release Publishing

Maven coordinates:

```text
groupId:    com.eclipse30618.ftc
artifactId: diaglib
version:    0.1.0
```

GitHub Actions publishes to Maven Central when a semver release tag is pushed:

```bash
git tag 0.1.0
git push origin 0.1.0
```

Required GitHub repository secrets:

```text
MAVEN_CENTRAL_USERNAME
MAVEN_CENTRAL_PASSWORD
SIGNING_KEY
SIGNING_PASSWORD
```

The Maven Central username/password are generated from a Central Portal user token. The signing key/password are generated from a local GPG key and provided to CI as in-memory signing credentials.

## Local Checks

Using the committed Gradle wrapper:

```bash
./gradlew :diaglib:build
./gradlew :diaglib:publishToMavenLocal
```

## Project Layout

```text
diaglib/   Android library published as com.eclipse30618.ftc:diaglib
examples/  Copyable FTC OpMode examples
```
