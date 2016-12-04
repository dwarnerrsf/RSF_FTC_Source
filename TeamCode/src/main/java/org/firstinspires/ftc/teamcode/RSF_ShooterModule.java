package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_ShooterModule extends RSF_MotorModule {
    public void Initialize(HardwareMap hardwareMap) {
        Initialize("SHOOT", hardwareMap, DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction) {
        Initialize("SHOOT", hardwareMap, direction, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.RunMode mode) {
        Initialize("SHOOT", hardwareMap, DcMotor.Direction.FORWARD, mode);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction, DcMotor.RunMode mode) {
        Initialize("SHOOT", hardwareMap, direction, mode);
    }
}
