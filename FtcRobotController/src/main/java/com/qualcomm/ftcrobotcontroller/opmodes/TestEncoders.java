package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Tim on 10/25/2015.
 */
public class TestEncoders extends OpHelper{

    public TestEncoders()
    {}

    @Override
    public void loop() {
        telemetry();
        setToEncoderMode();
        setTargetValue(-20);
    }
}
