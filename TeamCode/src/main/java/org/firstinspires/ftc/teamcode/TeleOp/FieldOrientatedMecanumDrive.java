package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced.PollenMotor;
import org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced.NectarMotor;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMotor;
import org.firstinspires.ftc.teamcode.mechanisms.Servos;

@TeleOp(name = "Field Orientated Mecanum Drive", group = "TeleOp")
public class FieldOrientatedMecanumDrive extends OpMode {
    DriveTrain driveTrain = new DriveTrain();
    IntakeMotor intakeMotor = new IntakeMotor();
    PollenMotor pollenMotor = new PollenMotor();
    NectarMotor nectarMotor = new NectarMotor();
    Servos servos = new Servos();

    @Override
    public void init() {
        driveTrain.init(hardwareMap);
        intakeMotor.init(hardwareMap);
        pollenMotor.init(hardwareMap);
        nectarMotor.init(hardwareMap);
        servos.init(hardwareMap);
    }

    double forward, strafe, rotate, multiplier;
    int intake_motor_c = 1; // constant used for revering the motor intake
    int pollen_motor_c = 1;
    int nectar_motor_c = 1;
    double intakePower = 0.5;

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

        if (gamepad1.circle) {
            intakeMotor.setIntakePower(intake_motor_c * intakePower);
            servos.setUpperServo(1);
            servos.setLowerServo(1);
        } else {
            intakeMotor.setIntakePower(0);
            servos.stopServos();
        }

        // ------------------ Servo Logic -------------------------


        if (gamepad1.square) {
            servos.setUpperServo(1);
            servos.setLowerServo(1);
        } else {
            servos.stopServos();
        }

        // --------------- Fly Wheel Motor Logic ----------------

        if (gamepad1.leftBumperWasReleased()) {
            pollen_motor_c = -pollen_motor_c;
        }

        pollenMotor.setPollenMotor(gamepad1.left_trigger * pollen_motor_c);

        if (gamepad1.rightBumperWasReleased()) {
            nectar_motor_c = -nectar_motor_c;
        }

        nectarMotor.setNectarMotor(gamepad1.right_trigger * nectar_motor_c);

        if (pollenMotor.getRPM() == 1000) {
            servos.setLowerServo(1);
        }
        if (nectarMotor.getRPM() == 1000) {
            servos.setUpperServo(1);
        }


            // ------------- Telemetry ------------------

            telemetry.addData("Pollen motor power", gamepad1.left_trigger * pollen_motor_c);
            telemetry.addData("Nectar motor power", gamepad1.left_trigger * nectar_motor_c);
        }

    }
