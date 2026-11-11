package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced.FlyWheelMotor;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotor;

@TeleOp(name = "Field Orientated Mecanum Drive", group = "TeleOp")
public class FieldOrientatedMecanumDrive extends OpMode {
    DriveTrain driveTrain = new DriveTrain();
    IntakeMotor intakeMotor = new IntakeMotor();
    FlyWheelMotor flyWheelMotor = new FlyWheelMotor();

    @Override
    public void init() {
        driveTrain.init(hardwareMap);
        intakeMotor.init(hardwareMap);
        flyWheelMotor.init(hardwareMap);
    }
    double forward, strafe, rotate, multiplier;
    int intake_motor_c = 1; // constant used for revering the motor intake
    int fly_wheel_motor_c = 1;

    @Override
    public void loop() {
        // --------------- Drive Train Logic ----------------

        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        multiplier = gamepad1.square ? 0.5 : 1;

        driveTrain.driveFieldRelative(forward, strafe, rotate, multiplier);

        // --------------- Intake Motor Logic ----------------

        if (gamepad1.crossWasReleased()) {
            intake_motor_c = -intake_motor_c;
        }

        if (gamepad1.right_trigger_pressed)
            intakeMotor.setIntakePower(intake_motor_c);
        else {
            intakeMotor.setIntakePower(0);
        }

        // --------------- Fly Wheel Motor Logic ----------------

        if (gamepad1.leftBumperWasReleased()) {
            fly_wheel_motor_c = -fly_wheel_motor_c;
        }

        flyWheelMotor.setFlyWheelMotor(gamepad1.left_trigger * fly_wheel_motor_c);

        // --------------- Telemetry ----------------

        telemetry.addData("Fly wheel motor power", gamepad1.left_trigger * fly_wheel_motor_c);
    }
}
