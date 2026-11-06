package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "FlyWheelTest", group = "TeleOp")
public class FlyWheelMotor {
    private DcMotor flyWheelMotor;

    public void init(HardwareMap hwMap) {
        flyWheelMotor = hwMap.get(DcMotor.class, "fly_wheel");

        flyWheelMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setFlyWheelMotor(double  power) {
        flyWheelMotor.setPower(power);
    }

    // --------------- PIDF Tunig Logic ------------------------

    double kS, kV, kP;

    public double getRPM() {
        return awtMotorRPM();
    }

    // meed to tune these constants next wek

    public void awtMotorRPM(double targetRPM) {
        // call every loop
        double error = targetRPM - getRPM();
        double ff = (kV * targetRPM) +kS;
        double fb = error * kP;
        double power = ff + fb;

        setFlyWheelMotor(power);
    }

}
