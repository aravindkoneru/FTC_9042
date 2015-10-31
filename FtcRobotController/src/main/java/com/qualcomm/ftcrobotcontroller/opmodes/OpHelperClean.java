package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.BuildConfig;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by aravindkoneru on 10/28/15.
 */
public class OpHelperClean extends OpMode {

    DcMotor frontLeft,
            backLeft;

    DcMotor frontRight,
            backRight;

    //currently placeholders, was told they would be needed
    DcMotor armMotor1,
            armMotor2;

    Servo zipLiner;

    DcMotorController leftController,
                      rightController,
                      armController;//currently a placeholder, was told it would be needed

    //are these needed?
    private int rightTarget,
                leftTarget;

    //SERVO CONSTANTS
    private final double SERVO_MAX=1,
                        SERVO_MIN=-1,
                        SERVO_NEUTRAL = 9.0/17;      //Stops the continuous servo

    //MOTOR RANGES
    private final double MOTOR_MAX=1,
                        MOTOR_MIN=-1;

    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double CIRCUMFERENCE_INCHES = 4*Math.PI,
                        TICKS_PER_ROTATION = 1200/1.06,
                        TICKS_PER_INCH = TICKS_PER_ROTATION/CIRCUMFERENCE_INCHES,
                        TOLERANCE = 10;


    public OpHelperClean(){

    }

    public void init(){
        leftController = hardwareMap.dcMotorController.get("leftController");
        frontLeft = hardwareMap.dcMotor.get("l1");
        backLeft = hardwareMap.dcMotor.get("l2");

        rightController = hardwareMap.dcMotorController.get("rightController");
        frontRight = hardwareMap.dcMotor.get("r1");
        backRight = hardwareMap.dcMotor.get("r2");

        setDirection(); //ensures the proper motor directions

        resetEncoders(); //ensures that the encoders have reset
    }

    //sets the proper direction for the motors
    public void setDirection(){
        if(frontLeft.getDirection() == DcMotor.Direction.FORWARD){
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
        }
        if(backLeft.getDirection() == DcMotor.Direction.FORWARD){
            backLeft.setDirection(DcMotor.Direction.REVERSE);
        }

        if(frontRight.getDirection() == DcMotor.Direction.REVERSE){
            frontRight.setDirection(DcMotor.Direction.FORWARD);
        }

        if(backRight.getDirection() == DcMotor.Direction.REVERSE){
            backRight.setDirection(DcMotor.Direction.FORWARD);
        }
    }

    public void resetEncoders(){
        frontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    //TODO: Implement cheesy drive or special drive code?
    public void setPower(double leftPower, double rightPower){//only accepts clipped values
        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }

    public void setToEncoderMode(){
        frontLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    public void setToWOEncoderMode()
    {
        frontLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public boolean runStraight(double distance_in_inches) {              //Sets values for driving straight, and indicates completion
        leftTarget = (int)(distance_in_inches*TICKS_PER_INCH);
        rightTarget = leftTarget;
        setTargetValueMotor(leftTarget, rightTarget);

        setPower(.4, .4);//TODO: Stalling factor that Libby brought up; check for adequate power

        if(checkRunStatus())
        {
            setPower(0,0);
            return true;//done traveling
        }
        return false;
    }

    public void setTargetValueMotor(int leftTarget, int rightTarget){
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    public boolean checkRunStatus()
    {
        return (Math.abs(frontLeft.getCurrentPosition()-leftTarget)<=TOLERANCE &&
                Math.abs(backLeft.getCurrentPosition()-leftTarget)<=TOLERANCE &&
                Math.abs(frontRight.getCurrentPosition()-rightTarget)<=TOLERANCE &&
                Math.abs(backRight.getCurrentPosition()-rightTarget)<=TOLERANCE);
    }

    //TODO: Run tests to determine the relationship between degrees turned and ticks
    public boolean setTargetValueTurn(double degrees){
        return false;
    }

    public void basicTel(){
        telemetry.addData("frontLeftPos: ", frontLeft.getCurrentPosition());
        telemetry.addData("backLeftPos: ", backLeft.getCurrentPosition());
        telemetry.addData("LeftTargetTarg: ", leftTarget);

        telemetry.addData("frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("backRightPos: ", backRight.getCurrentPosition());
        telemetry.addData("RightTargetTarg: ", rightTarget);
    }


    public boolean setServo(double pos){//only accepts a clipped value
        zipLiner.setPosition(pos);
        return true;
    }

    public void manualDrive(){
        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }


    public void loop(){

    }

    public void stop(){

    }

}
