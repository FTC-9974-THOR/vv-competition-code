package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by FTC on 11/13/2015.
 */
public class OvertrollMode extends LinearOpMode {
    DcMotor LeftFrontMotor;
    DcMotor LeftBackMotor;
    DcMotor RightFrontMotor;
    DcMotor RightBackMotor;
    double pos = 0.01;
    double pos2 = 0.99;
    Servo Servo1;
    Servo Servo2;

    @Override
    public void runOpMode() throws InterruptedException {
        LeftFrontMotor = hardwareMap.dcMotor.get("left_front_drive");
        LeftBackMotor = hardwareMap.dcMotor.get("left_back_drive");
        RightFrontMotor = hardwareMap.dcMotor.get("right_front_drive");
        RightBackMotor= hardwareMap.dcMotor.get("right_back_drive");
        LeftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        LeftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        LeftFrontMotor.setChannelMode
                (DcMotorController.RunMode.RESET_ENCODERS
                );
        LeftBackMotor.setChannelMode
                (DcMotorController.RunMode.RESET_ENCODERS
                );
        RightFrontMotor.setChannelMode
                (DcMotorController.RunMode.RESET_ENCODERS
                );
        RightBackMotor.setChannelMode
                (DcMotorController.RunMode.RESET_ENCODERS
                );

        waitForNextHardwareCycle();

        Servo1 = hardwareMap.servo.get("servo1");
        Servo2 = hardwareMap.servo.get("servo2");

        Servo1.setPosition(pos);
        Servo2.setPosition(pos2);

        waitForStart();

         //At start...
        pos += 1;

        pos = Range.clip(pos, 0.01, 0.99);
        Servo1.setPosition(pos);

        sleep(100);

        pos -= 1;

        pos = Range.clip(pos, 0.01, 0.99);
        Servo1.setPosition(pos);
        //sleep(10000);
        sleep(10);
        LeftFrontMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        LeftBackMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        RightFrontMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        RightBackMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        int right_count = 15200;
        LeftFrontMotor.setPower(1);
        LeftBackMotor.setPower(1);
        RightFrontMotor.setPower(1);
        RightBackMotor.setPower(1);
        while ((Math.abs(RightFrontMotor.getCurrentPosition()) < right_count)) {
            telemetry.addData("LF: ", LeftFrontMotor.getCurrentPosition());
            telemetry.addData("LB: ", LeftBackMotor.getCurrentPosition());
            telemetry.addData("RF: ", RightFrontMotor.getCurrentPosition());
            telemetry.addData("RB: ", RightBackMotor.getCurrentPosition());
            telemetry.addData("AVG: ", (LeftFrontMotor.getCurrentPosition() + LeftBackMotor.getCurrentPosition()
                    + RightFrontMotor.getCurrentPosition() + RightBackMotor.getCurrentPosition()) / 4);
            telemetry.addData("LF power = ", LeftFrontMotor.getPower());
            telemetry.addData("RF power = ", RightFrontMotor.getPower());
            telemetry.addData("LB power = ", LeftBackMotor.getPower());
            telemetry.addData("RB power = ", RightBackMotor.getPower());
            telemetry.addData("LF target = ", LeftFrontMotor.getTargetPosition());
            telemetry.addData("RF target = ", RightFrontMotor.getTargetPosition());
            telemetry.addData("LB target = ", LeftBackMotor.getTargetPosition());
            telemetry.addData("RB target = ", RightBackMotor.getTargetPosition());

        }

        LeftFrontMotor.setPower(0);
        LeftBackMotor.setPower(0);
        RightFrontMotor.setPower(0);
        RightBackMotor.setPower(0);
    }
}
