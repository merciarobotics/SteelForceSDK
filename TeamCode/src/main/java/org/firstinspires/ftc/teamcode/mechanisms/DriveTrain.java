package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveTrain {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private IMU imu;

    public void init(HardwareMap hwMap) {

        // This code maps the variables to the hardware i.e. the motors

        frontLeftMotor = hwMap.get(DcMotor.class, "FL");
        backLeftMotor = hwMap.get(DcMotor.class, "BL");
        frontRightMotor = hwMap.get(DcMotor.class, "FR");
        backRightMotor = hwMap.get(DcMotor.class, "BR");

        // Motor directions

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        /*
        Motor mode:
        RUN_USING_ENCODER tells the motor controller to use the motor's encoder feedback to regulate its speed.
        An encoder measures how much a motor has rotated and can be used to determine its rotational speed.
         */

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
        This code makes the motors brake when no power is applied This means when you release the sticks,
        the motors actively brake rather than letting the robot coast.
        */

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Mapping the imu variable to the imu hardware

        imu = hwMap.get(IMU.class, "imu");

        // Sets the direction of the front facing direction of the robot and the direction of the USB on the control hub

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );

        // Initializes the IMU

        imu.initialize(new IMU.Parameters(RevOrientation));
    }
    public void drive(double forward, double strafe, double rotate, double multiplier) {

        /*
        This code calculates how much power each of the four motors needs to move a mecanum-drive robot
        in the desired direction. It combines forward (forward/backward movement), strafe (strafing left/right),
        and rotate (turning) to calculate each wheel’s power independently.
         */

        double frontLeftPower = forward + strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backLeftPower = forward - strafe + rotate;
        double backRightPower = forward + strafe - rotate;

        /*
        It then finds the largest absolute motor power using Math.max() and Math.abs(). If any power
        exceeds 1.0 or falls below -1.0, it divides all four values by the largest absolute value to
        keep them within the valid range of -1.0 to 1.0, while preserving their proportions.
        */

        double max;

        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        /*
        Finally, setPower() sends the calculated power values to the four motors, making the robot
        move according to the controller inputs.
         */

        frontLeftMotor.setPower(multiplier * frontLeftPower);
        backLeftMotor.setPower(multiplier * backLeftPower);
        frontRightMotor.setPower(multiplier * frontRightPower);
        backRightMotor.setPower(multiplier * backRightPower);
    }

    public void driveFieldRelative(double forward, double strafe, double rotate, double multiplier) {
        // Converts cartesian coordinates to polar coordinates

        double theta = Math.atan2(forward, strafe);
        double distance = Math.hypot(strafe, forward);

        // Rotates the angle (theta) so we get the angle the robot is actually pointing in

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        // Converts polar coordinates back into cartesian coordinates

        double newForward = distance * Math.sin(theta);
        double newStrafe = distance * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate, multiplier);
    }
}