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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Pushbot: 7696 Teleop", group="Pushbot")
public class RSF_7696_Teleop extends RSF_BaseOp {
    private Servo leftPusher = null;
    private Servo rightPusher = null;

    private boolean isForward = true;
    private double moveSpeed = 0.0d;
    private double servoPosition = 0.0d;

    private DcMotor lift_1 = null;
    private DcMotor lift_2 = null;

    private double speedModifier = 1.0d;

    @Override
    public void runOpMode() throws InterruptedException {
        isForward = true;
        moveSpeed = 0.0d;

        engine.Initialize(hardwareMap, RSF_States.Encoders.On);
        engine.SetSpeed(moveSpeed);

        collector.Initialize(hardwareMap, DcMotor.Direction.REVERSE);
        collector.SetActivePower(1.0d);

        lift_1 = hardwareMap.dcMotor.get("LIFT1");
        lift_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_1.setPower(0.0d);

        lift_2 = hardwareMap.dcMotor.get("LIFT2");
        lift_2.setDirection(DcMotorSimple.Direction.REVERSE);
        lift_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_2.setPower(0.0d);

        shooter.Initialize(hardwareMap);
        shooter.SetActivePower(1.0d);

        leftPusher = hardwareMap.servo.get("LEFTPUSH");
        leftPusher.setDirection(Servo.Direction.REVERSE);
        leftPusher.setPosition(0.0d);

        rightPusher = hardwareMap.servo.get("RIGHTPUSH");
        rightPusher.setDirection(Servo.Direction.REVERSE);
        rightPusher.setPosition(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        leftPusher.setPosition(0.60d);
        rightPusher.setPosition(0.50d);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            RSF_MoveInput movement = RSF_MoveInput.Check(gamepad1);

            switch (movement.MoveType()) {
                case Dpad:
                    //engine.Move(movement.Dpad(), (isForward) ? moveSpeed : moveSpeed * -1);
                    engine.Move(movement.Dpad(), ((isForward) ? 1 : -1) * speedModifier);
                    break;
                case Joystick:
                    double left = (movement.Joystick().Left() * ((isForward) ? 1 : -1) * speedModifier);
                    double right = (movement.Joystick().Right() * ((isForward) ? 1 : -1) * speedModifier);
                    engine.SetPower(left, left, right, right);
                    break;
                default:
                    engine.Stop();
                    break;
            }

            Controller_One();
            Controller_Two();

            telemetry.addData("Forward", (isForward) ? "True" : "False");

            Update(5);
        }
    }

    private void Controller_One() {
        if (gamepad1.a) {
            isForward = false;
        }
        else if (gamepad1.b) {
            isForward = true;
        }
        else if (gamepad1.right_bumper) {
            speedModifier = 1.0d;
        }
        else if (gamepad1.left_bumper) {
            speedModifier = 0.33d;
        }

        if (gamepad1.right_trigger > 0.0d) {
            shooter.EnableForward();
        }
        else {
            shooter.Disable();
        }
    }

    private void Controller_Two() {
        if (gamepad2.right_trigger > 0.0d) {
            lift_1.setPower(gamepad2.right_trigger);
            lift_2.setPower(gamepad2.right_trigger);
        }
        else if (gamepad2.left_trigger > 0.0d) {
            lift_1.setPower(-gamepad2.left_trigger);
            lift_2.setPower(-gamepad2.left_trigger);
        }
        else {
            lift_1.setPower(0.0d);
            lift_2.setPower(0.0d);
        }

        if (gamepad2.right_bumper) {
            collector.EnableForward();
        }
        else if (gamepad2.left_bumper) {
            collector.EnableReverse();
        }
        else {
            collector.Disable();
        }
    }
}
