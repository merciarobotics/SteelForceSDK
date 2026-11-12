package org.firstinspires.ftc.teamcode.mechanisms.FlyWheelAdvanced;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "kSTuner", group = "TeleOP")
public class kSTuner extends OpMode {

    FlyWheelMotor flyWheelMotor = new FlyWheelMotor();

    public static double kS = 0;

    double[] incriments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrimentIndex = 4; // starts at 0.01

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

        double currentStep = incriments[incrimentIndex];

        if (gamepad1.dpadUpWasPressed()) { kS += currentStep; }
        if (gamepad1.dpadDownWasPressed()) { kS -= currentStep; }

        flyWheelMotor.setFlyWheelMotor(kS);

        telemetry.addData("Step","%.6", currentStep);
        telemetry.addData("kS", "%.6", kS);
        telemetry.addData("RPM", flyWheelMotor.getRPM());
        telemetry.addData("Ticks Per Second", flyWheelMotor.getTicksPerSec());



    }
}
