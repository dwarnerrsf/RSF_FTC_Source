package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_DeviceInterfaceModule {
    protected DeviceInterfaceModule deviceInterface = null;

    public void Initialize(HardwareMap hardwareMap, int channel) {
        deviceInterface = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        deviceInterface.setDigitalChannelMode(channel, DigitalChannelController.Mode.OUTPUT);
        deviceInterface.setDigitalChannelState(channel, false);
    }
}
