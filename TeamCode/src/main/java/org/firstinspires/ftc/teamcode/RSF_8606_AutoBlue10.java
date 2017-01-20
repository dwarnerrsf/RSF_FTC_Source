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


@TeleOp(name="Pushbot: 8606 Auto Blue 10", group="Pushbot")
public class RSF_8606_AutoBlue10 extends RSF_BaseOp {
    private int stage = 0;
    private double speedModifier = 0.0d;
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
        speedModifier = 1.0d;

        engine.Initialize(hardwareMap);
        engine.SetSpeed(speedModifier);
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
        odsFront = hardwareMap.opticalDistanceSensor.get("ODSFRONT");
        odsBack = hardwareMap.opticalDistanceSensor.get("ODSBACK");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "RANGE");
        color.Initialize(hardwareMap, true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        stage = 1;
        speedModifier = 0.15d;
        flap.setPosition(0.45d);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            odsFront.enableLed(true);
            odsBack.enableLed(true);

            Execute();

            telemetry.addData("Stage: ", stage);
            telemetry.addData("ODS Front: ", odsFront.getRawLightDetected());
            telemetry.addData("ODS Back: ", odsBack.getRawLightDetected());
            telemetry.addData("Range: ", rangeSensor.rawOptical());
            telemetry.addData("LF: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
            telemetry.addData("LB: ", engine.GetEncoderPosition(RSF_States.EngineMotor.BackLeft));
            telemetry.addData("RF: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
            telemetry.addData("RB: ", engine.GetEncoderPosition(RSF_States.EngineMotor.BackRight));

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
            /*case 22:
                Stage_22();
                break;
            case 23:
                Stage_23();
                break;
            case 24:
                Stage_24();
                break;*/
            default:
                engine.Stop();
                break;
        }
    }

    public void Stage_1() {
        int target = (int)(Full_Rotation * 0.80f);
        shoot_1.setPower(-0.25d);
        shoot_2.setPower(-0.25d);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(0.25d);
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
        if (time < 1.0d) {
            engine.Stop();
        } else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 3;
        }
    }

    public void Stage_3() {
        if (time < 1.0d) {
            trigger.setPosition(0.375d);
        } else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 4;
        }
    }

    public void Stage_4() {
        if (time < 1.0d) {
            trigger.setPosition(0.0d);
        } else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 5;
        }
    }

    public void Stage_5() {
        if (time < 1.0d) {
            trigger.setPosition(0.375d);
        } else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 6;
        }
    }

    public void Stage_6() {
        int target = (int)(Full_Rotation * 1.0f);
        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.LeftTurn(0.60d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 7;
        }
    }

    public void Stage_7() {
        int target = (int)(Full_Rotation * 1.50);
        shoot_1.setPower(-0.25d);
        shoot_2.setPower(-0.25d);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 8;
        }
    }

    public void Stage_8() {
        int target = (int)(Full_Rotation * 1.50f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.LeftTurn(0.60d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 9;
        }
    }

    public void Stage_9() {
        int target = (int)(Full_Rotation * 1.30f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.SetSpeed(0.30d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 10;
        }
    }

    public void Stage_10() {
        if (odsFront.getRawLightDetected() < 2.0d && odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Left, 1.0d);
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
            engine.Move(RSF_States.DPad.Up, 0.40d);

            results = color.Detect();

            if (results == RSF_States.SensorColor.Red) {
                flap.setPosition(0.9375d);
            }
            else if (results == RSF_States.SensorColor.Blue) {
                flap.setPosition(0.0d);
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 12;
        }
    }

    public void Stage_12() {
        int target = -(int)(Full_Rotation * 3.0f);
        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.RighTurn(-0.60d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 13;
        }
    }

    public void Stage_13() {
        int target = -(int)(Full_Rotation * 2.5f);
        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.Move(RSF_States.DPad.Down, 1.0d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 50;
        }
    }

    public void Stage_14() {
        int target = (int)(Full_Rotation * 1.5f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) > target) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 50;
        }
    }

    /*public void Stage_12() {
        int target = -(int)(Full_Rotation * 0.60f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.Move(RSF_States.DPad.Down, 0.35d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 13;
        }
    }

    public void Stage_13() {
        results = RSF_States.SensorColor.None;
        flap.setPosition(0.45d);

        if (time < 2.0d) {
            engine.Move(RSF_States.DPad.Left, 1.0d);
        }
        else if (odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Left, 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 14;
        }
    }*/

    /*public void Stage_14() {
        if (odsFront.getRawLightDetected() < 2.0d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 16;
        }
    }*/

    public void Stage_15() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            if (results == RSF_States.SensorColor.Red) {
                flap.setPosition(0.9375d);
            }
            else if (results == RSF_States.SensorColor.Blue) {
                flap.setPosition(0.0d);
            }

            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 15;
        }
    }

    public void Stage_16() {
        if (rangeSensor.rawOptical() < 7) {
            engine.Move(RSF_States.DPad.Up, 0.55d);
            results = color.Detect();

            if (results == RSF_States.SensorColor.Red) {
                flap.setPosition(0.9375d);
            }
            else if (results == RSF_States.SensorColor.Blue) {
                flap.setPosition(0.0d);
            }
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 17;
        }
    }

    public void Stage_17() {
        int target = -(int)(Full_Rotation * 0.50f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.Move(RSF_States.DPad.Down, 0.50d);

            shoot_1.setPower(-0.25d);
            shoot_2.setPower(-0.25d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 18;
        }
    }

    public void Stage_18() {
        int target = -(int)(Full_Rotation * 1.35f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.RighTurn(-0.60d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 19;
        }
    }

    public void Stage_19() {
        int target = -(int)(Full_Rotation * 2.90f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) > target) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 20;
        }
    }

    public void Stage_20() {
        int target = (int)(Full_Rotation * 0.80f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(RSF_States.DPad.Up, 0.35d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 2;
        }
    }

    public void Stage_21() {
        int target = (int)(Full_Rotation * 3.0f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.Move(RSF_States.DPad.Up, 0.75d);

            telemetry.addData("Target: ", target);
            telemetry.addData("Current: ", engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight));
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 99;
        }
    }

    /*public void Stage_3() {
        int target = -(int)(Full_Rotation * 3.5f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) > target) {
            engine.SetSpeed(0.50d);
            engine.LeftTurn();

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
            engine.Move(RSF_States.DPad.Left, 1.0d);
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
            engine.Move(RSF_States.DPad.Left, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 8;
        }
    }

    public void Stage_8() {
        if (odsFront.getRawLightDetected() < 2.0d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        }
        else if (odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 9;
        }
    }

    public void Stage_9() {
        if (rangeSensor.rawOptical() < 3) {
            engine.Move(RSF_States.DPad.Down, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 10;
        }
    }

    public void Stage_10() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 11;
        }
    }

    public void Stage_11() {
        int target = (int)(Full_Rotation * 0.20f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontRight) < target) {
            engine.SetSpeed(0.50d);
            engine.MoveTo(target);

            telemetry.addData("Target: ", target);

            if (results == RSF_States.SensorColor.Red) {
                if (!pusherEnabled) {
                    rightPusher.setPosition(0.30d);
                    pusherEnabled = true;
                }
            }
            else if (results == RSF_States.SensorColor.Blue) {
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
            stage = 12;
        }
    }

    public void Stage_12() {
        if (rangeSensor.rawOptical() < 8) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 13;
        }
    }

    public void Stage_13() {
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
            stage = 50;
        }
    }

    public void Stage_14() {
        int target = -(int)(Full_Rotation * 1.50f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) > target) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            stage = 15;
        }
    }

    public void Stage_15() {
        if (time > 1.0d)
        {
            if (odsBack.getRawLightDetected() < 2.0d) {
                engine.Move(RSF_States.DPad.Up, 0.50d);
            }
            else {
                resetStartTime();
                engine.ResetEncoders();
                engine.Stop();
                stage = 16;
            }
        }
        else {
            engine.Move(RSF_States.DPad.Up, 0.50d);

        }
    }

    public void Stage_16() {
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
            stage = 17;
        }
    }

    public void Stage_17() {
        int target = (int)(Full_Rotation * 1.60f);

        if (engine.GetEncoderPosition(RSF_States.EngineMotor.FrontLeft) < target) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            stage = 18;
        }
    }

    public void Stage_18() {
        if (odsFront.getRawLightDetected() < 2.0d && odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(RSF_States.DPad.Left, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 19;
        }
    }

    public void Stage_19() {
        if (odsFront.getRawLightDetected() < 2.0d) {
            engine.Move(new RSF_Joysticks(1.0d, -1.0d), 1.0d);
        }
        else if (odsBack.getRawLightDetected() < 2.0d) {
            engine.Move(new RSF_Joysticks(-1.0d, 1.0d), 1.0d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 20;
        }
    }

    public void Stage_20() {
        if (rangeSensor.rawOptical() < 3) {
            engine.Move(RSF_States.DPad.Down, 0.25d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 21;
        }
    }

    public void Stage_21() {
        results = color.Detect();

        if (results != RSF_States.SensorColor.None) {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 22;

            pusherEnabled = false;
        }
    }

    public void Stage_22() {
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
            stage = 23;
        }
    }

    public void Stage_23() {
        if (rangeSensor.rawOptical() < 8) {
            engine.Move(RSF_States.DPad.Down, 0.50d);
        }
        else {
            resetStartTime();
            engine.ResetEncoders();
            engine.Stop();
            stage = 24;
        }
    }

    public void Stage_24() {
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
            stage = 26;
        }
    }*/
}


