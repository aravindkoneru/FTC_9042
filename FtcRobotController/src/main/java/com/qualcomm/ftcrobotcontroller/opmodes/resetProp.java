package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

public class resetProp extends OpHelperClean {

    public resetProp() {

    }

    @Override
    public void loop() {
        propellor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        propellor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }
}