/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


@TeleOp(name="Pushbot: Sensor Op", group="Pushbot")
public class RSF_SensorOp extends LinearOpMode implements SensorEventListener {
    private SensorManager _SensorManager = null;
    private Sensor _Accelerometer = null;
    private Sensor _Gyro = null;
    private Sensor _Rotation = null;
    private float X, Y, Z = 0.0f;
    private final float NOISE = 2.0f;
    private String type = "";


    protected void onResume() {
        //_SensorManager.registerListener(this, _Accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        _SensorManager.registerListener(this, _Gyro , SensorManager.SENSOR_DELAY_NORMAL);
        //_SensorManager.registerListener(this, _Rotation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        _SensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void runOpMode() throws InterruptedException {
        _SensorManager = (SensorManager)hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);

        //_Accelerometer = _SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        _Gyro = _SensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //_Rotation = _SensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        //_SensorManager.registerListener(this, _Accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        _SensorManager.registerListener(this, _Gyro , SensorManager.SENSOR_DELAY_NORMAL);
        //_SensorManager.registerListener(this, _Rotation , SensorManager.SENSOR_DELAY_NORMAL);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        resetStartTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (_Gyro == null) {
                telemetry.addData("Gyro: ", "None");
            }

            telemetry.update();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float deltaX = Math.abs(X - x);
        float deltaY = Math.abs(Y - y);
        float deltaZ = Math.abs(Z - z);

        if (deltaX < NOISE) {
            deltaX = 0.0f;
        }

        if (deltaY < NOISE) {
            deltaY = 0.0f;
        }

        if (deltaZ < NOISE) {
            deltaZ = 0.0f;
        }

        X = x;
        Y = y;
        Z = z;*/

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            type = "Accelerometer";
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            type = "Gyro";
        }
        else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            type = "Rotation";
        }
        else {
            type = "None";
        }

        telemetry.addData("Type: ", type);
        /*telemetry.addData("X: ", x);
        telemetry.addData("Y: ", y);
        telemetry.addData("Z: ", z);
        telemetry.addData("D_X: ", deltaX);
        telemetry.addData("D_Y: ", deltaY);
        telemetry.addData("D_Z: ", deltaZ);*/
    }
}


