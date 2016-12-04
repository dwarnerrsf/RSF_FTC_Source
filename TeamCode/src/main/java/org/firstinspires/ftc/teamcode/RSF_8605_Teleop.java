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

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Pushbot: 8605 Teleop", group="Pushbot")
public class RSF_8605_Teleop extends RSF_BaseOp {
    private DcMotor pusher = null;

    private double moveSpeed = 1.0d;

    @Override
    public void runOpMode() throws InterruptedException {
        moveSpeed = 1.0d;

        engine.Initialize(hardwareMap);
        engine.SetSpeed(moveSpeed);

        collector.Initialize(hardwareMap, DcMotor.Direction.REVERSE);
        lift.Initialize(hardwareMap);
        shooter.Initialize(hardwareMap);

        pusher = hardwareMap.dcMotor.get("PUSH");
        pusher.setDirection(DcMotor.Direction.FORWARD);
        pusher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pusher.setPower(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            RSF_MoveInput movement = RSF_MoveInput.Check(gamepad1);

            switch (movement.MoveType()) {
                case Dpad:
                    engine.Move(movement.Dpad(), moveSpeed);
                    break;
                case Joystick:
                    engine.Move(movement.Joystick());
                    break;
                default:
                    engine.Stop();
                    break;
            }

            Player_One();
            Player_Two();

            Update(40);
        }
    }

    private void Player_One() {
        if (gamepad1.left_trigger > 0.0d) {
            collector.EnableReverse();
        }
        else if (gamepad1.right_trigger > 0.0d) {
            collector.EnableForward();
        }
        else {
            collector.Disable();
        }

        if (gamepad1.a) {
            engine.SetSpeed(0.0d);
        }
        else if (gamepad1.b) {
            engine.SetSpeed(1.0d);
        }
        else if (gamepad1.x) {
            engine.SetSpeed(0.50d);
        }
    }

    private void Player_Two() {
        if (gamepad2.b) {
            shooter.EnableForward();
        }
        else {
            shooter.Disable();
        }

        if (gamepad2.left_trigger > 0.0d) {
            lift.EnableReverse();
        }
        else if (gamepad2.right_trigger > 0.0d) {
            lift.EnableForward();
        }
        else {
            lift.Disable();
        }

        if (gamepad2.dpad_left) {
            pusher.setPower(-1.0d);
        }
        else if (gamepad2.dpad_right) {
            pusher.setPower(1.0d);
        }
        else {
            pusher.setPower(0.0d);
        }
    }
}
