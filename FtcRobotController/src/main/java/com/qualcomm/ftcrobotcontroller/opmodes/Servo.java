package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by NamanSarda on 10/11/15.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class Servo extends OpMode {

    Servo Servo1;

    /**
     * Constructor
     */

    public Servo() {

    }

    @Override
    public void init() {



    }

    @Override
    public void loop() {



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
