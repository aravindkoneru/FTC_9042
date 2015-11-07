package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpHelperClean {

    //operator = gamepad2; driver = gamepad1

    public MainTeleOp(){

    }

    @Override
    public void loop() {
        //enable basic feedback
        basicTel();

        //move robot using joysticks
        if(gamepad1.right_bumper && gamepad1.left_bumper){
            manualDrive(true);
        } else{
            manualDrive(false);
        }

        if(gamepad1.y){
            setMotorPower(1,1);
        }

        if(gamepad1.a){
            setMotorPower(-1,-1);
        }


        //Handle zipliner positions
        if(gamepad2.right_bumper){
            setZipLinePosition(1);
        } else if(gamepad2.left_bumper){
            setZipLinePosition(-1);
        } else{
            setZipLinePosition(0);
        }

        //handle arm pivot
        if(gamepad2.dpad_down){
            setArmPivot(-.2);
        }else if(gamepad2.dpad_up){
            setArmPivot(.2);
        } else{
            setArmPivot(0);
        }

        //handle tape measure movement
        if(gamepad2.y) {
            moveTapeMeasure(.2);
        } else if(gamepad2.a){
            moveTapeMeasure(-.2);
        } else{
            moveTapeMeasure(0);
        }

    }

    @Override
    public void stop() {

    }

}
