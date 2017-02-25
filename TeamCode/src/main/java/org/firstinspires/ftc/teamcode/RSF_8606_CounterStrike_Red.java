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


@TeleOp(name="Pushbot: 8606 CS Blue", group="Pushbot")
public class RSF_8606_CounterStrike_Red extends RSF_BaseOp {
    private int beaconIndex = 0;
    private int stage = 0;
    private final float Full_Rotation = 1478.4f;

    private DcMotor shoot_1 = null;
    private DcMotor shoot_2 = null;

    private Servo flap = null;
    private Servo trigger = null;

    private OpticalDistanceSensor odsFront = null;
    private OpticalDistanceSensor odsBack = null;

    private ModernRoboticsI2cRangeSensor rangeSensor = null;

    RSF_States.SensorColor results = RSF_States.SensorColor.None;

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

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        beaconIndex = 3;
        flap.setPosition(0.0d);
        trigger.setPosition(0.0d);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            Execute();

            telemetry.addData("Stage: ", stage);

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
            default:
                engine.Stop();
                shoot_1.setPower(0.0d);
                shoot_2.setPower(0.0d);
                break;
        }
    }

    public void Next(int nextStage) {
        resetStartTime();
        engine.ResetEncoders();
        engine.Stop();
        stage = nextStage;
    }

    public void Stage_1() {
        int target = (int) (Full_Rotation * 1.70f);
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
            shoot_1.setPower(0.0d);
            shoot_2.setPower(0.0d);
            Next(6);
        }
    }

    public void Stage_6() {
        if (time < 5.50d) {
        } else {
            Next(7);
        }
    }

    public void Stage_7() {
        int target = (int) (Full_Rotation * 0.65f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.20d);

            telemetry.addData("Target: ", target);
        } else {
            Next(8);
        }
    }

    public void Stage_8() {
        int target = (int) (Full_Rotation * 6.35f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target && time < 5.0f) {
            engine.SetSpeed(1.0d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            Next(9);
        }
    }

    public void Stage_9() {
        if (time < 12.0d) {
        } else {
            Next(10);
        }
    }

    public void Stage_10() {
        int target = (int) -(Full_Rotation * 2.0f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) > target) {
            engine.SetSpeed(1.0d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        } else {
            Next(11);
        }
    }
}


