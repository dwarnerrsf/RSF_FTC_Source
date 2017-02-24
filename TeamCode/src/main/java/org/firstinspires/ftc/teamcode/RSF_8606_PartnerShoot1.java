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


@TeleOp(name="Pushbot: 8606 PS 1", group="Pushbot")
public class RSF_8606_PartnerShoot1 extends RSF_BaseOp {
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
        //vuforia.Activate();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        beaconIndex = 3;
        flap.setPosition(0.0d);
        trigger.setPosition(0.0d);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            /*GetColor();
            GetFlapper();
            GetCoordinates();*/
            Execute();

            telemetry.addData("Stage: ", stage);

            /*if (_coordinates != null && useVuforia) {
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
            telemetry.addData("Range: ", rangeSensor.rawOptical());*/

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
        int target = (int) (Full_Rotation * 1.80f);
        shoot_1.setPower(-0.25d);
        shoot_2.setPower(-0.25d);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            Next(2);
        }
    }

    public void Stage_2() {
        if (time < 0.75d) {
            trigger.setPosition(0.375d);
        } else {
            Next(3);
        }
    }

    public void Stage_3() {
        if (time < 0.75d) {
            trigger.setPosition(0.0d);
        } else {
            Next(4);
        }
    }

    public void Stage_4() {
        if (time < 0.75d) {
            trigger.setPosition(0.375d);
        } else {
            Next(5);
        }
    }

    public void Stage_5() {
        if (time < 1.0d) {
            trigger.setPosition(0.0d);
        } else {
            Next(6);
        }
    }
}


