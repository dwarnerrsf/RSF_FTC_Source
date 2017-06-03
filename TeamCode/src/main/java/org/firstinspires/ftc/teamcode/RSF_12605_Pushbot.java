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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="12605_Pushbot", group="Pushbot")
public class RSF_12605_Pushbot extends LinearOpMode {
    private DcMotor backLeft = null;
    private DcMotor frontLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontRight = null;

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();

        Stop();

        waitForStart();

        while (opModeIsActive()) {
            double left = RSF_Utilities.Clamp(-gamepad1.left_stick_y);
            double right = RSF_Utilities.Clamp(-gamepad1.right_stick_y);

            Move(left, right);

            telemetry.addData("Left Stick: ", left);
            telemetry.addData("Right Stick: ", right);
            telemetry.update();
        }

        Stop();
    }

    private void Initialize() {
        backLeft = hardwareMap.dcMotor.get("LB");
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft = hardwareMap.dcMotor.get("LF");
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRight = hardwareMap.dcMotor.get("RB");
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight = hardwareMap.dcMotor.get("RF");
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void Move(double left, double right) {
        backLeft.setPower(left);
        frontLeft.setPower(left);
        backRight.setPower(right);
        frontRight.setPower(right);
    }

    private void Stop() {
        backLeft.setPower(0.0d);
        frontLeft.setPower(0.0d);
        backRight.setPower(0.0d);
        frontRight.setPower(0.0d);
    }
}
