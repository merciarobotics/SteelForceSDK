package org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class NectarMotor {
    private DcMotor flyWheelMotortwo;

    public void init(HardwareMap hwMap) {
        flyWheelMotortwo = hwMap.get(DcMotor.class, "nectar_shooter");

        flyWheelMotortwo.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setNectarMotor(double  power) {
        flyWheelMotortwo.setPower(power);
    }

    // --------------- PIDF Tuning Logic ------------------------

    // https://www.youtube.com/watch?v=KVPdbWcAyUY

    // we create another init method as PIDF tuning works without encoding
    public void initPIDF(HardwareMap hwMap) {
        flyWheelMotortwo = hwMap.get(DcMotor.class, "fly_wheel");
        flyWheelMotortwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flyWheelMotortwo.setDirection(DcMotor.Direction.FORWARD);
    }

    private double encoderCPM = 28;

    double kS, kV, kP; // need to tune these constants this week

    /*
    kS (constant static) is the amount of voltage needed to initially get past the static friction
    kV (constant voltage) amount of voltage needed to maintain flywheel at desired RPM
    kP (constant proportional) is a proportional constant where the further away from our target RPM brings us closer
     */

    public double getTicksPerSec() {
        // cast into a DcMotorEx class as that class has more methods than the basic DcMotor, if wanted we could just turn it into a base Dc Motor
        return ((DcMotorEx) flyWheelMotortwo).getVelocity();
    }

    public double getRPM() {
        return (getTicksPerSec() / encoderCPM) *60;
    }

    public void setMotorRPM(double targetRPM) { // our target RPM - around 1000
        // call every loop
        double error = targetRPM - getRPM();
        double feedForward = (kV * targetRPM) + kS;
        double feedBack = error * kP;
        double power = feedForward + feedBack;


        setNectarMotor(power);
    }

}
