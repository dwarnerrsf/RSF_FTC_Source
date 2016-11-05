package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@TeleOp(name="Concept: Vuforia Navigation", group ="Concept")
public class VuforiaTest2 extends LinearOpMode {

  @Override public void runOpMode() {
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

    waitForStart();

    beacons.activate();

    while (opModeIsActive()) {
      for (VuforiaTrackable item: beacons) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) item.getListener()).getPose();

        if (pose != null) {
          String name = item.getName();
          VectorF translation = pose.getTranslation();
          double degrees = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));

          telemetry.addData(name + "-Translation: ", translation);
          telemetry.addData(name + "-Degrees: ", degrees);
        }
      }

      telemetry.update();
    }
  }
}
