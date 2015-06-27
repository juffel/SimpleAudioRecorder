package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class ActivityWelcome extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
                Intent intent = new Intent(context, ActivityZiegelExplain.class);
                startActivity(intent);
            }
        }, duration);
    }
}
