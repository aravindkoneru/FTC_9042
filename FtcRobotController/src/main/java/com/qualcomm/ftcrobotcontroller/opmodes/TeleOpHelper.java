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
public class TeleOpHelper extends OpMode {

    //driving motors
    DcMotor frontLeft,
            backLeft;

    DcMotor frontRight,
            backRight;

    //arm motors
    DcMotor armMotor1,
            armMotor2,
            armPivot,
            propeller;

    //zipline servo
    Servo zipLiner,
          dropClimber;


    //SERVO CONSTANTS
    private final double SERVO_MAX = .6,
            SERVO_MIN = .2,
            SERVO_NEUTRAL = 9.0 / 17;
    //Stops the continuous servo

    //MOTOR RANGES
    private final double MOTOR_MAX = 1,
            MOTOR_MIN = -1;



    //ENCODER CONSTANTS
    private final double CIRCUMFERENCE_INCHES = 4 * Math.PI,
            TICKS_PER_ROTATION = 1200 / 1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION / CIRCUMFERENCE_INCHES,
            TOLERANCE = 40,
            ROBOT_WIDTH = 14.5;

    private int turn=0,
            targetPos;


    public TeleOpHelper() {

    }


    public void init() {
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
        propeller = hardwareMap.dcMotor.get("prop");

        //zipline servo
        zipLiner = hardwareMap.servo.get("zip");
        dropClimber = hardwareMap.servo.get("drop");


        setDirection();
        resetEncoders();
    }


    public void setDirection() {
        if (frontLeft.getDirection() == DcMotor.Direction.REVERSE) {
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }

        if (backLeft.getDirection() == DcMotor.Direction.REVERSE) {
            backLeft.setDirection(DcMotor.Direction.FORWARD);
        }

        if (frontRight.getDirection() == DcMotor.Direction.FORWARD) {
            frontRight.setDirection(DcMotor.Direction.REVERSE);
        }

        if (backRight.getDirection() == DcMotor.Direction.FORWARD) {
            backRight.setDirection(DcMotor.Direction.REVERSE);
        }

        if (armMotor1.getDirection() == DcMotor.Direction.FORWARD) {
            armMotor1.setDirection(DcMotor.Direction.REVERSE);
        }
    }


    //ENCODER MANIPULATION
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

    public void setToEncoderMode() {

        frontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        frontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    public void setToWOEncoderMode() {
        frontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        frontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    //MANUAL MOVEMENT (DRIVING)
    public void manualDrive(boolean turtleDrive) {
        setToWOEncoderMode();

        double rightPower = gamepad1.right_stick_y;
        double leftPower = gamepad1.left_stick_y;

        if (turtleDrive) {
            setMotorPower(rightPower * .3, leftPower * .3);
        } else {
            setMotorPower(rightPower, leftPower);
        }

    }

    public void backDrive() {
        setToWOEncoderMode();

        double rightPower = -gamepad1.right_stick_y;
        double leftPower = -gamepad1.left_stick_y;

        setMotorPower(.3*rightPower, .3*leftPower);

    }

    public void setMotorPower(double leftPower, double rightPower) {
        clipValues(leftPower, ComponentType.MOTOR);
        clipValues(rightPower, ComponentType.MOTOR);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);

        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);
    }


    //MANUAL MOVEMENT (OPERATING)
    public void spinPropeller(int direction) {
        if (direction == 1) {
            propeller.setPower(1);
        } else if (direction == -1) {
            propeller.setPower(-1);
        } else if (direction == 0) {
            propeller.setPower(0);
        }
    }

    public void moveTubing(double power) {
        armMotor1.setPower(power);
        armMotor2.setPower(power);
    }

    public void dropClimber(boolean dump) {
        if (dump) {
            dropClimber.setPosition(1);
        }
        else if (!dump){
            dropClimber.setPosition(.8);
        }
    }

    public boolean resetProp(){
        propeller.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int currentPos = propeller.getCurrentPosition();
        if (turn==0) {
            if (targetPos==0) {
                targetPos = currentPos + (280-(currentPos % 280));
            }
            propeller.setTargetPosition(targetPos);
            propeller.setPower(.4);
            if (targetPos - currentPos <= 6) {
                resetPropellerEncoder();
                propeller.setPower(0);
                turn = 1;
                return true;
            } else return false;
        } else return false;
    }

    public void resetPropellerEncoder(){
        propeller.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        propeller.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public boolean setZipLinePosition(double pos) {//slider values
        if (pos == 1) {
            zipLiner.setPosition(SERVO_MAX);
        } else if (pos == -1) {
            zipLiner.setPosition(SERVO_MIN);
        } else if (pos == 0) {
            zipLiner.setPosition(SERVO_NEUTRAL);
        }
        telemetry.addData("00 Zipline moving at: ", pos);
        return true;
    }

    public void setArmPivot(double power) {
        armPivot.setPower(power);
    }


    //HELPER METHODS
    enum ComponentType {
        NONE,
        MOTOR,
        SERVO
    }

    public double clipValues(double initialValue, ComponentType type) {
        double finalval = 0;
        if (type == ComponentType.MOTOR)
            finalval = Range.clip(initialValue, MOTOR_MIN, MOTOR_MAX);
        if (type == ComponentType.SERVO)
            finalval = Range.clip(initialValue, SERVO_MIN, SERVO_MAX);
        return finalval;
    }

    //DEBUG
    public void basicTel() {
        telemetry.addData("01 frontLeftPos: ", frontLeft.getCurrentPosition());
        telemetry.addData("02 backLeftPos: ", backLeft.getCurrentPosition());

        telemetry.addData("04 frontRightPos: ", frontRight.getCurrentPosition());
        telemetry.addData("05 backRightPos: ", backRight.getCurrentPosition());

        telemetry.addData("07 ArmMotor1: ", armMotor1.getCurrentPosition());
        telemetry.addData("08 ArmMotor2: ", armMotor2.getCurrentPosition());
        telemetry.addData("09 propeller: ", propeller.getCurrentPosition());

        telemetry.addData("10 Target Position: ", targetPos);
        telemetry.addData("Dump position",dropClimber.getPosition());

    }


    //MANDATORY METHODS
    public void loop() {
    }

    @Override
    public void stop() {
        setMotorPower(0, 0);//brake the movement of drive
        moveTubing(0);//brake the tape measure
        setArmPivot(0);//brake the arm pivot
        setZipLinePosition(0);
        spinPropeller(0);

    }

}