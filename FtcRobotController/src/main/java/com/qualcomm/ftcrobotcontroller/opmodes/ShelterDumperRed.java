package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class ShelterDumperRed extends AutonHelper{


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
        SEVENTH_STATE,
        LAST_STATE,
        RESET_PROP
    }


    private RunState rs = RunState.RESET_STATE;
    public ShelterDumperRed() {}


    @Override
    public void loop() {
        telemetry.addData("Alternating Propeller is ", on);
        telemetry.addData("Propeller Target Position is ", propellerTargetPos);
        telemetry.addData("Current Runstate", rs);

        basicTel();
        alternatePropeller(on);
        setToEncoderMode();
        propellerSetToEncoderMode();

        switch(rs) {
            case RESET_STATE: {
                setZipLinePosition(0);
                dropClimber(false);
                resetEncoders();
                rs = RunState.FIRST_STATE;
                break;
            }
            case FIRST_STATE: {
                on = true;
                if (runStraight(-12, false)) {
                    rs = RunState.FIRST_RESET;
                }
                break;
            }
            case FIRST_RESET: {

                if (resetEncoders()) {//make sure that the encoder have reset
                    rs = RunState.SECOND_STATE;
                }
                break;
            }
            case SECOND_STATE: {
                if (setTargetValueTurn(-70)) {
                    rs = RunState.SECOND_RESET;
                }
                break;
            }
            case SECOND_RESET: {
                if (resetEncoders()) {//make sure that the encoder have reset
                    rs = RunState.THIRD_STATE;
                }
                break;
            }
            case THIRD_STATE: {
                if (runStraight(-93, false)) {
                    rs = RunState.THIRD_RESET;
                }
                break;
            }
            case THIRD_RESET:
            {
                if (resetEncoders()){
                    rs = RunState.FOURTH_STATE;
                }
                break;
            }
            case FOURTH_STATE:
            {
                //330 worked for longer turn
                if (setTargetValueTurnRight(200)){
                    rs = RunState.FOURTH_RESET;
                }
                break;
            }
            case FOURTH_RESET:
            {
                if (resetEncoders()){
                    rs = RunState.FIFTH_STATE;
                }
                break;
            }
            case FIFTH_STATE:
            {
                on = false;
                spinPropeller(0);
                if (runStraight(15, false) || backBumper.isPressed()){
                    rs = RunState.FIFTH_RESET;
                }
                break;
            }
            case FIFTH_RESET:
            {
                if (resetEncoders()){
                    rs = RunState.SIXTH_STATE;
                }
                break;
            }
            case SIXTH_STATE:
            {
                if (backBumper.isPressed()) {
                    dropClimber(true);
                }
                rs = RunState.LAST_STATE;
                break;
            }

            case LAST_STATE: {
                stop();
            }
        }
    }
}