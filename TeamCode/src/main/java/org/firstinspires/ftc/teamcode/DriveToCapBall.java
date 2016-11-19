package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by FTC on 10/15/2015.
 */
@Autonomous(name="Drive to cap ball", group="2017")
public class DriveToCapBall extends LinearOpMode {
    DcMotor LeftMotor;
    DcMotor RightMotor;

    // How long (in milliseconds) to run the motors
    final int motorTime = 3000;

    @Override
    public void runOpMode() throws InterruptedException {
        LeftMotor = hardwareMap.dcMotor.get("FL");
        RightMotor = hardwareMap.dcMotor.get("FR");
        RightMotor.setDirection(DcMotor.Direction.REVERSE);
        LeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("");

        waitForStart();

        sleep(10000);

        LeftMotor.setPower(-1.0);
        RightMotor.setPower(-1.0);

        sleep(motorTime);

        LeftMotor.setPower(0.0);
        RightMotor.setPower(0.0);



    }


}