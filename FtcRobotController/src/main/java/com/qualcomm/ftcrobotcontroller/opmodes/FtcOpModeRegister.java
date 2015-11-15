
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

  public void register(OpModeManager manager) {

      manager.register("NullOp", NullOp.class);


      //driving
      manager.register("MainTeleOp", MainTeleOp.class);

      //autons
      manager.register("BlueSideRed", BlueSideRed.class);
//      manager.register("BlueSideBlue", BlueSideBlue.class);
//      manager.register("BlueSideRedSharp", BlueSideRedSharp.class);

  }
}
