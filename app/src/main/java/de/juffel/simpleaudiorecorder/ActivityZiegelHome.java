package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityZiegelHome extends ActivityZiegel {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Context context = ActivityZiegelHome.this;
                final Intent intent = new Intent(context, ActivityZiegelStory.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // so that the sensor is not always recording
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // so that the sensor is not always recording
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
