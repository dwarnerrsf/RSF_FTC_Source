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

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Pushbot: 8606 Red v3", group="Pushbot")
public class RSF_8606_AutoRed3 extends RSF_BaseOp {
    private boolean moveFlap = false;
    private boolean useColor = false;
    private boolean useVuforia = false;
    private int beaconIndex = 0;
    private int stage = 0;
    private final float Full_Rotation = 1478.4f;
    private String colorOutput = "";

    private DcMotor shoot_1 = null;
    private DcMotor shoot_2 = null;

    private Servo flap = null;
    private Servo trigger = null;

    private OpticalDistanceSensor odsFront = null;
    private OpticalDistanceSensor odsBack = null;

    private ModernRoboticsI2cRangeSensor rangeSensor = null;

    RSF_States.SensorColor results = RSF_States.SensorColor.None;
    RSF_Beacon _coordinates = null;

    @Override
    public void runOpMode() throws InterruptedException {
        engine.Initialize(hardwareMap);
        engine.SetSpeed(1.0d);
        engine.SetEngineMode(RSF_States.Encoders.On);

        lift.Initialize(hardwareMap);
        lift.SetActivePower(1.0d);

        collector.Initialize(hardwareMap);
        lift.SetActivePower(1.0d);

        shoot_1 = hardwareMap.dcMotor.get("SHOOT1");
        shoot_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot_1.setPower(0.0d);

        shoot_2 = hardwareMap.dcMotor.get("SHOOT2");
        shoot_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot_2.setPower(0.0d);

        flap = hardwareMap.servo.get("FLAP");
        flap.setPosition(0.0d);

        trigger = hardwareMap.servo.get("TRIGGER");
        trigger.setPosition(0.0d);

        deviceInterface.Initialize(hardwareMap, 5);
        color.Initialize(hardwareMap, true);
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RANGE");

        odsFront = hardwareMap.opticalDistanceSensor.get("ODSFRONT");
        odsFront.enableLed(true);

        odsBack = hardwareMap.opticalDistanceSensor.get("ODSBACK");
        odsBack.enableLed(true);

        vuforia.Initialize(RSF_States.TeamColor.Red, VuforiaKeys.Josh);
        vuforia.Activate();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        beaconIndex = 3;
        flap.setPosition(0.0d);
        trigger.setPosition(0.0d);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            GetColor();
            GetFlapper();
            GetCoordinates();
            Execute();

            telemetry.addData("Stage: ", stage);

            if (_coordinates != null && useVuforia) {
                telemetry.addData(_coordinates.Name, _coordinates.IsVisible ? "Visible" : "Not Visible");
                telemetry.addData("Stale Data: ", _coordinates.Stale ? "Yes" : "No");
                telemetry.addData("Coordinates: ", _coordinates.HasCoordinates ? "Yes" : "No");
                telemetry.addData("X: ", _coordinates.X);
                telemetry.addData("Y: ", _coordinates.Y);
                telemetry.addData("Rotation: ", _coordinates.Rotation);
            }

            telemetry.addData("Color: ", colorOutput);
            telemetry.addData("ODS Front: ", odsFront.getRawLightDetected());
            telemetry.addData("ODS Back: ", odsBack.getRawLightDetected());
            telemetry.addData("Range: ", rangeSensor.rawOptical());

            Update(5);
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
                break;
            case 14:
                Stage_14();
                break;
            case 15:
                Stage_15();
                break;
            case 16:
                Stage_16();
                break;
            case 17:
                Stage_17();
                break;
            case 18:
                Stage_18();
                break;
            case 19:
                Stage_19();
                break;
            case 20:
                Stage_20();
                break;
            case 21:
                Stage_21();
                break;
            case 22:
                Stage_22();
                break;
            case 23:
                Stage_23();
                break;
            case 24:
                Stage_24();
                break;
            case 25:
                Stage_25();
                break;
            default:
                engine.Stop();
                shoot_1.setPower(0.0d);
                shoot_2.setPower(0.0d);
                break;
        }
    }

    public void GetColor() {
        if (useColor) {
            results = color.Detect();
            moveFlap = true;

            switch (results) {
                case Blue:
                    colorOutput = "Blue";
                    break;
                case Red:
                    colorOutput = "Red";
                    break;
                default:
                    colorOutput = "None";
                    break;
            }
        }
    }

    public void GetFlapper() {
        if (moveFlap) {
            if (results == RSF_States.SensorColor.Red) {
                flap.setPosition(0.9375d);
            } else if (results == RSF_States.SensorColor.Blue) {
                flap.setPosition(0.0d);
            }
        }
    }

    public void GetCoordinates() {
        if (useVuforia) {
            _coordinates = vuforia.GetCoordinates(beaconIndex);
        }
    }

    public void Next(int nextStage) {
        resetStartTime();
        engine.ResetEncoders();
        engine.Stop();
        stage = nextStage;
    }

    public void Stage_1() {
        int target = (int) (Full_Rotation * 4.0f);
        shoot_1.setPower(-0.25d);
        shoot_2.setPower(-0.25d);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(1.0d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            useVuforia = true;
            Next(2);
        }
    }

    public void Stage_2() {
        int target = (int) (Full_Rotation * 0.75f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.20d);

            telemetry.addData("Target: ", target);
        } else {
            Next(3);
        }
    }

    public void Stage_3() {
        if (_coordinates.Rotation < 88.0f) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.10d);
        } else if (_coordinates.Rotation > 92.0f) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.10d);
        } else {
            useColor = true;
            Next(5);
        }
    }

    public void Stage_4() {
        if (_coordinates.X < 0.0f) {
            engine.Move(RSF_States.DPad.Right, 0.10d);
        } else if (_coordinates.X > 40.0f) {
            engine.Move(RSF_States.DPad.Left, 0.10d);
        } else {
            Next(5);
        }
    }

    public void Stage_5() {
        if (_coordinates.Y < 1300.0f) {
            engine.Move(RSF_States.DPad.Up, 0.30d);
        } else {
            Next(6);
        }
    }

    public void Stage_6() {
        if (_coordinates.Rotation < 88.0f) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.10d);
        } else if (_coordinates.Rotation > 92.0f) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.10d);
        } else {
            results = RSF_States.SensorColor.None;
            moveFlap = false;
            useColor = false;
            Next(8);
        }
    }

    public void Stage_7() {
        if (time < 0.35f) {
            engine.Stop();
        } else {
            moveFlap = false;
            useColor = false;
            Next(8);
        }
    }

    public void Stage_8() {
        if (_coordinates.Y < 1465.0f) {
            engine.Move(RSF_States.DPad.Up, 0.35d);
        } else {
            Next(9);
        }
    }

    public void Stage_9() {
        if (_coordinates.Y > 1200.0f) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        } else {
            beaconIndex = 1;
            Next(10);
        }
    }

    public void Stage_10() {
        int target = (int) (Full_Rotation * 3.80f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.Move(RSF_States.DPad.Right, 1.0d);

            telemetry.addData("Target: ", target);
        } else {
            Next(12);
        }
    }

    public void Stage_11() {
        if (_coordinates.Rotation < 88.0f) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.10d);
        } else if (_coordinates.Rotation > 92.0f) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.10d);
        } else {
            useColor = true;
            Next(12);
        }
    }

    public void Stage_12() {
        useColor = true;
        moveFlap = true;

        if (_coordinates.X > -1785.0f) {
            engine.Move(RSF_States.DPad.Left, 0.10d);
        } else if (_coordinates.X < -1815.0f) {
            engine.Move(RSF_States.DPad.Right, 0.10d);
        } else {
            Next(13);
        }
    }

    public void Stage_13() {
        useColor = true;
        moveFlap = true;

        if (_coordinates.Rotation < 88.0f) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.10d);
        } else if (_coordinates.Rotation > 92.0f) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.10d);
        } else {
            Next(14);
        }
    }

    public void Stage_14() {
        useColor = true;
        moveFlap = true;

        if (_coordinates.Y < -475.0f) {
            engine.Move(RSF_States.DPad.Up, 0.30d);
        } else {
            Next(15);
        }
    }

    public void Stage_15() {
        useColor = true;
        moveFlap = true;

        if (_coordinates.Rotation < 88.0f) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.10d);
        } else if (_coordinates.Rotation > 92.0f) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.10d);
        } else {
            /*moveFlap = false;
            useColor = false;*/
            Next(17);
        }
    }

    public void Stage_16() {
        if (time < 0.35f) {
            engine.Stop();
        } else {
            moveFlap = false;
            useColor = false;
            Next(17);
        }
    }

    public void Stage_17() {
        if (_coordinates.Y < -300.0f) {
            engine.Move(RSF_States.DPad.Up, 0.35d);
        } else {
            moveFlap = false;
            useColor = false;
            Next(18);
        }
    }

    public void Stage_18() {
        if (_coordinates.Y > -400.0f) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        } else {
            useVuforia = false;
            Next(19);
        }
    }

    public void Stage_19() {
        int target = (int) (Full_Rotation * 1.68f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);

            telemetry.addData("Target: ", target);
        } else {
            Next(20);
        }
    }

    public void Stage_20() {
        int target = (int) (Full_Rotation * 0.40f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(1.0d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            Next(21);
        }
    }

    public void Stage_21() {
        if (time < 0.75d) {
            trigger.setPosition(0.375d);
        } else {
            Next(22);
        }
    }

    public void Stage_22() {
        if (time < 0.75d) {
            trigger.setPosition(0.0d);
        } else {
            Next(23);
        }
    }

    public void Stage_23() {
        if (time < 0.75d) {
            trigger.setPosition(0.375d);
        } else {
            Next(25);
        }
    }

    public void Stage_24() {
        if (time < 1.0d) {
            trigger.setPosition(0.0d);
        } else {
            Next(100);
        }
    }

    public void Stage_25() {
        int target = (int) (Full_Rotation * 2.45f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(1.0d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            Next(26);
        }
    }
}


