package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class ActivityWelcome extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // TODO turn on radio / turn off airplane mode if radio is off
        Context context = getApplicationContext();
        Log.i(TAG, "checking FlightMode status");
        if (!isFlightModeEnabled(context)) {
            Log.i(TAG, "trigger setFlightMode off");
            setFlightMode(getApplicationContext());
        }
    }

    @Override
    protected void onResume() {
        final Context context = getApplicationContext();
        super.onResume();
        // play welcome/startanimation (this button is not really used as a button, but only to display an animation)
        ButtonBasic btn = (ButtonBasic) findViewById(R.id.button_welcome);

        // start animation and start ExplainActivity after the amount of time the animations endures
        Integer duration = btn.triggerEntryAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(context, ActivityExplain.class);
                startActivity(intent);
            }
        }, duration);
    }
}
