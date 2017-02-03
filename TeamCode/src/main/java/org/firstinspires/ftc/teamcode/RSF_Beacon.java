package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;


public class RSF_Beacon {
    public boolean HasCoordinates = false;
    public boolean IsVisible = false;
    public String Name = "";
    public float Rotation = 0.0f;
    public boolean Stale = false;
    public float X = 0.0f;
    public float Y = 0.0f;

    public RSF_Beacon(String name, boolean isVisible, OpenGLMatrix location, boolean stale) {
        Name = name;
        IsVisible = isVisible;

        if (location != null) {
            float[] coordinates = location.getTranslation().getData();
            HasCoordinates = true;
            X = coordinates[0];
            Y = coordinates[1];
            Rotation = Orientation.getOrientation(location, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
        }

        Stale = stale;
    }
}
