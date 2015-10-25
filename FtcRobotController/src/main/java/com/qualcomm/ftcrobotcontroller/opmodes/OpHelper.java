package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Tim on 10/24/2015.
 */
public class OpHelper extends OpMode {
    //Components:
    private DcMotor left1, left2, right1, right2;
    private Servo servo1;

    private int leftTarget, rightTarget;
    //Constants
    public final double
            SERVO_MAX=1,
            SERVO_MIN=-1,
            SERVO_NEUTRAL = 9.0/17,      //Stops the continuous servo
            MOTOR_MAX=1,
            MOTOR_MIN=-1;

    public final double                     //Constants for running using encoders
            CIRCUMFERENCE_INCHES = 4*Math.PI,
            TICKS_PER_ROTATION = 1200/1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION/CIRCUMFERENCE_INCHES,
            TOLERANCE = 100;
    //
    public OpHelper(){}
    @Override
    public void init() {
        if(left1.getDirection() == DcMotor.Direction.FORWARD)
            left1.setDirection(DcMotor.Direction.REVERSE);
        if(left2.getDirection() == DcMotor.Direction.FORWARD)
            left2.setDirection(DcMotor.Direction.REVERSE);


        left1 = hardwareMap.dcMotor.get("l1");
        left2 = hardwareMap.dcMotor.get("l2");
        right1 = hardwareMap.dcMotor.get("r1");
        right2 = hardwareMap.dcMotor.get("r2");
        servo1 = hardwareMap.servo.get("s1");

        resetEncoders();
        setToEncoderMode();
    }

    @Override
    public void loop(){
    }

    @Override
    public void stop(){
    }

    public boolean setPower(double left, double right)      //Sets power, and checks values
    {
        double leftPower = clipValues(left, ComponentType.MOTOR);
        double rightPower = clipValues(right, ComponentType.MOTOR);

        left1.setPower(leftPower);
        left2.setPower(rightPower);

        right1.setPower(rightPower);
        right2.setPower(rightPower);

        return true;
    }

    public boolean setServo(double position)
    {
        double pos = clipValues(position, ComponentType.SERVO);
        servo1.setPosition(pos);

        return true;
    }

    enum ComponentType{         //helps with clipValues
        NONE,
        MOTOR,
        SERVO
    }

    public double clipValues(double initialValue, ComponentType type)
    {
        if (type == ComponentType.MOTOR)
            return Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        else if (type == ComponentType.SERVO)
            return Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return -2;
    }

    public boolean resetEncoders()
    {
        left1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        left2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        return true;
    }

    public boolean setToEncoderMode()
    {
        resetEncoders();
        left1.setTargetPosition(0);
        left2.setTargetPosition(0);
        right1.setTargetPosition(0);
        right2.setTargetPosition(0);

        left1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        left2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        return true;
    }

    public boolean setTargetValue(double distance_in_inches) {              //Sets values for driving straight
        leftTarget = (int)(distance_in_inches*TICKS_PER_INCH);
        rightTarget=leftTarget;
        setTargetValueMotor(leftTarget, rightTarget);
        return true;
    }

    public boolean checkRunStatus()         //TODO Implement the correct sign for the ticks
    {
        if(Math.abs(left1.getCurrentPosition()-leftTarget)<=TOLERANCE &&
                Math.abs(left2.getCurrentPosition()-leftTarget)<=TOLERANCE &&
                Math.abs(right1.getCurrentPosition()-rightTarget)<=TOLERANCE &&
                Math.abs(right2.getCurrentPosition()-rightTarget)<=TOLERANCE)
            return true;
        return false;
    }

    private void setTargetValueMotor(int left, int right)        //Individual target position setting, do not use manually.
    {
        left1.setTargetPosition(left);
        left2.setTargetPosition(left);
        right1.setTargetPosition(right);
        right2.setTargetPosition(right);
    }


    public void telemetry(){
        telemetry.addData("Left1", left1.getCurrentPosition());
        telemetry.addData("Left2", left2.getCurrentPosition());
        telemetry.addData("LeftTarget", leftTarget);
        telemetry.addData("Right1", right1.getCurrentPosition());
        telemetry.addData("Right2", right2.getCurrentPosition());
        telemetry.addData("RightTarget", rightTarget);
    }
}
