package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


/**
 * This activity is displayed while the Application is sleeping.
 */
public class ActivitySleep extends ActivityZiegel {

    private Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        // TODO turn off radio / turn on airplane mode if radio is on
        Context context = getApplicationContext();
        Log.i(TAG, "checking FlightMode status");
        if (super.isFlightModeEnabled(context)) {
            Log.i(TAG, "trigger setFlightMode on");
            setFlightMode(getApplicationContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // check if this ist the first time on resume is called (on first create) then ignore
        if (first) {
            first = false;
        } else {
        // or the second time then reset application!
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
