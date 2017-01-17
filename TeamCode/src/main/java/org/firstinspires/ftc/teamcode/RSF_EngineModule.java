package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_EngineModule {
    private DcMotor motor_FrontLeft = null;
    private DcMotor motor_BackLeft = null;
    private DcMotor motor_FrontRight = null;
    private DcMotor motor_BackRight = null;
    private RSF_States.Encoders encoders = RSF_States.Encoders.Off;
    private double motorSpeed = 0.0d;

    public int GetAverageEncoderPosition() {
        int value = GetEncoderPosition(RSF_States.EngineMotor.FrontLeft);
        value += GetEncoderPosition(RSF_States.EngineMotor.BackLeft);
        value += GetEncoderPosition(RSF_States.EngineMotor.FrontRight);
        value += GetEncoderPosition(RSF_States.EngineMotor.BackRight);

        return (value / 4) - 10;
    }

    public int GetEncoderPosition(RSF_States.EngineMotor motor) {
        if (encoders == RSF_States.Encoders.On) {
            switch (motor) {
                case FrontLeft:
                    return motor_FrontLeft.getCurrentPosition();
                case BackLeft:
                    return motor_BackLeft.getCurrentPosition();
                case FrontRight:
                    return motor_FrontRight.getCurrentPosition();
                case BackRight:
                    return motor_BackRight.getCurrentPosition();
                default:
                    return 0;
            }
        }
        else {
            return 0;
        }
    }

    public boolean HasEncoders() {
        return (encoders == RSF_States.Encoders.On) ? true : false;
    }

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

    public void Initialize(HardwareMap hardwareMap, RSF_States.Encoders encoderMode) {
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

        if (encoderMode == RSF_States.Encoders.On) {
            ResetEncoders();
            SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else {
            SetMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        encoders = encoderMode;

        // Set all the motors to zero power.
        Stop();
    }

    public void Move(RSF_States.DPad dpad) {
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(joysticks.Left(), joysticks.Left(), joysticks.Right(), joysticks.Right());
    }

    public void Move(RSF_Joysticks joysticks, double maximum) {
        double minimum = maximum * -1;
        double left = joysticks.Left();
        double right = joysticks.Right();

        if (left > maximum) {
            left = maximum;
        }
        else if (left < minimum) {
            left = minimum;
        }

        if (right > maximum) {
            right = maximum;
        }
        else if (right < minimum) {
            right = minimum;
        }

        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(left, left, right, right);
    }

    public void MoveTo(int rotations) {
        motor_FrontLeft.setTargetPosition(rotations);
        motor_BackLeft.setTargetPosition(rotations);
        motor_FrontRight.setTargetPosition(rotations);
        motor_BackRight.setTargetPosition(rotations);

        SetMode(DcMotor.RunMode.RUN_TO_POSITION);
        SetPower(motorSpeed, motorSpeed, motorSpeed, motorSpeed);
    }

    public void ResetEncoders() {
        SetMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void SetEngineMode(RSF_States.Encoders mode) {
        encoders = mode;
    }

    private void SetMode(DcMotor.RunMode runMode) {
        motor_FrontLeft.setMode(runMode);
        motor_BackLeft.setMode(runMode);
        motor_FrontRight.setMode(runMode);
        motor_BackRight.setMode(runMode);
    }

    public void SetPower(double frontLeft, double backLeft, double frontRight, double backRight) {
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

    public void TestTurn() {
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(0.0, 0.0d, -1.0d, -1.0d);
    }

    public void RighTurn(double power) {
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(0.0, 0.0d, power, power);
    }

    public void LeftTurn() {
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(-1.0, -1.0d, 0.0d, 0.0d);
    }

    public void LeftTurn(double power) {
        SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SetPower(power, power, 0.0d, 0.0d);
    }
}
