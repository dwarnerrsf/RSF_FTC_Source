package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_EngineModule {
    private DcMotor motor_FrontLeft = null;
    private DcMotor motor_BackLeft = null;
    private DcMotor motor_FrontRight = null;
    private DcMotor motor_BackRight = null;
    private double motorSpeed = 0.0d;

    public void Initialize(HardwareMap hardwareMap) {
        // Define and Initialize Motors
        motor_FrontLeft = hardwareMap.dcMotor.get("LF");
        motor_BackLeft = hardwareMap.dcMotor.get("LB");
        motor_FrontRight = hardwareMap.dcMotor.get("RF");
        motor_BackRight = hardwareMap.dcMotor.get("RB");

        // Set to REVERSE if using AndyMark motors
        motor_FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motor_BackLeft.setDirection(DcMotor.Direction.FORWARD);
        motor_FrontRight.setDirection(DcMotor.Direction.REVERSE);
        motor_BackRight.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to run without encoders.
        motor_FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all the motors to zero power.
        Stop();
    }

    public void Move(RSF_States.DPad dpad) {
        switch (dpad) {
            case Down:
                SetPower(-motorSpeed, -motorSpeed, -motorSpeed, -motorSpeed);
                break;
            case DownLeft:
                SetPower(motorSpeed, 0.0d, motorSpeed, 0.0d);
                break;
            case DownRight:
                SetPower(-motorSpeed, 0.0d, -motorSpeed, 0.0d);
                break;
            case Left:
                SetPower(-motorSpeed, motorSpeed, -motorSpeed, motorSpeed);
                break;
            case Right:
                SetPower(motorSpeed, -motorSpeed, motorSpeed, -motorSpeed);
                break;
            case Up:
                SetPower(motorSpeed, motorSpeed, motorSpeed, motorSpeed);
                break;
            case UpLeft:
                SetPower(0.0d, -motorSpeed, 0.0d, -motorSpeed);
                break;
            case UpRight:
                SetPower(0.0d, motorSpeed, 0.0d, motorSpeed);
                break;
            default:
                SetPower(motorSpeed, motorSpeed, motorSpeed, motorSpeed);
                break;
        }
    }

    public void Move(RSF_States.DPad dpad, double speed) {
        motorSpeed = speed;

        switch (dpad) {
            case Down:
                SetPower(-motorSpeed, -motorSpeed, -motorSpeed, -motorSpeed);
                break;
            case DownLeft:
                SetPower(motorSpeed, 0.0d, motorSpeed, 0.0d);
                break;
            case DownRight:
                SetPower(-motorSpeed, 0.0d, -motorSpeed, 0.0d);
                break;
            case Left:
                SetPower(-motorSpeed, motorSpeed, -motorSpeed, motorSpeed);
                break;
            case Right:
                SetPower(motorSpeed, -motorSpeed, motorSpeed, -motorSpeed);
                break;
            case Up:
                SetPower(motorSpeed, motorSpeed, motorSpeed, motorSpeed);
                break;
            case UpLeft:
                SetPower(0.0d, -motorSpeed, 0.0d, -motorSpeed);
                break;
            case UpRight:
                SetPower(0.0d, motorSpeed, 0.0d, motorSpeed);
                break;
            default:
                SetPower(motorSpeed, motorSpeed, motorSpeed, motorSpeed);
                break;
        }
    }

    public void Move(RSF_Joysticks joysticks) {
        SetPower(joysticks.Left(), joysticks.Left(), joysticks.Right(), joysticks.Right());
    }

    private void SetPower(double frontLeft, double backLeft, double frontRight, double backRight) {
        motor_FrontLeft.setPower(frontLeft);
        motor_BackLeft.setPower(backLeft);
        motor_FrontRight.setPower(frontRight);
        motor_BackRight.setPower(backRight);
    }

    public void SetSpeed(double value) {
        if (value > 1.0d) {
            motorSpeed = 1.0d;
        }
        else if (value < -1.0d) {
            motorSpeed = -1.0d;
        }
        else {
            motorSpeed = value;
        }
    }

    public void Stop() {
        SetPower(0.0d, 0.0d, 0.0d, 0.0d);
    }

    public double TestPower() {
        return motorSpeed;
    }
}
