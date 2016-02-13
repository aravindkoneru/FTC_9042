package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class FloorZoneDelay extends AutonHelper{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        DELAY_STATE,
        FIRST_STATE,
        LAST_STATE
    }


    private RunState rs = RunState.RESET_STATE;
    int elapsedTime=0;

    public FloorZoneDelay() {}


    @Override
    public void loop() {

        basicTel();
        telemetry.addData("state: ", rs);
        telemetry.addData("Elapsed Time: ", elapsedTime/1000);
        setToEncoderMode();
        propellerSetToEncoderMode();
        alternatePropeller(on);

        switch(rs) {
            case RESET_STATE:
            {
                setZipLinePosition(0);
                if (resetEncoders()){
                    rs= RunState.DELAY_STATE;
                    setToWOEncoderMode();
                }
                break;
            }
            case DELAY_STATE:
            {
                elapsedTime+=18;
                if (elapsedTime>=10000){
                    rs = RunState.FIRST_STATE;
                }
                break;
            }
            case FIRST_STATE:
            {
                on = true;
                if (runStraight(-99,false)) {
                    rs = RunState.LAST_STATE;
                }
                break;
            }
            case LAST_STATE:
            {
                on=false;
                stop();
            }
        }
    }
}