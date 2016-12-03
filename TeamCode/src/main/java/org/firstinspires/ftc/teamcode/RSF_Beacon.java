package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;


public class RSF_Beacon {
    private boolean _hasCoordinates = false;
    private boolean _isVisible = false;
    private String _name = "";
    private float _rotation = 0.0f;
    private float _x = 0.0f;
    private float _y = 0.0f;

    public RSF_Beacon(VuforiaTrackable beacon) {
        VuforiaTrackableDefaultListener listener = (VuforiaTrackableDefaultListener)beacon.getListener();
        _name = beacon.getName();
        _isVisible = listener.isVisible();

        if (_isVisible) {
            OpenGLMatrix location = listener.getUpdatedRobotLocation();

            if (location != null) {
                float[] coordinates = location.getTranslation().getData();
                _hasCoordinates = true;
                _x = coordinates[0];
                _y = coordinates[1];
                _rotation = Orientation.getOrientation(location, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
            }
        }
    }

    public boolean HasCoordinates() {
        return _hasCoordinates;
    }

    public boolean IsVisible() {
        return _isVisible;
    }

    public String Name() {
        return _name;
    }


    public float Rotation() {
        return _rotation;
    }

    public float X() {
        return _x;
    }

    public float Y() {
        return _y;
    }
}
