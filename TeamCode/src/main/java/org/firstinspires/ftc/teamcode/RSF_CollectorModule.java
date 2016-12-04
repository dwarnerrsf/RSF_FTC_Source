package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_CollectorModule extends RSF_MotorModule {
    public void Initialize(HardwareMap hardwareMap) {
        Initialize("COLLECT", hardwareMap, DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction) {
        Initialize("COLLECT", hardwareMap, direction, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.RunMode mode) {
        Initialize("COLLECT", hardwareMap, DcMotor.Direction.FORWARD, mode);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction, DcMotor.RunMode mode) {
        Initialize("COLLECT", hardwareMap, direction, mode);
    }
}
