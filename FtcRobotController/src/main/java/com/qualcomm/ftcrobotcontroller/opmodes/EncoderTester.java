package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;


public class EncoderTester extends OpMode {

    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorLeft1;
    DcMotor motorLeft2;

    public EncoderTester() {
    }

    @Override
    public void init() {

        motorRight1 = hardwareMap.dcMotor.get("r1");
        motorRight2 = hardwareMap.dcMotor.get("r2");
        motorLeft1 = hardwareMap.dcMotor.get("l1");
        motorLeft2 = hardwareMap.dcMotor.get("l2");
        motorLeft1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);


    }

    @Override
    public void loop() {
        telemetry.addData("Right Front Position = ", motorRight1.getCurrentPosition());
        telemetry.addData("Right Back Position = ", motorRight2.getCurrentPosition());
        telemetry.addData("Left Front Position = ", motorLeft1.getCurrentPosition());
        telemetry.addData("Left Back Position = ", motorLeft2.getCurrentPosition());

    }
}