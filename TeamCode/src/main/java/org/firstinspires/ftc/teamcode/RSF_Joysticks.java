package org.firstinspires.ftc.teamcode;


public class RSF_Joysticks {
    private double _left = 0.0d;
    private double _right = 0.0d;

    public RSF_Joysticks(double left, double right) {
        _left = left;
        _right = right;
    }

    public double Left() {
        return _left;
    }
    public double Right() {
        return _right;
    }
}
