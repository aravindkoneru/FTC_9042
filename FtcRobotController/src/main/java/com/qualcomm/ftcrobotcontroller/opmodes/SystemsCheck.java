package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class SystemsCheck extends OpHelperClean{


    //establish run states for auton
    enum RunState{
        ZERO_STATE,
        FIRST_STATE,
        SECOND_STATE,
        THIRD_STATE,
        FOURTH_STATE,
        FIFTH_STATE,
        SIXTH_STATE,
        SEVENTH_STATE,
        EIGHT_STATE,
        NINTH_STATE,
        LAST_STATE
    }


    private RunState rs = RunState.ZERO_STATE;

    public SystemsCheck() {}


    @Override
    public void loop() {


        basicTel();
        setToEncoderMode();

        switch(rs) {
            case ZERO_STATE:
            {
                resetEncoders();
                rs= RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE:
            {
                setMotorPower(0,1);
                rs = RunState.SECOND_STATE;
                break;
            }
            case SECOND_STATE:
            {
                setMotorPower(1,0);
                rs = RunState.FOURTH_STATE;
                break;
            }
            case FOURTH_STATE:
            {
                setArmPivot(.1);
                rs = RunState.SIXTH_STATE;
                break;
            }
            case SIXTH_STATE:
            {
                setZipLinePosition(1);
                rs= RunState.SEVENTH_STATE;
                break;
            }
            case SEVENTH_STATE: {
                setZipLinePosition(-1);
                rs= RunState.NINTH_STATE;
                break;
            }
            case NINTH_STATE:
            {
                armMotor1.setPower(.2);
                armMotor2.setPower(.2);
                rs= RunState.LAST_STATE;
                break;
            }
            case LAST_STATE:
            {
                stop();
            }

        }
    }
}
