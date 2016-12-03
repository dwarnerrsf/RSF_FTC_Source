package org.firstinspires.ftc.teamcode;

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

public class RSF_VuforiaModule {
    private VuforiaLocalizer _localizer = null;
    private VuforiaTrackables _beacons = null;

    public VuforiaTrackables Beacons() { return _beacons; }
    public void Activate() {
        _beacons.activate();
    }
    public VuforiaTrackable GetBeacon(int index) {
        return _beacons.get(index);
    }

    public void Initialize(RSF_States.TeamColor team, String key) {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        //parameters.useExtendedTracking = false;
        parameters.vuforiaLicenseKey = key;

        _localizer = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        _beacons = _localizer.loadTrackablesFromAsset("FTC_2016-17");
        _beacons.get(0).setName("Wheels");
        _beacons.get(1).setName("Tools");
        _beacons.get(2).setName("Lego");
        _beacons.get(3).setName("Gears");

        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;
        float mmFTCFieldWidth  = ((12 * 12) - 2) * mmPerInch;

        /* Then we translate the target off to the RED WALL. Our translation here is a negative translation in X.*/
        OpenGLMatrix wheels = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 90, 0));
        _beacons.get(0).setLocation(wheels);

        OpenGLMatrix tools = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 90, 0));
        _beacons.get(1).setLocation(tools);

        OpenGLMatrix target3 = OpenGLMatrix
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 0, 0));
        _beacons.get(2).setLocation(target3);

        OpenGLMatrix target4 = OpenGLMatrix
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 0, 0));
        _beacons.get(3).setLocation(target4);

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix.translation(mmBotWidth/2,0,0)
                .multiplied((team == RSF_States.TeamColor.Red) ? Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, 0, 0) : Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, -90, 0));

        ((VuforiaTrackableDefaultListener)_beacons.get(0).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(1).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(2).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(3).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
    }

    public RSF_Beacon LocateBeacon(int beaconIndex) {
        return new RSF_Beacon(GetBeacon(beaconIndex));
    }
}
