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


@TeleOp(name="Pushbot: 8605 Auto 5", group="Pushbot")
public class RSF_8605_Auto5 extends RSF_BaseOp {
    private final String VuforiaLicenseKey = "AZjwTlP/////AAAAGQ0Nw67MdEzAm0Cuvb+oJGOCSFyqWb6WplQDZyJo9vOTw0iLTPRXEXyjZ/WAN+V4wKPlbTv0pbPad2yBRZ3vyHl4hRafu7wJGLMgzfsTeozU4SzDgl378Q3zGZsJWK+UkfmekmXkzeysQqnkL0rZK+1KNaARZjbR14/OONP2j9AjqOoJa6yMMMN/2jgM5x/Lshv/++vTyHUzJYs7rEHh26FGDtlE9J8FoRXaNPrii2i3j2msG2bTO0AzrlwnK6AMtP92bqRGHnIvU4GQVnMgi2fn/EoxNRLiNktloDfdzv9vRaU3zgpOENxmqzkfdBskZzfy6EYGsEzMQPtJ07brBfDhC0l9w4kvuBi2i5dK7Mbo";
    int beaconIndex = 3;
    int stage = 0;

    RSF_Beacon beacon = null;
    String beaconName = "";
    boolean hasCoordinates = false;
    boolean isVisible = false;
    float x = 0.0f;
    float y = 0.0f;
    float rotation = 0.0f;

    @Override
    public void runOpMode() throws InterruptedException {
        /*vuforia.Initialize(RSF_States.TeamColor.Blue, VuforiaLicenseKey);
        engine.SetSpeed(0.15d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        vuforia.Activate();
        stage = 1;

        resetStartTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            beacon = vuforia.LocateBeacon(beaconIndex);
            beaconName = beacon.Name();
            hasCoordinates = beacon.HasCoordinates();
            isVisible = beacon.IsVisible();
            x = beacon.X();
            y = beacon.Y();
            rotation = beacon.Rotation();

            Execute();

            telemetry.addData(beaconName, isVisible ? "Visible" : "Not Visible");
            telemetry.addData("X: ", hasCoordinates ? x : "None");
            telemetry.addData("Y: ", hasCoordinates ? y : "None");
            telemetry.addData("Rotation: ", hasCoordinates ? rotation : "None");
            telemetry.addData("Stage: ", stage);
            telemetry.addData("Time: ", time);

            Update(40);
        }*/
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
            default:
                engine.Stop();
                break;
        }
    }

    public void Stage_1()
    {
        if (time < 3.5d) {
            if (isVisible) {
                stage = 2;
            }
            else {
                engine.SetSpeed(0.30d);
                engine.Move(RSF_States.DPad.Right);
            }
        }
        else {
            stage = 2;
            engine.Stop();
        }
    }

    public void Stage_2() {
        if (hasCoordinates) {
            if (y <= 1600.0f) {
                if (rotation < -3.0f) {
                    engine.Move(new RSF_Joysticks(0.05d, 0.20d));
                }
                else if (rotation > 3.0f) {
                    engine.Move(new RSF_Joysticks(0.20d, 0.05d));
                }
                else {
                    engine.Move(new RSF_Joysticks(0.20d, 0.20d));
                }
            }
            else {
                stage = 3;
            }
        }
    }

    public void Stage_3() {
        if (time < 1.0d) {
            if (hasCoordinates) {
                if (x < -230.0f) {
                    engine.Move(RSF_States.DPad.Left);
                }
                else if (x > -210.0f) {
                    engine.Move(RSF_States.DPad.Right);
                }
                else {
                    engine.Stop();
                    resetStartTime();
                    stage = 4;
                }
            }
        }
        else {
            engine.Stop();
            resetStartTime();
            stage = 4;
        }
    }

    public void Stage_4() {
        resetStartTime();
        stage = 5;
    }

    public void Stage_5() {
        if (time > 5.0d) {
            stage = (beaconIndex == 0) ? 6 : 8;
        }
        else {
            engine.Stop();
        }
    }

    public void Stage_6() {
        if (hasCoordinates)
        {
            if (y > 900.0f) {
                engine.Move(RSF_States.DPad.Down);
            }
            else {
                resetStartTime();
                beaconIndex = 2;
                stage = 7;
            }
        }
    }

    public void Stage_7() {
        if (time < 3.5d) {
            if (isVisible) {
                stage = 2;
            }
            else {
                engine.SetSpeed(0.30d);
                engine.Move(RSF_States.DPad.Right);
            }
        }
        else {
            stage = 2;
            engine.Stop();
        }
    }
}
