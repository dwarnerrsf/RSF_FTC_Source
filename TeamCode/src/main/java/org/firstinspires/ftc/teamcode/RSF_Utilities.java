package org.firstinspires.ftc.teamcode;


public class RSF_Utilities {
    public static double Clamp(double value) {
        return Clamp(value, -1.0d, 1.0d);
    }

    public static double Clamp(double value, double minimum, double maximum) {
        if (value < minimum) {
            return minimum;
        }
        else if (value > maximum) {
            return maximum;
        }
        else {
            return value;
        }
    }
}
