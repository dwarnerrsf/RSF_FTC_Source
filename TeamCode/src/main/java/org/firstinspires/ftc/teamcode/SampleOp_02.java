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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="SampleOp 02", group="Pushbot")
public class SampleOp_02 extends OpMode {
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        frontLeft = InitializeMotor("frontLeft");
        frontRight = InitializeMotor("frontRight");
        backLeft = InitializeMotor("backLeft", DcMotor.Direction.REVERSE);
        backRight = InitializeMotor("backRight", DcMotor.Direction.REVERSE);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;

        SetMotorPower(left, right);

        telemetry.addData("Left Stick: ", left);
        telemetry.addData("Right Stick: ", right);
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        SetMotorPower(0.0d);
    }

    private DcMotor InitializeMotor(String motorName) {
        return InitializeMotor(motorName, DcMotor.Direction.FORWARD);
    }

    private DcMotor InitializeMotor(String motorName, DcMotor.Direction direction) {
        return InitializeMotor(motorName, direction, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private DcMotor InitializeMotor(String motorName, DcMotor.Direction direction, DcMotor.RunMode runMode) {
        DcMotor motor = hardwareMap.dcMotor.get(motorName);
        motor.setDirection(direction);
        motor.setMode(runMode);

        return motor;
    }

    private void SetMotorPower(double power) {
        SetMotorPower(power, power);
    }

    private void SetMotorPower(double left, double right) {
        SetMotorPower(left, left, right, right);
    }

    private void SetMotorPower(double back_Left, double front_Left, double back_Right, double front_Right) {
        backLeft.setPower(back_Left);
        frontLeft.setPower(front_Left);
        backRight.setPower(back_Right);
        frontRight.setPower(front_Right);
    }
}
