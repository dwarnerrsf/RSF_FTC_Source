package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class RSF_ColorModule {
    private ColorSensor sensor = null;
    private View relativeLayout = null;

    protected void Initialize(HardwareMap hardwareMap, boolean enableLight) {
        sensor = hardwareMap.colorSensor.get("COLOR");
        sensor.enableLed(enableLight);

        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);
    }

    public RSF_States.SensorColor Detect() {
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;

        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        Color.RGBToHSV((red * 255) / 800, (green * 255) / 800, (blue * 255) / 800, hsvValues);

        RSF_States.SensorColor sensorColor = RSF_States.SensorColor.None;

        if (red > blue && red > green) {
            sensorColor = RSF_States.SensorColor.Red;
        }
        else if (blue > red && blue > green) {
            sensorColor = RSF_States.SensorColor.Blue;
        }

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        return sensorColor;
    }
}
