package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by aravindkoneru on 10/4/15.
 */
public class armDist extends OpMode{

    public armDist(){

    }

    DcMotor leftMotor,
            rightMotor,
            arm;

    public void init(){
        leftMotor = hardwareMap.dcMotor.get("m1");
        rightMotor = hardwareMap.dcMotor.get("m2");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        arm = hardwareMap.dcMotor.get("arm");

        leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void loop(){
        if(gamepad2.right_bumper==true) {
            leftMotor.setTargetPosition(getCounts(3));
            rightMotor.setTargetPosition(getCounts(3));
        } else if(gamepad2.left_bumper==true) {
            leftMotor.setTargetPosition(-getCounts(3));
            rightMotor.setTargetPosition(-getCounts(3));
        }
        else{
            leftMotor.setTargetPosition(0);
            rightMotor.setTargetPosition(0);
        }
        leftMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        rightMotor.setPower(0.3);
        leftMotor.setPower(0.3);
    }

    public int getCounts(int distance)
    {
        double counts = distance/(4*Math.PI)*1440;
        return (int) counts;
    }
}
