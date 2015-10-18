package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by NamanSarda on 10/11/15.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class Auto extends OpMode {

    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorLeft1;
    DcMotor motorLeft2;
    int[] left = new int[5];
    int[] right = new int[5];
    int h=1;
    int i=1;

    /**
     * Constructor
     */

    public Auto() {

    }

    @Override
    public void init() {

        motorRight1 = hardwareMap.dcMotor.get("r1");
        motorRight2 = hardwareMap.dcMotor.get("r2");
        motorLeft1 = hardwareMap.dcMotor.get("l1");
        motorLeft2 = hardwareMap.dcMotor.get("l2");
        left[1]=9000;
        left[2]=7000;
        left[3]=7800;
        left[4]=0;
        right[1]=9000;
        right[2]=11000;
        right[3]=11800;
        right[4]=0;


    }

    @Override
    public void loop() {
        int x = -motorLeft1.getCurrentPosition();
        int y = motorRight1.getCurrentPosition();
        // motor right encoder values are negative
        int rightTarget = right[i];
        int leftTarget = left[h];
        if (left[h]!=0 && (right[i]!=0 )) {
            h = movement(left, h, leftTarget, x, motorLeft1, motorLeft2);
            i = movement(right, i, rightTarget, y, motorRight1, motorRight2);
        }
        else if (left[h]==0 || right[i]==0) {
            motorLeft1.setPower(0);
        }

        telemetry.addData("Right Motor Position = ", y);
        telemetry.addData("Left Motor Position = ", x);


    }
    public int movement(int[] list, int h, int targetPos, int currentPos, DcMotor motorA, DcMotor motorB) {
        if (list[(h - 1)] > targetPos) {
            if (currentPos > targetPos) {
                motorA.setPower(-.5);
                motorB.setPower(-.5);
            } else if (currentPos <= targetPos) {
                motorA.setPower(0);
                motorB.setPower(0);
                h++;
            }
        }
        else if (list[(h - 1)] < targetPos) {
            if (currentPos < targetPos) {
                motorA.setPower(.5);
                motorB.setPower(.5);
            } else if (currentPos >= targetPos) {
                motorA.setPower(0);
                motorB.setPower(0);
                h++;


            }
        }
        return h;
    }



    @Override
    public void stop() {

    }
}