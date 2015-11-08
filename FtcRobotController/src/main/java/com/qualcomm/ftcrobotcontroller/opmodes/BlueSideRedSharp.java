package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */

//STARTING POSITION = Middle on crack of 3 Mats from side non mountain corner
public class BlueSideRedSharp extends OpHelperClean{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        SECOND_RESET,
        THIRD_STATE,
        THIRD_RESET,
        FOURTH_STATE,
        FOURTH_RESET,
        FIFTH_STATE,
        FIFTH_RESET,
        SIXTH_STATE,
        SIXTH_RESET,
        LAST_STATE
    }


    private RunState rs = RunState.RESET_STATE;

    public BlueSideRedSharp() {}


    @Override
    public void loop() {


        basicTel();
        setToEncoderMode();

        switch(rs) {
            case RESET_STATE:
            {
                resetEncoders();
                rs= RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:
            {

                if(runStraight(36, false) ){
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET: {

                if(resetEncoders()){//make sure that the encoder have reset
                    rs = RunState.SECOND_STATE;
                }
                break;
            }
            case SECOND_STATE: {
                if (setTargetValueTurn(135)){
                    rs = RunState.SECOND_RESET;
                }
                break;
            }
            case SECOND_RESET:
            {
                if(resetEncoders()){//make sure that the encoder have reset
                    rs = RunState.THIRD_STATE;
                }
                break;
            }
            case THIRD_STATE:
            {
                if (runStraight(20, false)){
                    rs= RunState.FOURTH_STATE;
                }
                break;
            }
            case THIRD_RESET:
            {
                if (resetEncoders()){
                    rs= RunState.FOURTH_STATE;
                }
                break;
            }
            case FOURTH_STATE: {
                if (runStraight(70, true)){
                    rs= RunState.FOURTH_RESET;
                }
                break;
            }
            case FOURTH_RESET:
            {
                if (resetEncoders()){
                    rs= RunState.LAST_STATE;
                }
                break;
            }
            case FIFTH_STATE:
            {
                if (runStraight(20, false)){
                    rs= RunState.FIFTH_RESET;
                }
            }
            case FIFTH_RESET:
            {
                if (resetEncoders()){
                    rs= RunState.FIFTH_STATE;
                }
                break;
            }
            case SIXTH_STATE:
            {
                if (runStraight(70, true)){
                    rs= RunState.LAST_STATE;
                }
            }
            case LAST_STATE:
            {
                stop();
            }
        }
    }
}
