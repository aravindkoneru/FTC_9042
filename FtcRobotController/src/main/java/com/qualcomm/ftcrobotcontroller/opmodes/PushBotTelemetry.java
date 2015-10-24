package com.qualcomm.ftcrobotcontroller.opmodes;
/**
 * Extends the PushBotHardware class to provide basic telemetry for the Push
 * Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-02-13-57
 */
public class PushBotTelemetry extends PushBotHardware       //This class extends PushBotHardware, and it handles telemetry for the opmodes
{
    //Since the init, loop, and stop methods required by the OpMode abstract class is already declared in PBHardware,
    //this is basically a purely convenience helper class.
    public void update_telemetry ()
    {
        telemetry.addData( "01", "Left Drive: "+ a_left_drive_power ()+ ", "+ a_left_encoder_count ());
        telemetry.addData( "02", "Right Drive: "+ a_right_drive_power ()+ ", "+ a_right_encoder_count ());
        telemetry.addData( "03", "Left Arm: " + a_left_arm_power ());
        telemetry.addData( "04", "Hand Position: " + a_hand_position ());
    }
}