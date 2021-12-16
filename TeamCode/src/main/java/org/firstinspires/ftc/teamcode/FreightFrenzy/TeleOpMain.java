
package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOpMain")
public class TeleOpMain extends LinearOpMode {
    FreightFrenzyRobot yoda = new FreightFrenzyRobot(this);
    double accelerator;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {



        double duckWheelSpeed = 0.6; // The speed the wheel to turn the duck carousel moves at.
        double spintakeSpeed = 0.6;

        double liftSpeed = 0.3; // The speed the lift moves at.



        DcMotor DWMotor = hardwareMap.get(DcMotor.class, "DWMotor"); // Port: 1 exp.
        DcMotor LiftMotor = hardwareMap.get(DcMotor.class, "LiftMotor"); // Port: 0 exp.d
        DcMotor SpintakeMotor = hardwareMap.get(DcMotor.class, "SpintakeMotor");

        // Put initialization blocks here.

        // Declaring the buttons may quickly change.
        boolean currentLiftUp;
        boolean currentLiftDown;
        boolean duckWheelLeft;
        boolean duckWheelRight;
        boolean spintakeIntake;
        boolean spintakeOuttake;

        // Declaring the former values of the buttons so we can tell if they changed.
        boolean priorLiftUp = false;
        boolean priorLiftDown = false;
        boolean priorDuckWheelLeft = false;
        boolean priorDuckWheelRight = false;
        boolean priorSpintakeIntake = false;
        boolean priorSpintakeOuttake = false;

        yoda.initHardware();

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.

                double forwardInput = gamepad1.left_stick_y; // Controls for moving back and forward.
                double strafeInput = gamepad1.left_stick_x; // Controls for strafing.
                double rotateInput = gamepad1.right_stick_x; // Controls for pivoting.
                // Controls to allow our robot to reach speeds up to maxSpeed.
                accelerator = gamepad1.right_trigger;
                currentLiftUp = gamepad2.right_bumper; // Controls for moving the lift up.
                currentLiftDown = gamepad2.left_bumper; // Controls for moving the lift down.
                duckWheelLeft = gamepad2.b; // Controls for rotating the duck wheel left.
                duckWheelRight = gamepad2.x; // Controls for moving the duck wheel right.
                spintakeIntake = gamepad2.y;
                spintakeOuttake = gamepad2.a;


                /*
                Lift requirements:
                    The first button pressed sets the direction.
                    Holding both buttons stops the motion.
                    Releasing one of those two buttons heads in the direction of the remaining button.
                    Releasing both buttons stops the motion.

                Lift requirements (video game option, not chosen):
                    The first button to be pressed sets the direction that we are going.
                    The other button is ignored while the first button is being held down.
                    Both buttons must be released to stop the lift
                    or if it reaches its target position.
                    The lift must be stopped in order to switch direction.

                DuckWheel requirements:

                 */

                // To control the lift.
                // Checking to see whether the buttons are still pressed.
                if(currentLiftUp != priorLiftUp || currentLiftDown != priorLiftDown) {
                    // 0 = no motion, 1 = up, -1 = down
                    // Checking to see which buttons are pushed.
                    int direction = (currentLiftUp?1:0) + (currentLiftDown?-1:0);
                    yoda.liftMotor(liftSpeed,direction);
                }

                // To control the duck wheel.
                // Checking to see whether the buttons are still pressed.
                if(duckWheelLeft != priorDuckWheelLeft || duckWheelRight != priorDuckWheelRight) {
                    // 1 = rotate left, -1 = rotate right, 0 = don't move.
                    // Checking to see which buttons are pressed.
                    int direction = (duckWheelLeft?1:0) + (duckWheelRight?-1:0);
                    double power = 0; // By default the wheel should not rotate.
                    // If the rotate left button is pressed, rotate left.
                    if(direction == 1) {
                        power = duckWheelSpeed;
                    }
                    // If the rotate right button is pressed, rotate right.
                    else if(direction == -1) {
                        power = -duckWheelSpeed;
                    }
                    // Setting the power the duck wheel motor should move at.
                    DWMotor.setPower(power);
                }

                if(spintakeIntake != priorSpintakeIntake || spintakeOuttake != priorSpintakeOuttake) {
                    int direction = (spintakeIntake?1:0) + (spintakeOuttake?-1:0);
                    double power = 0;
                    if(direction == 1) {
                        power = spintakeSpeed;
                    }
                    else if(direction == -1) {
                        power = -spintakeSpeed;
                    }
                    SpintakeMotor.setPower(power);
                }




                /* Here we show values on the driver hub that may be useful to know while driving
                the robot or during testing. */
                telemetry.addData("Lift", LiftMotor.getCurrentPosition());
                telemetry.addData("Accelerator", gamepad1.right_trigger);
                telemetry.update();

                /* Here we set the current button positions to the prior button position so we have
                updated data as we loop back.

                Note: This loops multiple times per millisecond.
                 */
                priorLiftUp = currentLiftUp;
                priorLiftDown = currentLiftDown;
                priorDuckWheelLeft = duckWheelLeft;
                priorDuckWheelRight = duckWheelRight;
                priorSpintakeIntake = spintakeIntake;
                priorSpintakeOuttake = spintakeOuttake;
            }
        }
    }
}
