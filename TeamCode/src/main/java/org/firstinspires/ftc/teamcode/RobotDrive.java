package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by FTC on 10/19/2016.
 *
 * An instantiatable object that is used for easy configuration of
 * the robot.
 */
@SuppressWarnings("unused")
public class RobotDrive {
    static public final int FOUR_WHEEL = 0;     // 4 wheel drive
    static public final int TWO_WHEEL = 1;      // Ranger bot

    private int mode;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    /**
     * Returns a new RobotDrive object. Object oriented programming for the win!
     * @param configNumber determines what robot config to use
     * @param hardwareMap pass OpMode's hardwareMap so this class can use it
     */
    // TODO add custom configs
    public RobotDrive(int configNumber, HardwareMap hardwareMap) throws IllegalArgumentException {
        switch (configNumber) {
            case FOUR_WHEEL:
                mode = 0;
                frontLeft = hardwareMap.dcMotor.get("FL");
                frontRight = hardwareMap.dcMotor.get("FR");
                backLeft = hardwareMap.dcMotor.get("BL");
                backRight = hardwareMap.dcMotor.get("BR");
                frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                backRight.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case TWO_WHEEL:
                mode = 1;
                frontLeft = hardwareMap.dcMotor.get("FL");
                frontRight = hardwareMap.dcMotor.get("FR");
                frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                // Because we don't use them
                // TODO find something better, like maybe some type of destroy()
                backLeft = null;
                backRight = null;
                break;
            default:
                throw new IllegalArgumentException("Error! Use a valid config");
        }
    }

    /**
     * Get which mode the object is in
     * @return mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Built in tank drive
     * @param left left input
     * @param right right input
     */
    public void tankUpdate(double left, double right) {
        switch (mode) {
            case FOUR_WHEEL:
                frontLeft.setPower(left);
                backLeft.setPower(left);
                frontRight.setPower(right);
                backRight.setPower(right);
                break;
            case TWO_WHEEL:
                frontLeft.setPower(left);
                frontRight.setPower(right);
                break;
            default:
                break;
        }
    }
}
