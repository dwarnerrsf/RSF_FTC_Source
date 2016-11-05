/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Pushbot: Base Op", group="Pushbot")
public class RSF_BaseOp extends LinearOpMode {
    private RSF_RobotEngine engine = new RSF_RobotEngine();
    private ElapsedTime period = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        engine.Initialize(hardwareMap);
        engine.SetSpeed(1.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            RSF_MoveInput movement = CheckInput();

            switch (movement.MoveType()) {
                case Dpad:
                    engine.Move(movement.Dpad());
                    break;
                case Joystick:
                    engine.Move(movement.Joystick());
                    break;
                default:
                    engine.Stop();
                    break;
            }

            waitForTick(40);
        }
    }

    private double Absolute(double value) {
        if (value > 1.0d) {
            return 1.0d;
        } else if (value < -1.0d) {
            return -1.0d;
        } else {
            return value;
        }
    }

    private RSF_MoveInput CheckInput() {
        if (!gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_down) {
            double left = Absolute(-gamepad1.left_stick_y);
            double right = Absolute(-gamepad1.right_stick_y);

            return new RSF_MoveInput(new RSF_Joysticks(left, right));
        }
        else {
            if (gamepad1.dpad_up && gamepad1.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.UpLeft);
            } else if (gamepad1.dpad_up && gamepad1.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.UpRight);
            } else if (gamepad1.dpad_down && gamepad1.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.DownLeft);
            } else if (gamepad1.dpad_down && gamepad1.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.DownRight);
            } else if (gamepad1.dpad_left) {
                return new RSF_MoveInput(RSF_States.DPad.Left);
            } else if (gamepad1.dpad_right) {
                return new RSF_MoveInput(RSF_States.DPad.Right);
            } else if (gamepad1.dpad_up) {
                return new RSF_MoveInput(RSF_States.DPad.Up);
            } else if (gamepad1.dpad_down) {
                return new RSF_MoveInput(RSF_States.DPad.Down);
            }
            else {
                return new RSF_MoveInput(RSF_States.MoveType.None);
            }
        }
    }

    private void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
