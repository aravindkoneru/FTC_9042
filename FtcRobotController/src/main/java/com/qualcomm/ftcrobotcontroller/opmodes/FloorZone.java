package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
//STARTING POSITION = Middle on crack of 2 Mats from side non mountain corner

public class FloorZone extends OpHelperClean{


    //establish run states for auton
    enum RunState{
        RESET_STATE,
        FIRST_STATE,

        LAST_STATE
    }
    int timer=0;
    int counter=0;


    private RunState rs = RunState.RESET_STATE;

    public FloorZone() {}


    @Override
    public void loop() {

        basicTel();
        telemetry.addData("state: ", rs);
        setToEncoderMode();

        switch(rs) {
            case RESET_STATE:
            {
                setZipLinePosition(0);
                setPlowPosition(down);
                if (resetEncoders()){
                    rs= RunState.FIRST_STATE;
                }
                break;
            }
            case FIRST_STATE:
            {
//                plowFlicker();
                if (runStraight(-87, true)){
                    rs=RunState.LAST_STATE;
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
