package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by aravindkoneru on 10/4/15.
 */
public class armTesterManual extends OpMode{

    public armTesterManual(){

    }

    DcMotor leftMotor,
            rightMotor,
            arm;

    final int inc = 10;

    public void init(){
        leftMotor = hardwareMap.dcMotor.get("m1");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor = hardwareMap.dcMotor.get("m2");
        arm = hardwareMap.dcMotor.get("arm");
    }

    public void loop(){
        float armPower = gamepad2.left_stick_y;

        float extend = gamepad2.left_trigger - gamepad2.right_trigger;

        arm.setPower(armPower);

        if(extend > 0){
            rightMotor.setPower(0.2);
            leftMotor.setPower(0.2);
        } else if(extend < 0){
            rightMotor.setPower(- 0.2);
            leftMotor.setPower(- 0.2);
        } else
        {
            rightMotor.setPower(0);
            leftMotor.setPower(0);
        }
    }

}
