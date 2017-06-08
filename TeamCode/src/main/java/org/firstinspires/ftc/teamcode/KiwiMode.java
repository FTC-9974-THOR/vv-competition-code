package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by FTC on 6/6/2017.
 */
public class KiwiMode extends OpMode {

    KiwiDrive kd;

    @Override
    public void init() {
        kd = new KiwiDrive(hardwareMap);
    }

    @Override
    public void loop() {
        kd.debug = gamepad1.left_bumper;
        kd.move(-gamepad1.right_stick_x, -gamepad1.right_stick_y, -gamepad1.left_stick_x);
    }
}
