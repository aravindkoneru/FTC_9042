package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelper{

    enum RunState{
        RESET_STATE,
        FIRST_STATE,
        SECOND_STATE,
        THIRD_STATE,
        FOURTH_STATE
    }

    private RunState rs = RunState.RESET_STATE;
    public TestEncoders()
    {}

    @Override
    public void loop() {
        telemetry();
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
                if(setTargetValue(10))
                {
                    rs = RunState.SECOND_STATE;
                    resetEncoders();
                }
                break;
            }
            case SECOND_STATE:
            {
                if(setTargetValue(5))
                {
                    rs = RunState.THIRD_STATE;
                    resetEncoders();
                }
                break;
            }
        }
    }
}
