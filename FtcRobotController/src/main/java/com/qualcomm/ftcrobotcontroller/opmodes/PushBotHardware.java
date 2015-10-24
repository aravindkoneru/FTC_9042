package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//This is an example of a helper class for the OpMode class.
public class PushBotHardware extends OpMode
{
    //The instance fields are the components of the robot (e.g. motors, controllers, servos, etc)
    private DcMotorController v_dc_motor_controller_drive;  //One motor controller

    private DcMotor v_motor_left_drive;         //Declares the motors
    private DcMotor v_motor_right_drive;

    final int v_channel_left_drive = 1;         //Declares the channels of which the motors are
    final int v_channel_right_drive = 2;        //plugged into the motor controller.

    //Manipulator components:
    DcMotor v_motor_left_arm;

    Servo v_servo_left_hand;
    Servo v_servo_right_hand;

    public PushBotHardware()
    {} //Empty constructor

    @Override
    public void init ()         //Called ONCE per round
    {   //Maps all of the components, connecting the physical devices to their names in the code
        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("drive_controller");

        v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");
        v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");

        //Reverses direction, so that forward is really forward.
        v_motor_right_drive.setDirection (DcMotor.Direction.REVERSE);

        v_motor_left_arm = hardwareMap.dcMotor.get ("left_arm");

        double l_hand_position = 0.5;       //Initial value for the servos

        v_servo_left_hand = hardwareMap.servo.get ("left_hand");        //Maps the servos
        v_servo_right_hand = hardwareMap.servo.get ("right_hand");

        v_servo_left_hand.setPosition (l_hand_position);        //Sets them to their initial position
        v_servo_right_hand.setPosition (l_hand_position);

    }
    @Override public void loop ()
    {
        //NOTHING here, it will be written by actual opmodes
    }
    @Override public void stop ()
    {
        //STUFF to do when the game ends (e.g. open claw, shuts off actuator etc.)
    }

    //----------------------------------------------------------------------------------------------
    //Everything below are the methods that will make life easier.

    double scale_motor_power (double p_power)       // Default scaling program from examples
    {
        double l_scale = 0.0f;
        double l_power = Range.clip (p_power, -1, 1);
        double[] l_array =
            { 0.00, 0.05, 0.09, 0.10, 0.12
            , 0.15, 0.18, 0.24, 0.30, 0.36
            , 0.43, 0.50, 0.60, 0.72, 0.85
            , 1.00, 1.00};

        int l_index = (int) (l_power * 16.0);
        if (l_index < 0)
            l_index = -l_index;
        else if (l_index > 16)
            l_index = 16;

        if (l_power < 0)
            l_scale = -l_array[l_index];
        else
            l_scale = l_array[l_index];

        return l_scale;
    }

    double a_left_drive_power ()        //Getter for left motor power level
    {
        return v_motor_left_drive.getPower();
    }
    double a_right_drive_power ()       //Getter for right motor power level
    {
        return v_motor_right_drive.getPower ();
    }

    void set_drive_power (double p_left_power, double p_right_power)    //Setter for both motors
    {
        v_motor_left_drive.setPower (p_left_power);
        v_motor_right_drive.setPower (p_right_power);
    }

    public void run_using_encoders ()       //Sets up the motors for proper encoder mode
    {
        DcMotorController.RunMode l_mode = v_dc_motor_controller_drive.getMotorChannelMode( v_channel_left_drive);
        //gets current mode by using declared motor channel to find a motor inside the controller
        if (l_mode == DcMotorController.RunMode.RESET_ENCODERS)     //If it is resetting...
            v_dc_motor_controller_drive.setMotorChannelMode(v_channel_left_drive, DcMotorController.RunMode.RUN_USING_ENCODERS);
                                                                    //Then it should be set to RUN_USING_ENCODERS
        l_mode = v_dc_motor_controller_drive.getMotorChannelMode( v_channel_right_drive);       //Rinse and repeat.

        if (l_mode == DcMotorController.RunMode.RESET_ENCODERS)
            v_dc_motor_controller_drive.setMotorChannelMode(v_channel_right_drive, DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void reset_drive_encoders ()
    {
        v_dc_motor_controller_drive.setMotorChannelMode(v_channel_left_drive, DcMotorController.RunMode.RESET_ENCODERS);
        //Sets everything to resetting mode
        v_dc_motor_controller_drive.setMotorChannelMode( v_channel_right_drive, DcMotorController.RunMode.RESET_ENCODERS);
    }

    int a_left_encoder_count () //Getter for encoder values
    {
        return v_motor_left_drive.getCurrentPosition ();
    }
    int a_right_encoder_count ()
    {
        return v_motor_right_drive.getCurrentPosition ();

    }

    boolean have_drive_encoders_reached(double p_left_count, double p_right_count) //Calculates if encoders are finished
    {
        boolean l_status = false;

        if ((Math.abs (v_motor_left_drive.getCurrentPosition ()) > p_left_count) && (Math.abs (v_motor_right_drive.getCurrentPosition ()) > p_right_count))
        //Does some math, and then returns the correct status
            l_status = true;

        return l_status;
    }

    boolean have_drive_encoders_reset ()    //Method for checking encoder reset progress
    {
        boolean l_status = false;

        if ((a_left_encoder_count () == 0) && (a_right_encoder_count () == 0))
            l_status = true;

        return l_status;
    }
    double a_left_arm_power ()      //Sets arm powers
    {
        return v_motor_left_arm.getPower ();
    }
    double a_hand_position ()
    {
        return v_servo_left_hand.getPosition ();
    }

    void m_hand_position (double p_position)    //Proprietary servo setting method
    {
        double l_position = Range.clip( p_position, Servo.MIN_POSITION, Servo.MAX_POSITION);

        v_servo_left_hand.setPosition (l_position);
        v_servo_right_hand.setPosition (1.0 - l_position);
    }
}
