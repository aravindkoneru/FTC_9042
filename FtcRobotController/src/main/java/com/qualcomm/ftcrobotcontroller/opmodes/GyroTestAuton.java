//package com.qualcomm.ftcrobotcontroller.opmodes;
//
///**
// * Created by Tim on 10/25/2015.
// */
////STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner
//
//public class GyroTestAuton extends AutonHelper{
//
//
//    //establish run states for auton
//    enum RunState{
//        RESET_STATE,
//        GYRO_CALIBRATE,
//        FIRST_STATE,
//        FIRST_RESET,
//        SECOND_STATE,
//        LAST_STATE
//    }
//
//
//    private RunState rs = RunState.RESET_STATE;
//
//    public GyroTestAuton() {}
//
//
//    @Override
//    public void loop() {
//
//        telemetry.addData("Current Runstate", rs);
//
//        basicTel();
//        propellerSetToEncoderMode();
//        setToEncoderMode();
//        alternatePropeller(on);
//
//        switch(rs) {
//            case GYRO_CALIBRATE:{
//                if (!calibratingGyro()) {
//                    rs = RunState.FIRST_STATE;
//                }
//                break;
//            }
//            case RESET_STATE: {
//                setZipLinePosition(0);
//                dropClimber(false);
//                resetEncoders();
//                gyro.calibrate();
//                rs = RunState.GYRO_CALIBRATE;
//                break;
//            }
//            case FIRST_STATE: {
//                on = true;
//                if (driveWithoutVeer(-20, false)) {
//                    rs = RunState.FIRST_RESET;
//                }
//                break;
//            }
//            case FIRST_RESET: {
//
//                if (resetEncoders()) {//make sure that the encoder have reset
//                    rs = RunState.SECOND_STATE;
//                }
//                break;
//            }
//            case SECOND_STATE: {
//                if (turnRightToDegree(360)) {
//                    rs = RunState.LAST_STATE;
//                }
//                break;
//            }
//            case LAST_STATE: {
//                stop();
//            }
//        }
//    }
//}