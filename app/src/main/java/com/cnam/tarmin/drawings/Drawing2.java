package com.cnam.tarmin.drawings;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Drawing2 extends AppCompatActivity {

    DrawingScreen2 drawingScreen;
    SensorManager sensorManager;
    SensorEventListener accelerometerListener;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_drawing2);

        drawingScreen = new DrawingScreen2(this);
        drawingScreen.setPosition(drawingScreen.getMeasuredWidth() / 2, drawingScreen.getMeasuredHeight() / 2);
        drawingScreen.setVelocity(0, 0);
        setContentView(drawingScreen);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                //gx.setText("gx = " + event.values[0] + " m/s2");
                //gy.setText("gy = " + event.values[1] + " m/s2");
                //gz.setText("gz = " + event.values[2] + " m/s2");
                float vx = event.values[0];
                float vy = event.values[1];

                drawingScreen.addVelocity(vx, vy);
                drawingScreen.processPosition();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        drawingScreen.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(accelerometerListener);
        drawingScreen.pause();
    }
}
