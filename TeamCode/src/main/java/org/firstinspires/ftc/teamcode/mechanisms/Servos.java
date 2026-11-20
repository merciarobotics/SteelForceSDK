package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Servos {
    private CRServo upperServo;
    private CRServo lowerServo;

    public void init(HardwareMap hwMap) {

        upperServo = hwMap.get(CRServo.class, "Upper_Servo");
        lowerServo = hwMap.get(CRServo.class, "Lower_Servo");

    }

    public void setUpperServo(double power) {
        upperServo.setPower(power);
    }

    public void setLowerServo(double power) {
        lowerServo.setPower(power);
    }

    public void stopServos() {
        upperServo.setPower(0);
        lowerServo.setPower(0);
    }

}
