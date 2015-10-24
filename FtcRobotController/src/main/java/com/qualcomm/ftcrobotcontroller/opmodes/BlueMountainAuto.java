package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by NamanSarda on 10/11/15.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class BlueMountainAuto extends OpMode {

    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorLeft1;
    DcMotor motorLeft2;
    int[] left = new int[5];
    int[] right = new int[5];
    int h=1;

    /**
     * Constructor
     */

    public BlueMountainAuto() {

    }

    @Override
    public void init() {

        motorRight1 = hardwareMap.dcMotor.get("r1");
        motorRight2 = hardwareMap.dcMotor.get("r2");
        motorLeft1 = hardwareMap.dcMotor.get("l1");
        motorLeft2 = hardwareMap.dcMotor.get("l2");
        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        left[1]=4000+2220;
        left[2]=5600+2220;
        left[3]=10200+2220;
        left[4]=0;
        right[1]=4000+2220;
        right[2]=2400+2220;
        right[3]=7000+2220;
        right[4]=0;


    }

    @Override
    public void loop() {
        int j;
        int k;
        int w = motorLeft1.getCurrentPosition();
        int x = motorLeft2.getCurrentPosition();
        int y = motorRight1.getCurrentPosition();
        int z = motorRight2.getCurrentPosition();

//        int LeftSidePosition=((x+w)/2);
//        int RightSidePosition=((y+z)/2);
        // motor right encoder values are negative
        int rightTarget = right[h];
        int leftTarget = left[h];
        if (leftTarget!=0 || (rightTarget!=0 )) {
            j=movement(left, h, leftTarget, x, motorLeft1, motorLeft2);
            k= movement(right, h, rightTarget, y, motorRight1, motorRight2);
            if (k>j){
                h=k;
            }
            if (j>k){
                h=j;
            }
            else{
                h=j;
            }
        }
        else if (leftTarget==0 || rightTarget==0) {
            motorLeft1.setPower(0);
            motorLeft2.setPower(0);
            motorRight1.setPower(0);
            motorRight2.setPower(0);
        }

        telemetry.addData("Right Front Position = ", motorRight1.getCurrentPosition());
        telemetry.addData("Right Back Position = ", motorRight2.getCurrentPosition());
        telemetry.addData("Left Front Position = ", motorLeft1.getCurrentPosition());
        telemetry.addData("Left Back Position = ", motorLeft2.getCurrentPosition());


    }
    public int movement(int[] list, int h, int targetPos, int currentPos, DcMotor motorA, DcMotor motorB) {
        if (list[(h - 1)] > targetPos) {
            if (currentPos > targetPos) {
                motorA.setPower(-.4);
                motorB.setPower(-.4);
            } else if (currentPos <= targetPos) {
                motorA.setPower(0);
                motorB.setPower(0);
                h++;
            }
        }
        else if (list[(h - 1)] < targetPos) {
            if (currentPos < targetPos) {
                motorA.setPower(.4);
                motorB.setPower(.4);
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