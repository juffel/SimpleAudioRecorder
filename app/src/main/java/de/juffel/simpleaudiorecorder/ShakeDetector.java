package de.juffel.simpleaudiorecorder;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

/**
 * Created by kai on 24.06.15.
 *
 * after a tutorial on shakedetectors by Jason McReynolds (jasonmcreynolds.com)
 */
public class ShakeDetector implements SensorEventListener {

    // TODO check this with actual phone and app G-force
    private static final float SHAKE_GRAVITY_THRESHHOLD = 2.7F;
    // time before next shake event is permitted
    private static long SHAKE_DELAY_TIME = 500;

    private OnShakeListener mListener;
    private long mShakeTimestamp = 0;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        // callback
        // TODO we can pass the number of shakes here or only call this method after the set amount of shakes
        public void onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // we don't need it
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gx = x / SensorManager.GRAVITY_EARTH;
            float gy = y / SensorManager.GRAVITY_EARTH;
            float gz = z / SensorManager.GRAVITY_EARTH;

            // the geforce for no movement should be 1 (with x and y being 0 and z being equivalent to earth gravity
            double gForce = Math.sqrt(gx * gx + gy * gy + gz * gz);

            if (gForce > SHAKE_GRAVITY_THRESHHOLD) {
                // check wether the last shake is far enough in the past and ignore if not
                final long now = System.currentTimeMillis();
                if (mShakeTimestamp + SHAKE_DELAY_TIME > now) {
                    return;
                }

                // TODO maybe build in a shake counter, so that you have to shake the device twice to minimize accidental activation

                mShakeTimestamp = now;

                mListener.onShake();
            }
        }
    }

}
