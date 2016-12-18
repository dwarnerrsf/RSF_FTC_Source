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
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Pushbot: 8605 Auto 9", group="Pushbot")
public class RSF_8605_Auto9 extends RSF_BaseOp {
    private int stage = 0;

    private DcMotor pusher = null;
    private DcMotor shooter8605 = null;
    private int shooterPosition = 0;

    private final int Full_Rotation = 1120;

    private OpticalDistanceSensor odsSensor = null;
    private Servo leftPusher = null;
    private Servo rightPusher = null;

    private RSF_ColorModule color2 = new RSF_ColorModule();

    @Override
    public void runOpMode() throws InterruptedException {
        collector.Initialize(hardwareMap, DcMotorSimple.Direction.REVERSE);
        color.Initialize(hardwareMap, "COLORLEFT", false);
        color2.Initialize(hardwareMap,"COLORRIGHT", false);
        deviceInterface.Initialize(hardwareMap, 5);
        engine.Initialize(hardwareMap, RSF_States.Encoders.On);
        engine.SetSpeed(0.0d);
        lift.Initialize(hardwareMap);

        pusher = hardwareMap.dcMotor.get("PUSH");
        pusher.setDirection(DcMotor.Direction.FORWARD);
        pusher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pusher.setPower(0.0d);

        shooter8605 = hardwareMap.dcMotor.get("SHOOT");
        shooter8605.setDirection(DcMotor.Direction.FORWARD);
        shooter8605.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter8605.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        odsSensor = hardwareMap.opticalDistanceSensor.get("ods");
        leftPusher = hardwareMap.servo.get("LEFTPUSH");
        leftPusher.setPosition(0.0d);

        rightPusher = hardwareMap.servo.get("RIGHTPUSH");
        rightPusher.setPosition(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            shooterPosition = shooter8605.getCurrentPosition();
            Execute();

            odsSensor.enableLed(true);

            telemetry.addData("Stage: ", stage);
            telemetry.addData("Time: ", time);
            telemetry.addData("Shooter: ", shooterPosition);

            telemetry.addData("Left Raw: ", "R-" + color.Detect("Red") + " G-" + color.Detect("Green") + " B-" + color.Detect("Blue"));
            telemetry.addData("Right Raw: ", "R-" + color2.Detect("Red") + " G-" + color2.Detect("Green") + " B-" + color2.Detect("Blue"));

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
            case 4:
                Stage_4();
                break;
            case 5:
                Stage_5();
                break;
            case 6:
                Stage_6();
                break;
            case 7:
                Stage_7();
                break;
            case 8:
                Stage_8();
                break;
            default:
                engine.Stop();
                break;
        }
    }

    public void Stage_1() {
        int target = (int)(Full_Rotation * 3.75f);

        if (engine.GetAverageEncoderPosition() < target) {
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

    public void Stage_2()
    {
        if (shooterPosition < -4400) {
            resetStartTime();

            shooter8605.setPower(0.0d);
            stage = 3;
        }
        else {
            shooter8605.setPower(0.50d);
        }
    }

    public void Stage_3()
    {
        if (time < 3.0d) {
            shooter8605.setPower(0.0d);
            collector.EnableForward();
        }
        else {
            resetStartTime();

            collector.Disable();
            stage = 4;
        }
    }

    public void Stage_4()
    {
        if (time < 1.0d) {
            shooter8605.setPower(0.50d);
        }
        else {
            resetStartTime();

            shooter8605.setPower(0.0d);
            stage = 5;
        }
    }

    public void Stage_5() {
        if (odsSensor.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Up, 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 6;
        }
    }

    public void Stage_6() {
        int target = (int)(Full_Rotation * 1.0f);

        if (engine.GetAverageEncoderPosition() < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 7;
        }
    }

    /*public void Stage_5() {
        int target = (int)(Full_Rotation * 3.0f);

        if (engine.GetAverageEncoderPosition() < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 6;
        }
    }*/

    public void Stage_7() {
        if (time < 0.85d) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 8;
        }
    }

    public void Stage_8() {
        engine.Stop();

        RSF_States.SensorColor leftColor = color.Detect();
        RSF_States.SensorColor rightColor = color.Detect();

        String lc = "";
        String rc = "";

        if (leftColor == RSF_States.SensorColor.Red) {
            lc = "Red";
            rc = "None";
            leftPusher.setPosition(0.75d);
        }
        else if (rightColor == RSF_States.SensorColor.Red) {
            lc = "None";
            rc = "Red";
            rightPusher.setPosition(0.75d);
        }
        else {
            lc = "None";
            rc = "None";
        }

        telemetry.addData("Left Color: ", lc);
        telemetry.addData("Right Color: ", rc);
    }
}


