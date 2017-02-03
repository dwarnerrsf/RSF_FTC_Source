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
    private OpenGLMatrix _currentLocation = null;

    private float mmPerInch = 25.4f;
    private float mmBotWidth = 18.0f * mmPerInch;
    private float mmFTCFieldWidth = ((12.0f * 12.0f) - 2.0f) * mmPerInch;

    public VuforiaTrackables Beacons() { return _beacons; }
    public void Activate() {
        _beacons.activate();
    }
    public VuforiaTrackable GetBeacon(int index) {
        return _beacons.get(index);
    }

    public RSF_Beacon GetCoordinates(int index) {
        VuforiaTrackable beacon = GetBeacon(index);
        VuforiaTrackableDefaultListener listener = (VuforiaTrackableDefaultListener)beacon.getListener();
        OpenGLMatrix updatedLocation = listener.getUpdatedRobotLocation();
        boolean stale = true;

        if (updatedLocation != null) {
            _currentLocation = updatedLocation;
            stale = false;
        }

        return new RSF_Beacon(beacon.getName(), listener.isVisible(), _currentLocation, stale);
    }

    public void Initialize(RSF_States.TeamColor team, String key) {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        parameters.useExtendedTracking = true;
        parameters.vuforiaLicenseKey = key;

        _localizer = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        _beacons = _localizer.loadTrackablesFromAsset("FTC_2016-17");
        _beacons.get(0).setName("Wheels");
        _beacons.get(1).setName("Tools");
        _beacons.get(2).setName("Lego");
        _beacons.get(3).setName("Gears");

        /* Then we translate the target off to the RED WALL. Our translation here is a negative translation in X.*/
        OpenGLMatrix wheels = OpenGLMatrix
                .translation(-mmFTCFieldWidth / 2.0f, 0.0f, 0.0f)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90.0f, 0.0f, 0.0f));
        _beacons.get(0).setLocation(wheels);

        OpenGLMatrix tools = OpenGLMatrix
                .translation(-mmFTCFieldWidth / 2.0f, 0.0f, 0.0f)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90.0f, 0.0f, 0.0f));
        _beacons.get(1).setLocation(tools);

        OpenGLMatrix target3 = OpenGLMatrix
                .translation(0.0f, mmFTCFieldWidth / 2.0f, 0.0f)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90.0f, 0.0f, 0.0f));
        _beacons.get(2).setLocation(target3);

        OpenGLMatrix target4 = OpenGLMatrix
                .translation(0.0f, mmFTCFieldWidth / 2.0f, 0.0f)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90.0f, 0.0f, 0.0f));
        _beacons.get(3).setLocation(target4);

        /*OpenGLMatrix wheels = OpenGLMatrix
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
        _beacons.get(3).setLocation(target4);*/

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix.translation(mmBotWidth/2,0,0)
                .multiplied((team == RSF_States.TeamColor.Red) ? Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, 0, 0) : Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, -90, 0));

        ((VuforiaTrackableDefaultListener)_beacons.get(0).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(1).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(2).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)_beacons.get(3).getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
    }
}
