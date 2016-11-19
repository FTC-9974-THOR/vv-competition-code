package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.robot.RobotStatus;
import com.qualcomm.robotcore.util.BatteryChecker;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by FTC on 10/23/2016.
 *
 * Competition program
 */

@TeleOp(name="Competition (Error handling)", group="2017")

@SuppressWarnings("unused")

public class CompetitionVelocityErrorHandling extends OpMode {

    RobotDrive rb;

    //PID leftPid;
    //PID rightPid;

    DcMotor intake;
    DcMotor leftLauncher;
    DcMotor rightLauncher;

    DcMotor leftDrive;
    DcMotor rightDrive;

    //Servo buttonPresser;



    boolean isInRB;
    @Override
    protected void preInit() {
        super.preInit();

        Iterator<HardwareDevice> devices = hardwareMap.iterator();

        /*if (!devices.hasNext()) {
            RobotLog.setGlobalWarningMessage("TURN ON THE ROBOT!");
        }*/
    }

    @Override
    public void init() {
        //leftPid = new PID(1, 1, 1, 0, 330, false, 0, 1000, 0, 1000);

        RobotLog.setGlobalWarningMessage("TURN ON THE ROBOT!");
        try {
            rb = new RobotDrive(RobotDrive.TWO_WHEEL, hardwareMap);
            isInRB = true;
        } catch (Exception e) {
            isInRB = false;
            telemetry.addData("Error on drive motor mapping", e.getMessage());
            remapDrive(hardwareMap.dcMotor.entrySet());
        }
        intake = hardwareMap.dcMotor.get("intake");
        leftLauncher = hardwareMap.dcMotor.get("LL");
        rightLauncher = hardwareMap.dcMotor.get("RL");
        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //buttonPresser = hardwareMap.servo.get("servo");
    }

    @Override
    public void loop() {
        //if (!gamepad2.right_bumper) {   // Disable drive train so the firing motors spin faster
        if (isInRB) {
            rb.tankUpdate(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        } else {
            rightDrive.setPower(Range.clip(-gamepad1.right_stick_y, -1, 1));
            leftDrive.setPower(Range.clip(-gamepad1.left_stick_y, -1, 1));
        }
        //}

        if (gamepad2.b) {
            intake.setPower(1);
        } else if (gamepad2.a) {    // Because the motor can't go both directions at once
            intake.setPower(-1);
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
            leftLauncher.setPower(-1);
            rightLauncher.setPower(1);
        } else {
            leftLauncher.setPower(0);
            rightLauncher.setPower(0);
        }

        telemetry.addLine("Launcher data");
        telemetry.addData("Left RPM", leftLauncher.getPower()*330);     // The output shaft should
        telemetry.addData("Right RPM", rightLauncher.getPower()*330);   // be turning at roughly
                                                                        // 330 times it's input power
        telemetry.addLine("Encoder values");
        telemetry.addData("Left Drive", rb.frontLeft.getCurrentPosition());
        telemetry.addData("Right Drive", rb.frontRight.getCurrentPosition());
        telemetry.addData("Intake", intake.getCurrentPosition());
        /*
        telemetry.addLine("Servo data");
        telemetry.addData("Position", buttonPresser.getPosition());
        */

    }

    String likelyLeft[] = {
            "Left",
            "left",
            "l",
            "L",
            "LEFT"
    };

    String likelyRight[] = {
            "Right",
            "right",
            "r",
            "R",
            "RIGHT"
    };

    private void remapDrive(Set<Map.Entry<String, DcMotor>> entries) {
        //if (entries.isEmpty())
        /*Set<Map.Entry<String, DcMotor>> _entries = entries;
        Map.Entry<String, DcMotor>[] entryArray = (Map.Entry<String, DcMotor>[]) _entries.toArray();
        for (Map.Entry<String, DcMotor> stringDcMotorEntry : entryArray) {
            if (stringDcMotorEntry.getKey().contains("drive") || stringDcMotorEntry.getKey().contains("Drive") || stringDcMotorEntry.getKey().contains("DRIVE")) {
                // Likely drive motor
                for (String entry : likelyLeft) {
                    if (stringDcMotorEntry.getKey().contains(entry)) {
                        // Likely left drive motor found
                        leftDrive = stringDcMotorEntry.getValue();
                        telemetry.addData("Likely Left Drive found; assigning", entry);
                    }
                }

                for (String entry : likelyRight) {
                    if (stringDcMotorEntry.getKey().contains(entry)) {
                        // Likely left drive motor found
                        rightDrive = stringDcMotorEntry.getValue();
                        telemetry.addData("Likely Right Drive found; assigning", entry);
                    }
                }
            }
        } */
        //if ()
    }
}
