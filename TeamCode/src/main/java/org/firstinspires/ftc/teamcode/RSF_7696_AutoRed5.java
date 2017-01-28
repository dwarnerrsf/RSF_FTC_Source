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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Pushbot: 7696 Auto Red 5", group="Pushbot")
@Disabled
public class RSF_7696_AutoRed5 extends RSF_BaseOp {
    private int stage = 0;
    private double moveSpeed = 0.0d;

    private Servo leftPusher = null;
    private Servo rightPusher = null;

    private final int Full_Rotation = 1120;

    @Override
    public void runOpMode() throws InterruptedException {
        collector.Initialize(hardwareMap, DcMotorSimple.Direction.REVERSE);
        color.Initialize(hardwareMap, false );
        deviceInterface.Initialize(hardwareMap, 5);
        engine.Initialize(hardwareMap, RSF_States.Encoders.On);
        engine.SetSpeed(moveSpeed);
        lift.Initialize(hardwareMap);
        shooter.Initialize(hardwareMap);

        leftPusher = hardwareMap.servo.get("LEFTPUSH");
        leftPusher.setPosition(0.0d);

        rightPusher = hardwareMap.servo.get("RIGHTPUSH");
        rightPusher.setPosition(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        moveSpeed = 0.15d;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            Execute();

            telemetry.addData("Stage: ", stage);
            telemetry.addData("Time: ", time);

            Update(40);
        }
    }

    public void Execute() {
        switch (stage) {
            case 1:
                Stage_1();
                break;
            case 2:
                Stage_2();
                break;
            case 3:
                Stage_3();
                break;
            default:
                engine.Stop();
                break;
        }
    }

    public void Stage_1() {
        int target = -(int)(Full_Rotation * 1.25f);

        if (engine.GetAverageEncoderPosition() > target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 2;
        }
    }

    public void Stage_2() {
        if (time < 6.0d) {
            shooter.EnableForward();
        } else {
            resetStartTime();
            shooter.Disable();
            stage = 3;
        }
    }

    public void Stage_3() {
        if (time < 2.0d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        } else {
            resetStartTime();
            engine.Stop();
            stage = 4;
        }
    }
}


