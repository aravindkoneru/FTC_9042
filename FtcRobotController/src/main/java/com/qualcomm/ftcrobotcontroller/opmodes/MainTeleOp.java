package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends OpHelperClean {

    //TODO: Talk to drive team about controller prefs/who controls what
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


        //Handle zipliner positions
        if(gamepad2.x){
            setZipLinePosition(1);
        }

        if(gamepad2.b){
            setZipLinePosition(-1);
        }

        if(gamepad2.a){
            setZipLinePosition(0);
        }


        //handle arm pivot
        if(gamepad2.left_bumper){
            setArmPivot(-.2);
        }else if(gamepad2.right_bumper){
            setArmPivot(.2);
        } else{
            setArmPivot(0);
        }


        //handle tape measure movement
        if(gamepad2.left_trigger > 0) {
            moveTapeMeasure(.2);
        } else if(gamepad2.right_trigger > 0){
            moveTapeMeasure(-.2);
        } else{
            moveTapeMeasure(0);
        }

    }

    @Override
    public void stop() {

    }

}
