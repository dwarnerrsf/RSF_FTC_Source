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
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@TeleOp(name="Pushbot: Inherit Test", group="Pushbot")
public class RSF_InheritTest extends RSF_BaseOp {
    OpenGLMatrix lastLocation = null;

    @Override
    public void runOpMode() throws InterruptedException {
        Initialize();
        engine.SetSpeed(1.0d);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AZjwTlP/////AAAAGQ0Nw67MdEzAm0Cuvb+oJGOCSFyqWb6WplQDZyJo9vOTw0iLTPRXEXyjZ/WAN+V4wKPlbTv0pbPad2yBRZ3vyHl4hRafu7wJGLMgzfsTeozU4SzDgl378Q3zGZsJWK+UkfmekmXkzeysQqnkL0rZK+1KNaARZjbR14/OONP2j9AjqOoJa6yMMMN/2jgM5x/Lshv/++vTyHUzJYs7rEHh26FGDtlE9J8FoRXaNPrii2i3j2msG2bTO0AzrlwnK6AMtP92bqRGHnIvU4GQVnMgi2fn/EoxNRLiNktloDfdzv9vRaU3zgpOENxmqzkfdBskZzfy6EYGsEzMQPtJ07brBfDhC0l9w4kvuBi2i5dK7Mbo";
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;
        float mmFTCFieldWidth  = ((12 * 12) - 2) * mmPerInch;



        OpenGLMatrix target1 = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        beacons.get(0).setLocation(target1);

        OpenGLMatrix target2 = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        beacons.get(1).setLocation(target2);

        OpenGLMatrix target3 = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        beacons.get(2).setLocation(target3);

        OpenGLMatrix target4 = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        beacons.get(3).setLocation(target4);

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));

        ((VuforiaTrackableDefaultListener)beacons.get(0).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)beacons.get(1).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)beacons.get(2).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)beacons.get(3).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        beacons.activate();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            VuforiaTrackable legos = beacons.get(2);
            OpenGLMatrix location = ((VuforiaTrackableDefaultListener)legos.getListener()).getUpdatedRobotLocation();

            if (location != null) {
                telemetry.addData(legos.getName(), ((VuforiaTrackableDefaultListener)legos.getListener()).isVisible() ? "Visible" : "Not Visible");
                float[] coords = location.getTranslation().getData();
                telemetry.addData("X: ", coords[0]);
                telemetry.addData("Y: ", coords[1]);
                float rotation = Orientation.getOrientation(location, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
                telemetry.addData("Rotation: ", rotation);
            }



            /*for (VuforiaTrackable item: beacons) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) item.getListener()).getPose();

                if (pose != null) {
                    String name = item.getName();
                    VectorF translation = pose.getTranslation();
                    double degrees = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

                    //telemetry.addData(name + "-Translation: ", translation);
                    //telemetry.addData(name + "-Degrees: ", degrees);
                    telemetry.addData(name, ((VuforiaTrackableDefaultListener)item.getListener()).isVisible() ? "Visible" : "Not Visible");
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)item.getListener()).getUpdatedRobotLocation();

                    if (robotLocationTransform == null) {
                        telemetry.addData("Matrix: ", "Is Null.");
                    }
                    else {
                        telemetry.addData("Matrix: ", "Is not Null.");
                    }

                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }

                    if (robotLocationTransform != null) {
                        float[] coords = robotLocationTransform.getTranslation().getData();
                        telemetry.addData(name + "X: ", coords[0]);
                        telemetry.addData(name + "Y: ", coords[1]);
                        float rotation = Orientation.getOrientation(robotLocationTransform, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
                        telemetry.addData(name + "Rotation: ", rotation);
                    }
                }
            }*/

            RSF_MoveInput movement = RSF_MoveInput.Check(gamepad1);

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
            }

            /*if (lastLocation != null) {
                telemetry.addData("Pos", format(lastLocation));
            }
            else {
                telemetry.addData("Pos", "Unknown");
            }*/

            Update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}
