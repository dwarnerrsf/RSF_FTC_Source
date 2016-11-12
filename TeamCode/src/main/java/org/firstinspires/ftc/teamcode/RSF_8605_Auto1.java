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

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;


@TeleOp(name="Pushbot: 8605 Auto 1", group="Pushbot")
public class RSF_8605_Auto1 extends RSF_BaseOp {
    private final String VuforiaLicenseKey = "AZjwTlP/////AAAAGQ0Nw67MdEzAm0Cuvb+oJGOCSFyqWb6WplQDZyJo9vOTw0iLTPRXEXyjZ/WAN+V4wKPlbTv0pbPad2yBRZ3vyHl4hRafu7wJGLMgzfsTeozU4SzDgl378Q3zGZsJWK+UkfmekmXkzeysQqnkL0rZK+1KNaARZjbR14/OONP2j9AjqOoJa6yMMMN/2jgM5x/Lshv/++vTyHUzJYs7rEHh26FGDtlE9J8FoRXaNPrii2i3j2msG2bTO0AzrlwnK6AMtP92bqRGHnIvU4GQVnMgi2fn/EoxNRLiNktloDfdzv9vRaU3zgpOENxmqzkfdBskZzfy6EYGsEzMQPtJ07brBfDhC0l9w4kvuBi2i5dK7Mbo";

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize(VuforiaLicenseKey);
        engine.SetSpeed(0.15d);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        vuforia.Activate();

        boolean follow = false;
        boolean found = false;
        boolean centered = false;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            VuforiaTrackable legos = vuforia.GetBeacon(2);

            boolean isVisible = ((VuforiaTrackableDefaultListener)legos.getListener()).isVisible();
            telemetry.addData(legos.getName(), isVisible ? "Visible" : "Not Visible");

            if (isVisible) {
                follow = true;
            }
            else {
                if (!found) engine.Move(RSF_States.DPad.Up);
            }

            OpenGLMatrix location = ((VuforiaTrackableDefaultListener)legos.getListener()).getUpdatedRobotLocation();

            if (location != null) {
                float[] coords = location.getTranslation().getData();
                float rotation = Orientation.getOrientation(location, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

                double rotateSpeed = 0.10d;
                double forwardSpeed = 0.15d;

                if (follow && coords[1] > 1300.0f) {
                    if (rotation > 91.5f) {
                        engine.Move(new RSF_Joysticks(rotateSpeed, -rotateSpeed));
                    }
                    else if (rotation < 88.5f) {
                        engine.Move(new RSF_Joysticks(-rotateSpeed, rotateSpeed));
                    }
                    else {
                        engine.Stop();
                    }
                }
                else {
                    engine.Move(RSF_States.DPad.Up);
                }

                telemetry.addData("X: ", coords[0]);
                telemetry.addData("Y: ", coords[1]);
                telemetry.addData("Rotation: ", rotation);
            }
            else {
                telemetry.addData("X: ", "None");
                telemetry.addData("Y: ", "None");
                telemetry.addData("Rotation: ", "None");
            }

            telemetry.addData("Follow: ", follow ? "ON" : "OFF");

            /*RSF_MoveInput movement = RSF_MoveInput.Check(gamepad1);

            switch (movement.MoveType()) {
                case Dpad:
                    engine.Move(movement.Dpad());
                    break;
                case Joystick:
                    engine.Move(movement.Joystick());
                    break;
                default:
                    engine.Stop();
                    break;
            }*/

            Update(40);
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}
