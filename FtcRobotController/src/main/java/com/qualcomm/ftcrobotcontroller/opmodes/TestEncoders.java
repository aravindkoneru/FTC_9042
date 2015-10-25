package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelper{

    public TestEncoders()
    {}

    @Override
    public void loop() {
        //telemetry();
        setTargetValue(10);
        if(checkRunStatus())
            setPower(0,0);
        else
            setPower(0.4,0.4);
    }
}
