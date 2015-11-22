
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
      manager.register("BlueSideBlue", BlueSideBlue.class);
      manager.register("BlueSideRed", BlueMountainRed.class);
      //manager.register("Floor Goal", FloorZone.class);
      //manager.register("Dropping CLimbers", DropClimbers.class);

  }
}
