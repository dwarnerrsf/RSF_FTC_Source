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


@TeleOp(name="Pushbot: 7696 Auto Red 3", group="Pushbot")
@Disabled
public class RSF_7696_AutoRed3 extends RSF_BaseOp {
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

    private double moveSpeed = 0.0d;

    private Servo leftPusher = null;
    private Servo rightPusher = null;

    @Override
    public void runOpMode() throws InterruptedException {
        /*collector.Initialize(hardwareMap, DcMotorSimple.Direction.REVERSE);
        color.Initialize(hardwareMap, false );
        deviceInterface.Initialize(hardwareMap, 5);
        engine.Initialize(hardwareMap, RSF_States.Encoders.On);
        engine.SetSpeed(moveSpeed);
        lift.Initialize(hardwareMap);
        shooter.Initialize(hardwareMap);
        vuforia.Initialize(RSF_States.TeamColor.Red, VuforiaLicenseKey);

        leftPusher = hardwareMap.servo.get("LEFTPUSH");
        leftPusher.setPosition(0.0d);

        rightPusher = hardwareMap.servo.get("RIGHTPUSH");
        rightPusher.setPosition(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        vuforia.Activate();
        stage = 1;
        moveSpeed = 0.15d;

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
            telemetry.addData("Encoder LF: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
            telemetry.addData("Encoder LB: ", engine.GetEncoderPosition(RSF_States.EngineMotor.BackLeft));
            telemetry.addData("Encoder RF: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
            telemetry.addData("Encoder RB: ", engine.GetEncoderPosition(RSF_States.EngineMotor.BackRight));

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
            /*case 8:
                Stage_8();
                break;
            case 9:
                Stage_9();
                break;
            case 10:
                Stage_10();
                break;
            case 11:
                Stage_11();
                break;
            case 12:
                Stage_12();
                break;
            case 13:
                Stage_13();
                break;*/
            default:
                engine.Stop();
                break;
        }
    }

    public void Stage_1() {
        if (time < 0.80d) {
            engine.Move(RSF_States.DPad.Down, moveSpeed);
        } else {
            resetStartTime();
            engine.Stop();
            stage = 2;
        }
    }

    public void Stage_2() {
        if (time < 7.0d) {
            shooter.EnableForward();
        } else {
            resetStartTime();
            shooter.Disable();
            stage = 3;
        }
    }

    public void Stage_3() {
        if (time < 2.50d) {
            engine.Move(RSF_States.DPad.Right, moveSpeed);
        } else {
            resetStartTime();
            engine.Stop();
            stage = 4;
        }
    }

    public void Stage_4() {
        if (time < 0.60d) {
            moveSpeed = 1.0d;
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), moveSpeed);
        }
        else {
            resetStartTime();
            engine.Stop();
            stage = 5;
        }
    }

    public void Stage_5() {
        if (time < 1.65d) {
            engine.Move(RSF_States.DPad.Down, moveSpeed);
        } else {
            resetStartTime();
            engine.Stop();
            stage = 6;
        }
    }

    public void Stage_6() {
        if (time < 0.55d) {
            moveSpeed = 1.0d;
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), moveSpeed);
        }
        else {
            resetStartTime();
            engine.Stop();
            stage = 7;
        }
    }

    public void Stage_7() {
        if (x > -1300.0f) {
            if (hasCoordinates) {
                if (rotation < -3.0f) {
                    engine.Move(new RSF_Joysticks(0.40d, 0.10d));
                }
                else if (rotation > 3.0f) {
                    engine.Move(new RSF_Joysticks(0.10d, 0.40d));
                }
                else {
                    engine.Move(new RSF_Joysticks(0.25d, 0.25d));
                }
            }
        }
        else {
            stage = 8;
        }
    }

    /*public void Stage_7() {
        if (time < 0.85d) {
            engine.Move(RSF_States.DPad.Right, moveSpeed);
        }
        else {
            resetStartTime();
            engine.Stop();
            stage = 8;
        }
    }

    public void Stage_8() {
        if (!hasCoordinates) {
            engine.Stop();
        }
        else {
            resetStartTime();
            engine.Stop();
            stage = 9;
        }
    }

    public void Stage_9() {
        if (hasCoordinates) {
            if (y < -20.0f) {
                engine.Move(RSF_States.DPad.Right, 0.80f);
            }
            else if (y > 20.0f) {
                engine.Move(RSF_States.DPad.Left, 0.80f);
            }
            else {
                resetStartTime();
                engine.Stop();
                stage = 10;
            }
        }
    }

    public void Stage_10() {
        if (x > -1200.0f) {
            engine.Move(new RSF_Joysticks(-0.15d, -0.15d), 0.15d);
        }
        else {
            resetStartTime();
            engine.Stop();
            stage = 11;
        }
    }

    public void Stage_11()
    {
        if (hasCoordinates) {
            if (rotation < -3.0f) {
                engine.Move(new RSF_Joysticks(0.20d, -0.20d), 0.20d);
            }
            else if (rotation > 3.0f) {
                engine.Move(new RSF_Joysticks(-0.20d, 0.20d), 0.20d);
            }
            else {
                resetStartTime();
                engine.Stop();
                stage = 12;
            }
        }
    }

    public void Stage_12() {
        if (hasCoordinates) {
            if (y < -20.0f) {
                engine.Move(RSF_States.DPad.Right, 0.80f);
            }
            else if (y > 20.0f) {
                engine.Move(RSF_States.DPad.Left, 0.80f);
            }
            else {
                resetStartTime();
                engine.Stop();
                stage = 13;
            }
        }
    }

    public void Stage_13() {
        engine.Stop();
        RSF_States.SensorColor beaconColor = color.Detect();
        String _color = "";

        if (beaconColor == RSF_States.SensorColor.Red) {
            _color = "Red";
            leftPusher.setPosition(0.750d);
        }
        else if (beaconColor == RSF_States.SensorColor.Blue) {
            _color = "Blue";
            rightPusher.setPosition(0.75d);
        }
        else {
            _color = "None";
        }

        telemetry.addData("Color: ", _color);
    }*/
}


