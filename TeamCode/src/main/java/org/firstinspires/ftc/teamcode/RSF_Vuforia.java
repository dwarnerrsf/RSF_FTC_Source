package org.firstinspires.ftc.teamcode;

import com.vuforia.HINT;
import com.vuforia.Vuforia;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

public class RSF_Vuforia {
    private VuforiaLocalizer _localizer = null;
    private VuforiaTrackables _beacons = null;

    public VuforiaTrackables Beacons() { return _beacons; }

    public void Activate() {
        _beacons.activate();
    }

    public VuforiaTrackable GetBeacon(int index) {
        return _beacons.get(index);
    }

    public void Initialize(String key) {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = key;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        _localizer = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        _beacons = _localizer.loadTrackablesFromAsset("FTC_2016-17");
        _beacons.get(0).setName("Wheels");
        _beacons.get(1).setName("Tools");
        _beacons.get(2).setName("Lego");
        _beacons.get(3).setName("Gears");
    }
}
