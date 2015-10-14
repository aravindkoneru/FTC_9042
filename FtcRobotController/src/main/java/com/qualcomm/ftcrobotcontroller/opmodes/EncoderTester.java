package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by aravindkoneru on 9/20/15.
 */
public class EncoderTester extends OpMode {

    DcMotor motorRight1,
            motorRight2;
    DcMotor motorLeft1,
            motorLeft2;

    public EncoderTester(){

    }

    @Override
    public void init(){
        motorRight1 = hardwareMap.dcMotor.get("r1");
        motorRight2 = hardwareMap.dcMotor.get("r2");

        motorRight1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);


        motorLeft1 = hardwareMap.dcMotor.get("l1");
        motorLeft2 = hardwareMap.dcMotor.get("l2");

        motorLeft1.setDirection(DcMotor.Direction.REVERSE);
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);

        motorLeft1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

    }

    public void positionRight(int position){
        motorRight1.setTargetPosition(position);
        motorRight2.setTargetPosition(position);
    }

    public void positionLeft(int position){
        motorLeft1.setTargetPosition(position);
        motorLeft2.setTargetPosition(position);
    }

    @Override
    public void loop(){

        if(gamepad1.a){
            positionLeft(5000);
            positionRight(5000);
        }

        if(gamepad1.x){
            positionRight(-5000);
            positionLeft(-5000);
        }

        telemetry.addData("RIGHT1 ENCODER: ", motorRight1.getCurrentPosition());
        telemetry.addData("RIGHT2 ENCODER: ", motorRight2.getCurrentPosition());

        telemetry.addData("LEFT1 ENCODER: ", motorLeft1.getCurrentPosition());
        telemetry.addData("LEFT2 ENCODER: ", motorLeft2.getCurrentPosition());
    }

    @Override
    public void stop(){

    }

}
