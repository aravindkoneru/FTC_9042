package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

    public void register(OpModeManager manager) {

        //Tele-Op
        manager.register("MainTeleOp", MainTeleOp.class);

        //Autonomous
        manager.register("Blue Alliance Blue Mountain", BlueSideBlue.class);
        manager.register("Blue Alliance Red Mountain", BlueSideRed.class);
        manager.register("Blue Climbers", ShelterDumper.class);
        manager.register("Red Climbers", ShelterDumperRed.class);
        manager.register("Floor Goal", FloorZone.class);
    }
}
