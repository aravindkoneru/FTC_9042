package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelperClean{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
        SECOND_RESET,
        THIRD_STATE,
        FOURTH_STATE,
        LAST_STATE
    }

    private RunState rs = RunState.RESET_STATE;

    public TestEncoders() {}


    @Override
    public void loop() {


        basicTel();
        setToEncoderMode();

        switch(rs) {
            case RESET_STATE:
            {
                while(resetEncoders()==false)
                    telemetry.addData("in the reset loop", 10);
                    rs=RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:
            {

                if(runStraight(-10) ){
                    rs = RunState.LAST_STATE;
                }
                break;
            }
//            case FIRST_RESET: {
//
//                if(resetEncoders()){//make sure that the encoder have reset
//
//                    rs = RunState.SECOND_STATE;
//                }
//                break;
//            }
//            case SECOND_STATE:
//            {
//                if (setTargetValueTurn(90)){
//                    rs = RunState.LAST_STATE;
//                }
//                break;
//            }

            case LAST_STATE:
            {
                stop();
            }
        }
    }
}
