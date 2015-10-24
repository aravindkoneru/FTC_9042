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

    //Constants
    public final short
            LEFT1Channel = 1,
            LEFT2Channel = 2,
            RIGHT1Channel = 1,
            RIGHT2Channel = 2,
            SERVO1Channel = 1;
    public final double
            SERVO_MAX=1,
            SERVO_MIN=-1,
            SERVO_NEUTRAL = 9.0/17,      //Stops the continuous servo
            MOTOR_MAX=1,
            MOTOR_MIN=-1;
    //
    public OpHelper(){}
    @Override
    public void init() {
        motorController1 = hardwareMap.dcMotorController.get("mc1");
        motorController2 = hardwareMap.dcMotorController.get("mc2");
        left1 = hardwareMap.dcMotor.get("l1");
        left2 = hardwareMap.dcMotor.get("l2");
        right1 = hardwareMap.dcMotor.get("r1");
        right2 = hardwareMap.dcMotor.get("r2");
        servoController1 = hardwareMap.servoController.get("sc1");
        servo1 = hardwareMap.servo.get("s1");
    }

    @Override
    public void loop(){
    }

    @Override
    public void stop(){

    }

    public boolean setPower(double left, double right)
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
}
