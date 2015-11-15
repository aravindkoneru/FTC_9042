
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

  public void register(OpModeManager manager) {

      manager.register("NullOp", NullOp.class);

      //test full functionality of robot
      manager.register("SystemCheck", SystemCheck.class);

      //driving
      manager.register("TestEncoders", TestEncoders.class);

      //Tele-Op
      manager.register("MainTeleOp", MainTeleOp.class);

      //Autonomous
      manager.register("BlueSideBlue", BlueSideBlue.class);
      manager.register("BlueSideRed", BlueMountainRed.class);
      manager.register("Floor Goal", FloorZone.class);
      manager.register("Dropping CLimbers", DropClimbers.class);

  }
}
