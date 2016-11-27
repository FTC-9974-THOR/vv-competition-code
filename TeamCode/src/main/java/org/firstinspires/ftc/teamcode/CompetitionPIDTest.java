package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbDcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.DifferentialControlLoopCoefficients;
//import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by FTC on 10/23/2016.
 *
 * Competition program
 */

@TeleOp(name="Competition PID Test", group="2017")
@Disabled
@SuppressWarnings("unused")

public class CompetitionPIDTest extends OpMode {

    RobotDrive rb;

    //PID leftPid;
    //PID rightPid;

    DcMotor intake;
    DcMotor leftLauncher;
    DcMotor rightLauncher;

    ModernRoboticsUsbDcMotorController launcherControl;
    DifferentialControlLoopCoefficients pid[];

    //Servo buttonPresser;

    private int ticksPerSpeed(double ratio) {
        return ((int) ratio * 29);  // returns ticks per rev for a certain speed
    }

    @Override
    public void init() {
        //leftPid = new PID(1, 1, 1, 0, 330, false, 0, 1000, 0, 1000);

        rb = new RobotDrive(RobotDrive.TWO_WHEEL, hardwareMap);

        intake = hardwareMap.dcMotor.get("intake");
        leftLauncher = hardwareMap.dcMotor.get("LL");
        rightLauncher = hardwareMap.dcMotor.get("RL");
        launcherControl = (ModernRoboticsUsbDcMotorController) hardwareMap.dcMotorController.get("launch control");
        leftLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLauncher.setMaxSpeed(3065); // This corresponds to 1784 rpm. 29.7 ticks per rev
        rightLauncher.setMaxSpeed(3065);
        leftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //buttonPresser = hardwareMap.servo.get("servo");
    }

    @Override
    public void loop() {
        //if (!gamepad2.right_bumper) {   // Disable drive train so the firing motors spin faster
        rb.tankUpdate(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        //}

        if (gamepad2.b) {
            intake.setPower(0.7);
        } else if (gamepad2.a) {    // Because the motor can't go both directions at once
            intake.setPower(-1);
        } else if (gamepad2.left_trigger > 0){
            intake.setPower(gamepad2.left_trigger);
        } else {
            intake.setPower(0);
        }
        /*
        if (gamepad1.left_bumper) {
            buttonPresser.setPosition(90);
        } else {
            buttonPresser.setPosition(0);
        }
        */
        if (gamepad2.right_bumper) {
            leftLauncher.setPower(0.02);
            rightLauncher.setPower(-0.02);
        } else if(gamepad2.left_bumper) {
            leftLauncher.setPower(0.05);
            rightLauncher.setPower(-0.05);
        } else {
            leftLauncher.setPower(0);
            rightLauncher.setPower(0);
        }

        telemetry.addLine("Launcher data");
        telemetry.addData("Left RPM", leftLauncher.getPower()*330);     // The output shaft should
        telemetry.addData("Right RPM", rightLauncher.getPower()*330);   // be turning at roughly
                                                                        // 330 times it's input power
        pid[0] = launcherControl.getDifferentialControlLoopCoefficients(1);
        pid[1] = launcherControl.getDifferentialControlLoopCoefficients(2);

        telemetry.addLine("PID stuff");
        telemetry.addData("Motor 1 P", pid[0].p);
        telemetry.addData("Motor 1 I", pid[0].i);
        telemetry.addData("Motor 1 D", pid[0].d);
        telemetry.addData("Motor 2 P", pid[1].p);
        telemetry.addData("Motor 2 I", pid[1].i);
        telemetry.addData("Motor 2 D", pid[1].d);

        telemetry.addLine("Encoder values");
        telemetry.addData("Left Drive", rb.frontLeft.getCurrentPosition());
        telemetry.addData("Right Drive", rb.frontRight.getCurrentPosition());
        telemetry.addData("Intake", intake.getCurrentPosition());
        /*
        telemetry.addLine("Servo data");
        telemetry.addData("Position", buttonPresser.getPosition());
        */
    }
}
