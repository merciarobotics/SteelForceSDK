package org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp (name = "kPTuner", group = "TeleOP")
public class kPTuner extends OpMode {

    PollenMotor pollenMotor = new PollenMotor();

    public static double kS =  0.0;
    public double kV = 0.0;
    public double kP = 0.0;

    // NAME THE VARIABLES BETTER (after scrimmage, I better see these changed and understandable)

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIndex = 2; // starts at 0.0001

    public double goalRPM = 1000;

    @Override
    public void init() {
        pollenMotor.initPIDF(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpadRightWasPressed() && incrementIndex < 4 ) {
            incrementIndex++;
        } else if (gamepad1.dpadLeftWasPressed() && incrementIndex > 0) {
            incrementIndex--;
        }

        // likely may need to make it change for if balls are inside

        double currentStep = increments[incrementIndex];

        if (gamepad1.dpadUpWasPressed()) { kV += currentStep; }
        if (gamepad1.dpadDownWasPressed()) { kV -= currentStep; }

        double feedForward = (kV * goalRPM) + kS;
        double error = goalRPM - pollenMotor.getRPM();
        double feedBack = error * kP;

        pollenMotor.setPollenMotor(feedForward + feedBack);

        telemetry.addData("Step","%.6", currentStep);
        telemetry.addData("kP", "%.6", kP);
        telemetry.addData("error", error);
        telemetry.addData("RPM", pollenMotor.getRPM());


    }
}
