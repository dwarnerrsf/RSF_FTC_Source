package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_LiftModule extends RSF_MotorModule {
    public void Initialize(HardwareMap hardwareMap) {
        Initialize("LIFT", hardwareMap, DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction) {
        Initialize("LIFT", hardwareMap, direction, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.RunMode mode) {
        Initialize("LIFT", hardwareMap, DcMotor.Direction.FORWARD, mode);
    }

    public void Initialize(HardwareMap hardwareMap, DcMotor.Direction direction, DcMotor.RunMode mode) {
        Initialize("LIFT", hardwareMap, direction, mode);
    }
}
