package org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp (name = "kVTuner", group = "TeleOP")
public class kVTuner extends OpMode {
    PollenMotor pollenMotor = new PollenMotor();

    public static double kS =  0.0;
    public double kV = 0.0;
    double[] incriments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrimentIndex = 2; // starts at 0.0001

    public double goalRPM = 1000;

    @Override
    public void init() {
        pollenMotor.initPIDF(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpadRightWasPressed() && incrimentIndex < 4 ) {
            incrimentIndex++;
        } else if (gamepad1.dpadLeftWasPressed() && incrimentIndex > 0) {
            incrimentIndex--;
        }

        double currentStep = incriments[incrimentIndex];

        if (gamepad1.dpadUpWasPressed()) { kV += currentStep; }
        if (gamepad1.dpadDownWasPressed()) { kV -= currentStep; }

        double power = (kV * goalRPM) + kS;
        pollenMotor.setPollenMotor(power);

        telemetry.addData("Step","%.6", currentStep);
        telemetry.addData("kV", "%.6", kV);
        telemetry.addData("RPM", pollenMotor.getRPM());
        telemetry.addData("Ticks Per Second", pollenMotor.getTicksPerSec());




    }
}
