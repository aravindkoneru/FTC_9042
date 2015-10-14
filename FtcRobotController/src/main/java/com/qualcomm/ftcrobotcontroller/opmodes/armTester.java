package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by aravindkoneru on 10/4/15.
 */

//unstable code that has a double tap issue, but works by driving
//the arm using encoders.
public class armTester extends OpMode{

    public armTester(){

    }

    DcMotor leftMotor,
            rightMotor,
            arm;
    int pos = 0;
    final int inc = 10;

    public void init(){
        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");
        arm = hardwareMap.dcMotor.get("arm");

        leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void loop(){
        leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        float armPower = gamepad2.left_stick_y;

        float extend = gamepad2.left_trigger - gamepad2.right_trigger;

        arm.setPower(armPower);

        if(extend > 0){
            pos+=inc;
            rightMotor.setTargetPosition(inc);
            leftMotor.setTargetPosition(inc);
            rightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            leftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);


        } else if(extend < 0){
            pos-=inc;
            rightMotor.setTargetPosition(-inc);
            leftMotor.setTargetPosition(-inc);
            rightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            leftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        }

        rightMotor.setPower(0.3);
        leftMotor.setPower(0.3);
    }

}