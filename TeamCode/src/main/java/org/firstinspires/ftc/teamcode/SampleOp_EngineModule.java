package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SampleOp_EngineModule {
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    public void Initialize(HardwareMap hardwareMap) {
        this.frontLeft = SetMotor(hardwareMap, "frontLeft");
        this.frontRight = SetMotor(hardwareMap, "frontRight");
        this.backLeft = SetMotor(hardwareMap, "backLeft", DcMotor.Direction.REVERSE);
        this.backRight = SetMotor(hardwareMap, "backRight", DcMotor.Direction.REVERSE);

        Stop();
    }

    private DcMotor SetMotor(HardwareMap hardwareMap, String motorName) {
        return SetMotor(hardwareMap, motorName, DcMotor.Direction.FORWARD);
    }

    private DcMotor SetMotor(HardwareMap hardwareMap, String motorName, DcMotor.Direction direction) {
        return SetMotor(hardwareMap, motorName, direction, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private DcMotor SetMotor(HardwareMap hardwareMap, String motorName, DcMotor.Direction direction, DcMotor.RunMode runMode) {
        DcMotor motor = hardwareMap.dcMotor.get(motorName);
        motor.setDirection(direction);
        motor.setMode(runMode);

        return motor;
    }

    public void SetSpeed(double power) {
        SetSpeed(power, power);
    }

    public void SetSpeed(double left, double right) {
        SetSpeed(left, left, right, right);
    }

    public void SetSpeed(double backLeft, double frontLeft, double backRight, double frontRight) {
        this.backLeft.setPower(backLeft);
        this.frontLeft.setPower(frontLeft);
        this.backRight.setPower(backRight);
        this.frontRight.setPower(frontRight);
    }

    public void Stop() {
        SetSpeed(0.0d);
    }
}
