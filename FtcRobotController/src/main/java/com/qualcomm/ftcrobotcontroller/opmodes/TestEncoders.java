package com.qualcomm.ftcrobotcontroller.opmodes

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelper{

    private boolean isRunning = false;

    public TestEncoders()
    {}

    @Override
    public void loop() {
        telemetry();
        if(!isRunning)      //Initial set target and power
        {
            setTargetValue(10);
            setPower(0.4, 0.4);
            isRunning=true;
        }

        if(checkRunStatus())
    }
}
