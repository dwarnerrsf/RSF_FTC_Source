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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Pushbot: 7696 Auto Red 8", group="Pushbot")
public class RSF_7696_AutoRed8 extends RSF_BaseOp {
    private int stage = 0;
    private double moveSpeed = 0.0d;
    private final int Full_Rotation = 1120;

    private Servo leftPusher = null;
    private Servo rightPusher = null;

    private OpticalDistanceSensor odsFront = null;
    private OpticalDistanceSensor odsBack = null;

    private ModernRoboticsI2cRangeSensor rangeSensor = null;

    private DcMotor lift_1 = null;
    private DcMotor lift_2 = null;

    boolean pusherEnabled = false;

    RSF_States.SensorColor results = RSF_States.SensorColor.None;

    @Override
    public void runOpMode() throws InterruptedException {
        collector.Initialize(hardwareMap, DcMotorSimple.Direction.REVERSE);
        color.Initialize(hardwareMap, false );
        deviceInterface.Initialize(hardwareMap, 5);
        engine.Initialize(hardwareMap, RSF_States.Encoders.On);
        engine.SetSpeed(moveSpeed);
        shooter.Initialize(hardwareMap);

        leftPusher = hardwareMap.servo.get("LEFTPUSH");
        leftPusher.setPosition(0.0d);

        rightPusher = hardwareMap.servo.get("RIGHTPUSH");
        rightPusher.setPosition(0.0d);

        odsFront = hardwareMap.opticalDistanceSensor.get("ODSFRONT");
        odsBack = hardwareMap.opticalDistanceSensor.get("ODSBACK");

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RANGE");

        lift_1 = hardwareMap.dcMotor.get("LIFT1");
        lift_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_1.setPower(0.0d);

        lift_2 = hardwareMap.dcMotor.get("LIFT2");
        lift_2.setDirection(DcMotorSimple.Direction.REVERSE);
        lift_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_2.setPower(0.0d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        moveSpeed = 0.15d;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            odsFront.enableLed(true);
            odsBack.enableLed(true);

            Execute();

            telemetry.addData("Stage: ", stage);
            telemetry.addData("ODS Front: ", odsFront.getRawLightDetected());
            telemetry.addData("ODS Back: ", odsBack.getRawLightDetected());

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
        if (time < 5.0d) {
            shooter.EnableForward();
        } else {
            resetStartTime();

            engine.ResetEncoders();
            engine.Stop();

            shooter.Disable();
            stage = 3;
        }
    }

    public void Stage_3() {
        int target = -(int)(Full_Rotation * 3.5f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.SetSpeed(0.50d);
            engine.TestTurn();

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 4;
        }
    }

    public void Stage_4() {
        int target = -(int)(Full_Rotation * 0.75f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 5;
        }
    }

    public void Stage_5() {
        if (time < 1.20d) {
            engine.Move(RSF_States.DPad.Right, 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 6;
        }
    }

    public void Stage_6() {
        int target = -(int)(Full_Rotation * 1.30f);

        if (engine.GetAverageEncoderPosition() > target) {
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

    public void Stage_7() {
        if (odsFront.getRawLightDetected() < 2.0d && odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Right, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 8;
        }
    }

    public void Stage_8() {
        if (rangeSensor.rawOptical() < 3) {
            engine.Move(RSF_States.DPad.Down, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 9;
        }
    }

    public void Stage_9() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 10;
        }
    }

    public void Stage_10() {
        int target = (int)(Full_Rotation * 0.20f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);

            if (results == RSF_States.SensorColor.Blue) {
                if (!pusherEnabled) {
                    rightPusher.setPosition(0.30d);
                    pusherEnabled = true;
                }
            }
            else if (results == RSF_States.SensorColor.Red) {
                if (!pusherEnabled) {
                    leftPusher.setPosition(0.30d);
                    pusherEnabled = true;
                }
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 11;
        }
    }

    public void Stage_11() {
        if (rangeSensor.rawOptical() < 8) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 12;
        }
    }

    public void Stage_12() {
        int target = (int)(Full_Rotation * 0.65f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 13;
        }
    }

    public void Stage_13() {
        int target = -(int)(Full_Rotation * 1.70f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            stage = 14;
        }
    }

    public void Stage_14() {
        if (time > 1.0d)
        {
            if (odsBack.getRawLightDetected() < 2.0d) {
                engine.Move(RSF_States.DPad.Up, 0.50d);
            }
            else {
                resetStartTime();
                engine.ResetEncoders();
                engine.Stop();
                stage = 15;
            }
        }
        else {
            engine.Move(RSF_States.DPad.Up, 0.50d);

        }
    }

    public void Stage_15() {
        int target = -(int)(Full_Rotation * 0.35f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 16;
        }
    }

    public void Stage_16() {
        int target = (int)(Full_Rotation * 1.60f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            stage = 17;
        }
    }

    public void Stage_17() {
        if (odsFront.getRawLightDetected() < 2.0d && odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Right, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 18;
        }
    }

    public void Stage_18() {
        if (rangeSensor.rawOptical() < 3) {
            engine.Move(RSF_States.DPad.Down, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 19;
        }
    }

    public void Stage_19() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 20;

            pusherEnabled = false;
        }
    }

    public void Stage_20() {
        int target = (int)(Full_Rotation * 0.20f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);

            if (results == RSF_States.SensorColor.Blue) {
                if (!pusherEnabled) {
                    rightPusher.setPosition(0.40d);
                    pusherEnabled = true;
                }
            }
            else if (results == RSF_States.SensorColor.Red) {
                if (!pusherEnabled) {
                    leftPusher.setPosition(0.40d);
                    pusherEnabled = true;
                }
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 21;
        }
    }

    public void Stage_21() {
        if (rangeSensor.rawOptical() < 8) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 22;
        }
    }

    public void Stage_22() {
        int target = (int)(Full_Rotation * 0.25f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 23;
        }
    }

    /*public void Stage_15() {
        if (rangeSensor.rawOptical() < 3) {
            engine.Move(RSF_States.DPad.Down, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 16;
        }
    }*/

    /*public void Stage_16() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 17;

            pusherEnabled = false;
        }
    }*/

    /*public void Stage_17() {
        int target = (int)(Full_Rotation * 0.65f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);

            if (results == RSF_States.SensorColor.Blue) {
                if (!pusherEnabled) {
                    rightPusher.setPosition(0.36d);
                    pusherEnabled = true;
                }
            }
            else if (results == RSF_States.SensorColor.Red) {
                if (!pusherEnabled) {
                    leftPusher.setPosition(0.36d);
                    pusherEnabled = true;
                }
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 18;
        }
    }*/

    /*public void Stage_18() {
        if (front && !back) {
            if (odsBack.getRawLightDetected() < 1.0d) {
                engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 0.45d);
            }
            else {
                back = true;
                engine.Stop();
            }
            engine.Move(RSF_States.DPad.Right, 0.25d);
        }
        else if (back && !front) {
            if (odsFront.getRawLightDetected() < 1.0d) {
                engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.45d);
            }
            else {
                front = true;
                engine.Stop();
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 19;
        }

        if (time < 0.30d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 0.45d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 19;
        }
    }*/

    /*public void Stage_19() {
        if (rangeSensor.rawOptical() < 8) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 20;
        }
    }*/
}


