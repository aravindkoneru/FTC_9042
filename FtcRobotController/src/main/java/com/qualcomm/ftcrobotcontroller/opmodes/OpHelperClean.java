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
public class OpHelperClean extends OpMode{

    //driving motors
    DcMotor frontLeft,
            backLeft;

    DcMotor frontRight,
            backRight;

    //arm motors
    DcMotor armMotor1,
            armMotor2,
            armPivot;

    //zipline servo
    Servo zipLiner,
          plow1,
          plow2;

    //encoder targets
    private int rightTarget,
            leftTarget;

    //SERVO CONSTANTS
    private final double SERVO_MAX=.6,
            SERVO_MIN=.2,
            SERVO_NEUTRAL = 9.0/17,//Stops the continuous servo
            PLOW_UP = .8,
            PLOW_DOWN = .4;

    //MOTOR RANGES
    private final double MOTOR_MAX=1,
            MOTOR_MIN=-1;


    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double CIRCUMFERENCE_INCHES = 4*Math.PI,
            TICKS_PER_ROTATION = 1200/1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION/CIRCUMFERENCE_INCHES,
            TOLERANCE = 40;

    //WHEELBASE CONSTANTS
    private final double WHEELBASEWIDTH = 15;

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

        //zipline servo
        zipLiner = hardwareMap.servo.get("zip");

        setDirection(); //ensures the proper motor directions
        resetTapeEncoders();
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

        if(armMotor1.getDirection() == DcMotor.Direction.FORWARD){
            armMotor1.setDirection(DcMotor.Direction.REVERSE);
        }

        //TODO configure arm motor direction

        //TODO config arm pivot direction
    }

    //moves tape measure based on direct
    public void moveTapeMeasure(double power){
        double difference=1;
        if (Math.abs(armMotor1.getCurrentPosition() - armMotor2.getCurrentPosition())>=50){
            difference=armMotor1.getCurrentPosition()/armMotor2.getCurrentPosition();
        }
        double leftPower=power*.3;
        double rightPower=power*.3*difference;
        armMotor2.setPower(rightPower);
        armMotor1.setPower(leftPower);
    }

    //reset all the drive encoders and return true if all encoders read 0
    public boolean resetTapeEncoders() {
        armMotor1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        armMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        return (armMotor1.getCurrentPosition() == 0 &&
                armMotor2.getCurrentPosition() == 0);
    }


    public boolean resetEncoders() {
        frontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        return ((frontLeft.getCurrentPosition() == 0) &&
                (backLeft.getCurrentPosition() == 0) &&
                (frontRight.getCurrentPosition() == 0) &&
                (backRight.getCurrentPosition() == 0));

    }

    //driving power
    public void setMotorPower(double leftPower, double rightPower){
        clipValues(leftPower, ComponentType.MOTOR);
        clipValues(rightPower, ComponentType.MOTOR);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }

    //sets all drive motors to encoder mode
    public void setToEncoderMode(){
        armMotor1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        armMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    //sets all drive motors to run without encoders
    public void setToWOEncoderMode()
    {
        armMotor1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        armMotor2.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public boolean runStraight(double distance_in_inches, boolean speed) {//Sets values for driving straight, and indicates completion
        leftTarget = (int) (distance_in_inches * TICKS_PER_INCH);
        rightTarget = leftTarget;
        setTargetValueMotor();

        if(speed){
            setMotorPower(.8, .8);//TODO: Stalling factor that Libby brought up; check for adequate power
        }
        else{
            setMotorPower(.4,.4);
        }


        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }


    //sets the target position for the drive encoders
    public void setTargetValueMotor(){
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    //returns true if all the motors have reached the desired postiion
    public boolean hasReached() {
        return (Math.abs(frontLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(backLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(frontRight.getCurrentPosition() - rightTarget) <= TOLERANCE &&
                Math.abs(backRight.getCurrentPosition() - rightTarget) <= TOLERANCE);
    }


    //basic debugging and feedback
    public void basicTel(){
        //left drive
        telemetry.addData("frontLeftPos: ", frontLeft.getCurrentPosition());
        telemetry.addData("backLeftPos: ", backLeft.getCurrentPosition());
        telemetry.addData("LeftTarget: ", leftTarget);
        //right drive
        telemetry.addData("frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("backRightPos: ", backRight.getCurrentPosition());
        telemetry.addData("RightTarget: ", rightTarget);
        //Tape Measures
        telemetry.addData("Tape Measure Left", armMotor1.getCurrentPosition());
        telemetry.addData("Tape Measure Right", armMotor2.getCurrentPosition());
    }


    enum ComponentType{         //helps with clipValues
        NONE,
        MOTOR,
        SERVO
    }

    //makes sure values are within the range for various components
    public double clipValues(double initialValue, ComponentType type) {
        double finalval=0;
        if (type == ComponentType.MOTOR)
            finalval =  Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        if (type == ComponentType.SERVO)
            finalval= Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return finalval;
    }

    //sets the postiion of the zipline
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

    //moves the arm at a constant speed
    //TODO: Calibrate this motor for the arm
    public void setArmPivot(double power){
        armPivot.setPower(power);
    }

    //normal driving mode
    //boolean is true when turtle drive should be enabled
    public void manualDrive(boolean turtleDrive){
        setToWOEncoderMode();

        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        if(turtleDrive){
            setMotorPower(rightPower*.5, leftPower*.5);
        } else{
            setMotorPower(rightPower, leftPower);
        }
    }
    private final double ROBOT_WIDTH = 14.5;
    public boolean setTargetValueTurn(double right, double left) {

        int leftEncoderTarget = (int) (left * TICKS_PER_INCH);
        int rightEncoderTarget = (int) (right * TICKS_PER_INCH);
        leftTarget = leftEncoderTarget;
        rightTarget = rightEncoderTarget;
        setTargetValueMotor();
        if (right==0){
            setMotorPower(.6, .1);//TODO: Stalling factor that Libby brought up; check for adequate power
        }
        if (left==0){
            setMotorPower(.1, .6);//TODO: Stalling factor that Libby brought up; check for adequate power
        }

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;
        }
        return false;
    }
    public void setPlowPosition(boolean up){
        if (up){
            plow1.setPosition(PLOW_UP);
            plow2.setPosition(PLOW_UP);
        }
        if (!up){
            plow1.setPosition(PLOW_DOWN);
            plow2.setPosition(PLOW_DOWN);
        }
    }

    //TODO: Make a function to move drive at same speed as the tape measure (Eric's suggestion)
    public void upMountain(){

    }

    public void loop(){

    }

    public void stop(){

        setMotorPower(0,0);//brake the movement of drive
        moveTapeMeasure(0);//brake the tape measure
        setArmPivot(0);//brake the arm pivot
        setZipLinePosition(0);
    }
}
