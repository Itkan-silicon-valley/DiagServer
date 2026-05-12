package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.eclipse30618.ftc.diaglib.LimelightTunnelManager;
import com.eclipse30618.ftc.diaglib.SchemaDiagBus;
import com.eclipse30618.ftc.diaglib.DiagBus;

/**
 * Example of the new DiagBus usage with live-config.
 *
 * Rename this file if needed so the public class name matches the file name.
 */
@TeleOp(name = "ExampleSchemaDiagBus", group = "Examples")
public class ExampleSchemaDiagBus extends LinearOpMode {
    private static final String DIAG_SCHEMA_PATH = "configs/diag_schema.json";

    // Live-tunable variables (example).
    private double shooterKp = 0.015;
    private double turretLockGain = 3.0;

    @Override
    public void runOpMode() {
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // (Optional) Start the Limelight tunnel so the laptop can view the camera.
        LimelightTunnelManager limelightTunnelManager = LimelightTunnelManager.createDefault();
        limelightTunnelManager.start();

        DiagBus diagBus = new SchemaDiagBus(hardwareMap, DIAG_SCHEMA_PATH);

        // Live config registry: the dashboard can SET these while the robot runs.
        diagBus.config().registerDouble("shooter_kp", () -> shooterKp, v -> shooterKp = v, 0.0, 1.0);
        diagBus.config()
                .registerDouble("turret_lock_gain", () -> turretLockGain, v -> turretLockGain = v, 0.0, 10.0);

        diagBus.start();

        waitForStart();
        long runId = System.currentTimeMillis() / 1000L;

        while (opModeIsActive()) {
            diagBus.begin();
            diagBus.put("run_id", runId);
            diagBus.put("robot_ts_ms", System.currentTimeMillis());
            diagBus.put("front_left_power", frontLeft.getPower(), "%.3f");
            diagBus.put(
                    "imu_yaw_deg",
                    imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES),
                    "%.2f");
            diagBus.put("shooter_kp", shooterKp, "%.4f");
            diagBus.put("turret_lock_gain", turretLockGain, "%.2f");
            diagBus.publish();
        }

        diagBus.close();
        limelightTunnelManager.close();
    }
}
