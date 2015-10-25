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
    private DcMotorController motorController1, motorController2;
    private DcMotor left1, left2, right1, right2;
    private ServoController servoController1;
    private Servo servo1;

    private int l1Target, l2Target, r1Target, r2Target;
    //Constants
    public final short
            LEFT1CHANNEL = 1,
            LEFT2CHANNEL = 2,
            RIGHT1CHANNEL = 1,
            RIGHT2CHANNEL = 2,
            SERVO1CHANNEL = 1;
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


        motorController1 = hardwareMap.dcMotorController.get("mc1");        //Sets up everything using hardware map
        motorController2 = hardwareMap.dcMotorController.get("mc2");
        left1 = hardwareMap.dcMotor.get("l1");
        left2 = hardwareMap.dcMotor.get("l2");
        right1 = hardwareMap.dcMotor.get("r1");
        right2 = hardwareMap.dcMotor.get("r2");
        servoController1 = hardwareMap.servoController.get("sc1");
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
        motorController1.setMotorChannelMode(LEFT1CHANNEL, DcMotorController.RunMode.RESET_ENCODERS);
        motorController1.setMotorChannelMode(LEFT2CHANNEL, DcMotorController.RunMode.RESET_ENCODERS);
        motorController2.setMotorChannelMode(RIGHT1CHANNEL, DcMotorController.RunMode.RESET_ENCODERS);
        motorController2.setMotorChannelMode(RIGHT2CHANNEL, DcMotorController.RunMode.RESET_ENCODERS);
        return true;
    }

    public boolean setToEncoderMode()
    {
        resetEncoders();
        left1.setTargetPosition(0);
        left2.setTargetPosition(0);
        right1.setTargetPosition(0);
        right2.setTargetPosition(0);

        motorController1.setMotorChannelMode(LEFT1CHANNEL, DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorController1.setMotorChannelMode(LEFT2CHANNEL, DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorController2.setMotorChannelMode(RIGHT1CHANNEL, DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorController2.setMotorChannelMode(RIGHT2CHANNEL, DcMotorController.RunMode.RUN_USING_ENCODERS);
        return true;
    }

    public boolean setTargetValue(double distance_in_inches) {              //Sets values for driving straight
        l1Target = (int)(distance_in_inches*TICKS_PER_INCH);
        setTargetValueMotor(l1Target, l1Target, l1Target, l1Target);
        return true;
    }

    public boolean checkRunStatus()         //TODO Implement the correct sign for the ticks
    {
        if(Math.abs(left1.getCurrentPosition()-l1Target)<=TOLERANCE && Math.abs(left2.getCurrentPosition()-l2Target)<=TOLERANCE && Math.abs(right1.getCurrentPosition()-r1Target)<=TOLERANCE && Math.abs(right2.getCurrentPosition()-r2Target)<=TOLERANCE)
            return true;
        return false;
    }

    private void setTargetValueMotor(int l1, int l2, int r1, int r2)        //Individual target position setting, do not use manually.
    {
        left1.setTargetPosition(l1);
        left2.setTargetPosition(l2);
        right1.setTargetPosition(r1);
        right2.setTargetPosition(r2);
    }


    public void telemetry(){
        telemetry.addData("Left1", left1.getCurrentPosition());
        telemetry.addData("Left1Target:", l1Target);
        telemetry.addData("Left2", left2.getCurrentPosition());
        telemetry.addData("Left2Target", l2Target);
        telemetry.addData("Right1", right1.getCurrentPosition());
        telemetry.addData("Right1Target", r1Target);
        telemetry.addData("Right2", right2.getCurrentPosition());
        telemetry.addData("Right2Target", r2Target);
    }
}
