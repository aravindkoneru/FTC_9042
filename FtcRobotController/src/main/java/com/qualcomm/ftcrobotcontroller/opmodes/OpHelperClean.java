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


    int flag = 0;

    DcMotor frontLeft,
            backLeft;

    DcMotor frontRight,
            backRight;

    //currently placeholders, was told they would be needed
    DcMotor armMotor1,
            armMotor2,
            armPivot;

    Servo zipLiner;


    private int rightTarget,
            leftTarget;

    //SERVO CONSTANTS
    private final double SERVO_MAX=1,
            SERVO_MIN=0,
            SERVO_NEUTRAL = 9.0/17;      //Stops the continuous servo

    //MOTOR RANGES
    private final double MOTOR_MAX=1,
            MOTOR_MIN=-1;

    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double CIRCUMFERENCE_INCHES = 4*Math.PI,
            TICKS_PER_ROTATION = 1200/1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION/CIRCUMFERENCE_INCHES,
            TOLERANCE = 10;


    public OpHelperClean(){

    }

    public void init(){
        //left drive
        frontLeft = hardwareMap.dcMotor.get("l1");
        backLeft = hardwareMap.dcMotor.get("l2");

        //right drive
        frontRight = hardwareMap.dcMotor.get("r1");
        backRight = hardwareMap.dcMotor.get("r2");

        //pivot motor
        armPivot = hardwareMap.dcMotor.get("arm");

        //tape measure arms
        armMotor1 = hardwareMap.dcMotor.get("tm1");
        armMotor2 = hardwareMap.dcMotor.get("tm2");

        zipLiner = hardwareMap.servo.get("zip");

        setDirection(); //ensures the proper motor directions


        resetEncoders(); //ensures that the encoders have reset
    }

    //sets the proper direction for the motors
    public void setDirection(){
        //config drive motors
        if(frontLeft.getDirection() == DcMotor.Direction.REVERSE){
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }
        if(backLeft.getDirection() == DcMotor.Direction.REVERSE){
            backLeft.setDirection(DcMotor.Direction.FORWARD);
        }

        if(frontRight.getDirection() == DcMotor.Direction.FORWARD){
            frontRight.setDirection(DcMotor.Direction.REVERSE);
        }

        if(backRight.getDirection() == DcMotor.Direction.FORWARD){
            backRight.setDirection(DcMotor.Direction.REVERSE);
        }


        //TODO configure arm motor direction

        //TODO config arm pivot direction
    }

    public void moveTapeMeasure(double power){
        armMotor2.setPower(power);
        armMotor1.setPower(power);
    }

    public boolean resetEncoders() {
        frontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        flag = 1;


        return (frontLeft.getCurrentPosition() == 0 &&
                backLeft.getCurrentPosition() == 0 &&
                frontRight.getCurrentPosition() == 0 &&
                backRight.getCurrentPosition() == 0);

    }

    //TODO: Implement cheesy drive or special drive code?
    public void setMotorPower(double leftPower, double rightPower){//only accepts clipped values
        clipValues(leftPower, ComponentType.MOTOR);
        clipValues(rightPower, ComponentType.MOTOR);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }

    public void setToEncoderMode(){
        flag=0;

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

    public boolean runStraight(double distance_in_inches) {//Sets values for driving straight, and indicates completion
        leftTarget = (int)(distance_in_inches*TICKS_PER_INCH);
        rightTarget = leftTarget;
        setTargetValueMotor();

        setMotorPower(.4, .4);//TODO: Stalling factor that Libby brought up; check for adequate power

        if(hasReached())
        {
            setMotorPower(0,0);
            return true;//done traveling
        }
        return false;
    }

    public void setTargetValueMotor(){
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    public boolean hasReached()
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
        telemetry.addData("LeftTarget: ", leftTarget);

        telemetry.addData("frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("backRightPos: ", backRight.getCurrentPosition());
        telemetry.addData("RightTarget: ", rightTarget);

        telemetry.addData("RESET ENCODERS", flag);

        telemetry.addData("neutral", gamepad2.a);
        telemetry.addData("right", gamepad2.b);
        telemetry.addData("left", gamepad2.x);
    }

    enum ComponentType{         //helps with clipValues
        NONE,
        MOTOR,
        SERVO
    }

    public double clipValues(double initialValue, ComponentType type) {
        double finalval=0;
        if (type == ComponentType.MOTOR)
            finalval =  Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        if (type == ComponentType.SERVO)
            finalval= Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return finalval;
    }


    public boolean setZipLinePosition(double pos){//slider values
        if(pos == 1){
            zipLiner.setPosition(SERVO_MAX);
        } else if(pos == -1){
            zipLiner.setPosition(SERVO_MIN);
        } else if(pos == 0){
            zipLiner.setPosition(SERVO_NEUTRAL);
        }

        return true;
    }

    //TODO: Calibrate this motor for the arm
    public void setArmPivot(double power){
        armPivot.setPower(power);
        //armPivot.setPower(clipValues(power, ComponentType.MOTOR));
    }

    public void manualDrive(){
        setToWOEncoderMode();

        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        setMotorPower(rightPower, leftPower);
    }

    public void turtleDrive(){
        setToWOEncoderMode();

        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        setMotorPower(rightPower*.7, leftPower*.7);
    }

    public void loop(){

    }

    public void stop(){

    }

}
