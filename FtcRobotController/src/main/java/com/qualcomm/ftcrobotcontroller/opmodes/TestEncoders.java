package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelperClean{


    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        FIRST_RESET,
        SECOND_STATE,
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
                resetEncoders();
                rs=RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:
            {

                if(runStraight(10) )//&& debug)
                {
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET: {

                if(resetEncoders()){
                    rs = RunState.SECOND_STATE;
                }
                break;
            }
            case SECOND_STATE:
            {
//                if(runStraight(12))// && debug)
//                {
                //    resetEncoders();
                rs = RunState.LAST_STATE;
//                }
                break;
            }

            case LAST_STATE:
            {
            }


        }
    }
}
