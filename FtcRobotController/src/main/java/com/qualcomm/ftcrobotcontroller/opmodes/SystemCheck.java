package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by aravindkoneru on 11/11/15.
 */
public class SystemCheck extends OpHelperClean{

    public SystemCheck(){

    }

    enum RunState{
        RIGHT_DRIVE,
        LEFT_DRIVE,
        PIVOT_UP,
        PIVOT_DOWN,
        TAPES_OUT,
        TAPES_IN,
        LAST;
    }

    double timer = 0;

    int checkPos = 0;

    private RunState currentState = RunState.RIGHT_DRIVE;

    private boolean[] checks = new boolean[7];

    private String[] systemName = {"RIGHT_DRIVE",
            "LEFT_DRIVE",
            "PIVOT_UP",
            "PIVOT_DOWN",
            "TAPES_OUT",
            "TAPES_IN"};

    public void init(){
        for(int x = 0; x < checks.length; x++){
            checks[x] = false;
        }
    }

    @Override
    public void loop(){
        for(int x = 0; x < 7; x++){
            telemetry.addData(systemName[x], checks[x]);
        }

        switch (currentState) {

            case RIGHT_DRIVE: {
                setMotorPower(0, 1);
                if (timer > 100) {
                    setMotorPower(0, 0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.LEFT_DRIVE;
                }
                timer++;
                break;
            }

            case LEFT_DRIVE: {
                setMotorPower(1, 0);
                if (timer > 100) {
                    setMotorPower(0, 0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.PIVOT_UP;
                }
                timer++;
                break;
            }

            case PIVOT_UP: {
                setArmPivot(-.2);
                if (timer > 100) {
                    setArmPivot(0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.PIVOT_DOWN;
                }
                timer++;
                break;
            }

            case PIVOT_DOWN: {
                setArmPivot(.2);
                if (timer > 50) {
                    setArmPivot(0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.TAPES_OUT;
                }
                timer++;
                break;
            }

            case TAPES_OUT:
            {
                moveTapeMeasure(.2);
                if(timer > 100){
                    moveTapeMeasure(0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.TAPES_IN;
                }
                timer++;
                break;
            }

            case TAPES_IN: {
                moveTapeMeasure(-.2);
                if (timer > 100) {
                    moveTapeMeasure(0);
                    timer = 0;
                    checks[checkPos] = true;
                    checkPos++;
                    currentState = RunState.LAST;
                }
                timer++;
                break;
            }

            case LAST:
            {
                stop();
                break;
            }
        }
    }




}
