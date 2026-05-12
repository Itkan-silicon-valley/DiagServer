package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.eclipse30618.ftc.diaglib.ConfigRegistry;
import com.eclipse30618.ftc.diaglib.LimelightTunnelManager;
import com.eclipse30618.ftc.diaglib.SchemaDiagService;

/**
 * Minimal example that shows where to call schema diagnostics in an OpMode.
 *
 * Rename this file to ExampleSchemaDiag.java when you copy it
 * into your FTC project so the public class name matches the file name.
 */
@TeleOp(name = "ExampleSchemaDiag", group = "Examples")
public class ExampleSchemaDiag extends LinearOpMode {
    // Live-tunable variables (example).
    private double shooterKp = 0.015;
    private double turretLockGain = 3.0;

    @Override
    /*
     * Demo flow:
     * 1) Create the Limelight tunnel (optional).
     * 2) Register live config values.
     * 3) Start SchemaDiagService and publish fields each loop.
     */
    public void runOpMode() {
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // (Optional) Start the Limelight tunnel so the laptop can view the camera.
        LimelightTunnelManager limelightTunnelManager = LimelightTunnelManager.createDefault();
        limelightTunnelManager.start();

        // Live config registry: the dashboard can SET these while the robot runs.
        ConfigRegistry configRegistry = new ConfigRegistry();
        // Lambda #1: how to read the value (getter).
        // Lambda #2: how to change the value (setter).
        configRegistry.registerDouble("shooter_kp", () -> shooterKp, v -> shooterKp = v, 0.0, 1.0);
        configRegistry.registerDouble("turret_lock_gain", () -> turretLockGain, v -> turretLockGain = v, 0.0, 10.0);

        SchemaDiagService diagService = new SchemaDiagService(hardwareMap, "configs/diag_schema.json", configRegistry);
        diagService.start();

        waitForStart();
        long runId = System.currentTimeMillis() / 1000L;

        while (opModeIsActive()) {
            diagService.begin();
            diagService.put("run_id", runId);
            diagService.put("robot_ts_ms", System.currentTimeMillis());
            diagService.put("front_left_power", frontLeft.getPower(), "%.3f");
            diagService.put("imu_yaw_deg", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), "%.2f");
            diagService.put("shooter_kp", shooterKp, "%.4f");
            diagService.put("turret_lock_gain", turretLockGain, "%.2f");
            diagService.publish();
        }

        diagService.close();
        limelightTunnelManager.close();
    }
}
