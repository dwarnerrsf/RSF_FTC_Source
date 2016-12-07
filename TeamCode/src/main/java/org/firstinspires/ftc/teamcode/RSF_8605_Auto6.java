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


@TeleOp(name="Pushbot: 8605 Auto 6", group="Pushbot")
public class RSF_8605_Auto6 extends RSF_BaseOp {
    private final String VuforiaLicenseKey = "AZjwTlP/////AAAAGQ0Nw67MdEzAm0Cuvb+oJGOCSFyqWb6WplQDZyJo9vOTw0iLTPRXEXyjZ/WAN+V4wKPlbTv0pbPad2yBRZ3vyHl4hRafu7wJGLMgzfsTeozU4SzDgl378Q3zGZsJWK+UkfmekmXkzeysQqnkL0rZK+1KNaARZjbR14/OONP2j9AjqOoJa6yMMMN/2jgM5x/Lshv/++vTyHUzJYs7rEHh26FGDtlE9J8FoRXaNPrii2i3j2msG2bTO0AzrlwnK6AMtP92bqRGHnIvU4GQVnMgi2fn/EoxNRLiNktloDfdzv9vRaU3zgpOENxmqzkfdBskZzfy6EYGsEzMQPtJ07brBfDhC0l9w4kvuBi2i5dK7Mbo";
    private int beaconIndex = 0;
    private int stage = 0;

    private RSF_Beacon beacon = null;
    private String beaconName = "";
    private boolean hasCoordinates = false;
    private boolean isVisible = false;
    private float x = 0.0f;
    private float y = 0.0f;
    private float rotation = 0.0f;

    private DcMotor shooter8605 = null;
    private int shooterPosition = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        collector.Initialize(hardwareMap, DcMotorSimple.Direction.REVERSE);
        color.Initialize(hardwareMap, true);
        deviceInterface.Initialize(hardwareMap, 5);
        engine.Initialize(hardwareMap);
        engine.SetSpeed(0.0d);
        lift.Initialize(hardwareMap);
        vuforia.Initialize(RSF_States.TeamColor.Red, VuforiaLicenseKey);

        shooter8605 = hardwareMap.dcMotor.get("SHOOT");
        shooter8605.setDirection(DcMotor.Direction.FORWARD);
        shooter8605.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter8605.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        vuforia.Activate();
        stage = 1;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            beacon = vuforia.LocateBeacon(beaconIndex);
            beaconName = beacon.Name();
            hasCoordinates = beacon.HasCoordinates();
            isVisible = beacon.IsVisible();
            x = beacon.X();
            y = beacon.Y();
            rotation = beacon.Rotation();

            shooterPosition = shooter8605.getCurrentPosition();
            Execute();

            telemetry.addData(beaconName, isVisible ? "Visible" : "Not Visible");
            telemetry.addData("X: ", hasCoordinates ? x : "None");
            telemetry.addData("Y: ", hasCoordinates ? y : "None");
            telemetry.addData("Rotation: ", hasCoordinates ? rotation : "None");
            telemetry.addData("Stage: ", stage);
            telemetry.addData("Time: ", time);
            telemetry.addData("Shooter: ", shooterPosition);

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

    public void Stage_1()
    {
        if (shooterPosition < -4400) {
            resetStartTime();

            shooter8605.setPower(0.0d);
            stage = 2;
        }
        else {
            shooter8605.setPower(0.50d);
        }
    }

    public void Stage_2()
    {
        if (time < 3.0d) {
            shooter8605.setPower(0.0d);
            collector.EnableForward();
        }
        else {
            resetStartTime();

            collector.Disable();
            stage = 3;
        }
    }

    public void Stage_3()
    {
        if (time < 1.0d) {
            shooter8605.setPower(0.50d);
        }
        else {
            resetStartTime();

            shooter8605.setPower(0.0d);
            stage = 4;
        }
    }

    public void Stage_4()
    {
        if (time < 0.60d) {
            engine.SetSpeed(1.0d);
            engine.Move(RSF_States.DPad.Up);
        }
        else {
            resetStartTime();

            stage = 5;
        }
    }

    public void Stage_5()
    {
        if (time < 0.5d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d));
        }
        else {
            resetStartTime();

            stage = 6;
        }
    }

    public void Stage_6()
    {
        if (time < 2.25d) {
            engine.SetSpeed(1.0d);
            engine.Move(RSF_States.DPad.Up);
        }
        else {
            resetStartTime();

            stage = 7;
        }
    }

    public void Stage_7() {
        if (time < 0.5d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d));
        }
        else {
            resetStartTime();

            stage = 8;
        }
    }

    public void Stage_8() {
        RSF_States.SensorColor beaconColor = color.Dectect();

        if (beaconColor == RSF_States.SensorColor.Red) {
            resetStartTime();

            engine.SetSpeed(0.0d);
            stage = 9;
        }
        else {
            engine.SetSpeed(0.25d);
            engine.Move(RSF_States.DPad.Up);
        }
    }
}


