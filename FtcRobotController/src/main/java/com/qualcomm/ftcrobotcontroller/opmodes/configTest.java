package com.qualcomm.ftcrobotcontroller.opmodes;

public class configTest extends OpHelperClean {

    //operator = gamepad2; driver = gamepad1

    public configTest(){

    }

    private int position = 0;

    @Override
    public void loop() {
        //enable basic feedback
        basicTel();


        //move robot using joysticks
        if(gamepad1.x){
            armPivot.setPower(1);
        }
        else{
            armPivot.setPower(0);
        }

        if(gamepad1.a){
            frontLeft.setPower(.5);
        }
        else{
            frontLeft.setPower(0);
        }

        if (gamepad1.y){
            backLeft.setPower(1);
        }
        else {
            backLeft.setPower(0);
        }

        if (gamepad1.dpad_right){
            frontRight.setPower(1);
        }
        else{
            frontRight.setPower(0);
        }
        if (gamepad1.dpad_left){
            backRight.setPower(1);
        }
        else{
            backRight.setPower(0);
        }
        if (gamepad1.b) {
            propellor.setPower(1);
        }
        else{
            propellor.setPower(0);
        }
        if (gamepad1.dpad_down){
            armMotor1.setPower(1);
        }
        else{
            armMotor1.setPower(0);
        }
        if (gamepad1.dpad_up){
            armMotor2.setPower(1);
        }
        else{
            armMotor2.setPower(0);
        }




    }


    @Override
    public void stop() {

    }

}
