package org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "kPTuner", group = "TeleOP")
public class kPTuner extends OpMode {

    FlyWheelMotor flyWheelMotor = new FlyWheelMotor();

    public static double kS =  0.0;
    public double kV = 0.0;
    public double kP = 0.0;

    double[] incriments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrimentIndex = 2; // starts at 0.0001

    public double goalRPM = 1000;

    @Override
    public void init() {
        flyWheelMotor.initPIDF(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpadRightWasPressed() && incrimentIndex < 4 ) {
            incrimentIndex++;
        } else if (gamepad1.dpadLeftWasPressed() && incrimentIndex > 0) {
            incrimentIndex--;
        }

        // likely may need to make it change for if balls are inside

        double currentStep = incriments[incrimentIndex];

        if (gamepad1.dpadUpWasPressed()) { kV += currentStep; }
        if (gamepad1.dpadDownWasPressed()) { kV -= currentStep; }

        double feedForward = (kV * goalRPM) + kS;
        double error = goalRPM - flyWheelMotor.getRPM();
        double feedBack = error * kP;

        flyWheelMotor.setFlyWheelMotor(feedForward + feedBack);

        telemetry.addData("Step","%.6", currentStep);
        telemetry.addData("kP", "%.6", kP);
        telemetry.addData("error", error);
        telemetry.addData("RPM", flyWheelMotor.getRPM());




    }
}
