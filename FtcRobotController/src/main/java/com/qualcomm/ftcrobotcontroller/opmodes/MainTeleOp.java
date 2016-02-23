package com.qualcomm.ftcrobotcontroller.opmodes;

public class MainTeleOp extends TeleOpHelper {

    public MainTeleOp() {

    }

    @Override
    public void loop() {
        basicTel();

        //DRIVER CONTROLS
        //driving
        if (gamepad1.right_bumper) {
            manualDrive(true); //turtle mode
        }
        else if (gamepad1.left_bumper){
            backDrive();
        }
        else {
            manualDrive(false);
        }

        //constant driving
        if (gamepad1.a) {
            setMotorPower(-.2, -.2);
        }
        else if (gamepad1.y) {
//            setMotorPower(-.2, -1);
            setMotorPower(.2 , .2);
        }

        //propeller
        if (gamepad1.left_trigger > 0) {
            spinPropeller(1);
        }
        else if (gamepad1.right_trigger > 0) {
            spinPropeller(-1);
        }
        else if (gamepad1.b) {
            resetProp();
        }
        else {
            spinPropeller(0);
        }

        //shelter climber drop
        if (gamepad1.dpad_up){
            dropClimber(true);
        }
        else if (gamepad1.dpad_down){
            dropClimber(false);
        }
        else if (gamepad1.dpad_left){
            dropClimber.setPosition(.4);
        }


        //OPERATOR CONTROLS
        //zipliners

        if (gamepad2.right_bumper) {
            setZipLinePosition(1);
        }
        else if (gamepad2.left_bumper) {
            setZipLinePosition(-1);
        }
        else {
            setZipLinePosition(0);
        }

        //arm
        if (gamepad2.dpad_down) {
            setArmPivot(-.7);
        }
        else if (gamepad2.dpad_up) {
            setArmPivot(.7);
        }
        else {
            setArmPivot(0);
        }

        //tube
        if (gamepad2.y) {
            moveTubing(1);
        }
        else if (gamepad2.a) {
            moveTubing(-1);
        }
        else {
            moveTubing(0);

        }
    }
}