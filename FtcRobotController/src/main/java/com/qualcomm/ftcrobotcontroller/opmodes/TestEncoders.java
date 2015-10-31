package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelperClean{

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
                setTargetValueMotor(10,10);

                if(hasReached())
                {
                    rs = RunState.SECOND_STATE;
                    resetEncoders();
                }
                break;
            }
//            case SECOND_STATE:
//            {
//                setTargetValueMotor(5,5);
//
//                if(hasReached())
//                {
//                    rs = RunState.THIRD_STATE;
//                    resetEncoders();
//                }
//                break;
//            }
        }
    }
}
