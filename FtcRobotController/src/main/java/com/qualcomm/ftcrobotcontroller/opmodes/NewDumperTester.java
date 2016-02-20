package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class NewDumperTester extends AutonHelper{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        DRIVE_TO_STATE,
        DRIVE_RESET,
        BACKWARDS_STATE,
        BACKWARDS_RESET,
        LAST_STATE
    }

    private RunState rs = RunState.RESET_STATE;

    public NewDumperTester() {}


    @Override
    public void loop() {

        basicTel();
        telemetry.addData("state: ", rs);
        setToEncoderMode();
        propellerSetToEncoderMode();
        alternatePropeller(on);

        switch(rs) {
            case RESET_STATE:
            {
                setZipLinePosition(0);
                if (resetEncoders()){
                    rs= RunState.DRIVE_TO_STATE;
                }
                break;
            }
            case DRIVE_TO_STATE:
            {
                dropClimber(false);
                if (runStraight(60,false) || backBumper.isPressed()) {
                    rs = RunState.DRIVE_RESET;
                }
                break;
            }
            case DRIVE_RESET:
            {
                if (resetEncoders() && backBumper.isPressed()){
                rs = RunState.BACKWARDS_STATE;
                }
                else if (resetEncoders()){
                    rs = RunState.LAST_STATE;
                }
                break;
            }
            case BACKWARDS_STATE:
            {
                dropClimber.setPosition(.4);
                if (runStraight(-5, false)){
                    dropClimber(true);
                    rs = RunState.LAST_STATE;
                }
                break;
            }
            case LAST_STATE:
            {
                stop();
            }
        }
    }
}