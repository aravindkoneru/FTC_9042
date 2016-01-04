package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class SystemsCheck extends OpHelperClean{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        DRIVE_SIDE_RIGHT,
        DRIVE_SIDE_LEFT,
        DRIVE_FORWARDS,
        DRIVE_BACK,
        ARM_PIVOT_UP,
        ARM_PIVOT_DOWN,
        TAPE_MEASURE_OUT,
        TAPE_MEASURE_IN,
        ZIPLINER_OUT,
        ZIPLINER_IN,
        PROPELLER_RIGHT,
        PROPELLER_LEFT,
        LAST_STATE
    }
    int timer=0;


    private RunState rs = RunState.RESET_STATE;

    public SystemsCheck() {}


    @Override
    public void loop() {
//        setZipLinePosition(0);
        basicTel();
        setToEncoderMode();

        switch(rs) {
            case RESET_STATE:
            {
                setZipLinePosition(0);
                resetEncoders();
                rs= RunState.DRIVE_SIDE_RIGHT;
                break;
            }
            case DRIVE_SIDE_RIGHT:
            {
                setMotorPower(0,1);
                if(timer<100){
                    rs = RunState.DRIVE_SIDE_LEFT;
                    timer=0;
                }
                timer++;
                break;
            }
            case DRIVE_SIDE_LEFT: {
                setMotorPower(1,0);
                if(timer<100){//make sure that the encoder have reset
                    rs = RunState.DRIVE_FORWARDS;
                    timer=0;
                }
                timer++;
                break;
            }
            case DRIVE_FORWARDS: {
                setMotorPower(1,1);
                if (timer<100){
                    rs = RunState.DRIVE_BACK;
                    timer=0;
                }
                timer++;
                break;
            }
            case DRIVE_BACK: {
                setMotorPower(-1,-1);
                if(timer<100){//make sure that the encoder have reset
                    rs = RunState.ARM_PIVOT_UP;
                    timer=0;
                }
                timer++;
                break;
            }
            case ARM_PIVOT_UP:
            {
                if (timer<100){
                    rs= RunState.ARM_PIVOT_DOWN;
                    timer=0;
                }
                timer++;
                break;
            }
            case ARM_PIVOT_DOWN:
            {
                if (resetEncoders()){
                    rs= RunState.TAPE_MEASURE_OUT;
                    timer=0;
                }
                timer++;
                break;
            }
            case TAPE_MEASURE_OUT: {
                setZipLinePosition(-1);
                if (setTargetValueTurn(155)){
                    rs= RunState.TAPE_MEASURE_IN;
                    timer=0;
                }
                timer++;
                break;
            }
            case TAPE_MEASURE_IN:
            {
                setZipLinePosition(0);
                if (resetEncoders()){
                    rs= RunState.ZIPLINER_OUT;
                    timer=0;
                }
                timer++;
                break;
            }
            case ZIPLINER_OUT:
            {
                if (runStraight(-35, false)){
                    rs= RunState.ZIPLINER_IN;
                    timer=0;
                }
                timer++;
                break;
            }
            case ZIPLINER_IN:
            {
                spinPropeller(0);
                if (resetEncoders()){
                    rs= RunState.PROPELLER_LEFT;
                    timer=0;
                }
                timer++;
                break;
            }
            case PROPELLER_LEFT:
            {
                if (runStraight(-70, true)){
                    rs= RunState.PROPELLER_RIGHT;
                    timer=0;
                }
                timer++;
                break;
            }
            case PROPELLER_RIGHT:
            {
                if (runStraight(-70, true)){
                    rs= RunState.LAST_STATE;
                    timer=0;
                }
                timer++;
                break;
            }
            case LAST_STATE:
            {
                stop();
            }
        }
    }
}
