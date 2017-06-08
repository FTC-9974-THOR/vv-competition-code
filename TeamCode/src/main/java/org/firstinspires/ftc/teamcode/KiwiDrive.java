package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.Arrays;

/**
 * Created by FTC on 6/6/2017.
 */
public class KiwiDrive {

    DcMotor wheel1, wheel2, wheel3;
    double w1Vector, w2Vector, w3Vector, w1Norm, w2Norm, w3Norm;

    public boolean debug = false;

    public KiwiDrive(HardwareMap hw) {
        wheel1 = hw.dcMotor.get("W1");
        wheel2 = hw.dcMotor.get("W2");
        wheel3 = hw.dcMotor.get("W3");
    }

    public void move(double x, double y, double rot) {
        w1Vector = (Math.cos((2.0 *  Math.PI) / 3.0) * x) + (Math.sin((2.0 * Math.PI) / 3.0) * y) + rot;
        w2Vector = (Math.cos((4.0 * Math.PI) / 3.0) * x) + (Math.sin((4.0 * Math.PI) / 3.0) * y) + rot;
        w3Vector = (Math.cos(0) * x) + (Math.sin(0) * y) + rot;

        if (debug) {
            double[] vectors = new double[]{Math.abs(w1Vector), Math.abs(w2Vector), Math.abs(w3Vector)};
            Arrays.sort(vectors);

            w1Norm = Range.scale(w1Vector, -vectors[vectors.length - 1], vectors[vectors.length - 1], -1, 1);
            RobotLog.d(Boolean.toString(w1Norm == ((vectors[vectors.length - 1] == 0) ? 0 : (w1Vector / vectors[vectors.length - 1]))));
            w2Norm = Range.scale(w2Vector, -vectors[vectors.length - 1], vectors[vectors.length - 1], -1, 1);
            w3Norm = Range.scale(w3Vector, -vectors[vectors.length - 1], vectors[vectors.length - 1], -1, 1);

            wheel1.setPower(w1Norm);
            wheel2.setPower(w2Norm);
            wheel3.setPower(w3Norm);
        } else {
            wheel1.setPower(w1Vector);
            wheel2.setPower(w2Vector);
            wheel3.setPower(w3Vector);
        }
    }
}
