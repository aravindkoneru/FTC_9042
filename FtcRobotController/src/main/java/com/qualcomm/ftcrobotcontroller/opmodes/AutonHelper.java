package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Aravind on 12/30/2015.
 */
public class AutonHelper extends OpHelperClean {

    //ENCODER CONSTANTS TODO: Calibrate all of these values
    private final double CIRCUMFERENCE_INCHES = 4*Math.PI,
            TICKS_PER_ROTATION = 1200/1.05,
            TICKS_PER_INCH = TICKS_PER_ROTATION/CIRCUMFERENCE_INCHES,
            TOLERANCE = 40;

    private int rightTarget,
            leftTarget,
            targetPos;

    private final double ROBOT_WIDTH = 14.5;


    public boolean runStraight(double distance_in_inches, boolean speed) {//Sets values for driving straight, and indicates completion
        leftTarget = (int) (distance_in_inches * TICKS_PER_INCH);
        rightTarget = leftTarget;
        setTargetValueMotor();

        if(speed){
            setMotorPower(.9, .9);//TODO: Stalling factor that Libby brought up; check for adequate power
        }
        else{
            setMotorPower(.3,.3);
        }

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

    public boolean resetProp(){
        propellor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int currentPos = propellor.getCurrentPosition();
        if (targetPos==0) {
            targetPos = currentPos + (280 - (currentPos % 280));
        }
            propellor.setTargetPosition(targetPos);
            propellor.setPower(.4);
            if (targetPos - currentPos <= 6) {
                resetPropellerEncoder();
                propellor.setPower(0);
                targetPos=0;
                return true;
            } else return false;
    }


    //sets the target position for the drive encoders
    public void setTargetValueMotor(){
        frontLeft.setTargetPosition(leftTarget);
        backLeft.setTargetPosition(leftTarget);

        frontRight.setTargetPosition(rightTarget);
        backRight.setTargetPosition(rightTarget);
    }

    //returns true if all the motors have reached the desired position
    public boolean hasReached() {
        return (Math.abs(frontLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(backLeft.getCurrentPosition() - leftTarget) <= TOLERANCE &&
                Math.abs(frontRight.getCurrentPosition() - rightTarget) <= TOLERANCE &&
                Math.abs(backRight.getCurrentPosition() - rightTarget) <= TOLERANCE);
    }

    public boolean setTargetValueTurn(double degrees) {

        int encoderTarget = (int) (degrees / 360 * Math.PI * ROBOT_WIDTH * TICKS_PER_INCH);     //theta/360*PI*D
        leftTarget = encoderTarget;
        rightTarget = -encoderTarget;
        setTargetValueMotor();
        setMotorPower(.4, .4);

        if (hasReached()) {
            setMotorPower(0, 0);
            return true;//done traveling
        }
        return false;
    }

}