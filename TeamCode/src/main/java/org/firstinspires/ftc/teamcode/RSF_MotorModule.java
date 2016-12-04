package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_MotorModule {
    protected DcMotor motor = null;

    protected double activePower = 0.50d;
    protected double minimumPower = -1.0d;
    protected double maximumPower = 1.0d;
    protected double stopPower = 0.0;

    public void Disable() {
        motor.setPower(stopPower);
    }

    public void EnableForward() {
        motor.setPower(activePower);
    }

    public void EnableReverse() {
        motor.setPower(-activePower);
    }

    public int GetEncoderPosition() {
        return motor.getCurrentPosition();
    }

    protected void Initialize(String name, HardwareMap hardwareMap) {
        Initialize(name, hardwareMap, DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected void Initialize(String name, HardwareMap hardwareMap, DcMotor.Direction direction) {
        Initialize(name, hardwareMap, direction, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected void Initialize(String name, HardwareMap hardwareMap, DcMotor.RunMode mode) {
        Initialize(name, hardwareMap, DcMotor.Direction.FORWARD, mode);
    }

    protected void Initialize(String name, HardwareMap hardwareMap, DcMotor.Direction direction, DcMotor.RunMode mode) {
        motor = hardwareMap.dcMotor.get(name);
        motor.setDirection(direction);
        motor.setMode(mode);

        Disable();
    }

    public void SetActivePower(double value) {
        if (value > maximumPower) {
            activePower = maximumPower;
        }
        else if (value < minimumPower) {
            activePower = minimumPower;
        }
        else {
            activePower = value;
        }
    }

    public void SetMaximumPower(double value) {
        if (value > 1.0d) {
            stopPower = 1.0d;
        }
        else {
            maximumPower = value;
        }
    }

    public void SetMinimumPower(double value) {
        if (value < -1.0d) {
            minimumPower = -1.0d;
        }
        else {
            minimumPower = value;
        }
    }

    public void SetStopPower(double value) {
        if (value > maximumPower) {
            stopPower = maximumPower;
        }
        else if (value < minimumPower) {
            stopPower = minimumPower;
        }
        else {
            stopPower = value;
        }
    }
}
