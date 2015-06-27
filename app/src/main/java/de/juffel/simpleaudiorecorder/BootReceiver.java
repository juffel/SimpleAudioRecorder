package de.juffel.simpleaudiorecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class contains code that will be executed when the App receives the Booted-Event from the
 * Android System. How this event is received is documented here: http://www.andreas-schrade.de/2015/02/16/android-tutorial-how-to-create-a-kiosk-mode-in-android/
 * Created by Julian on 22/06/15.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intentParam) {
        Intent intent = new Intent(context, ActivityZiegelExplain.class);
        // TODO check, whether next line is necessary
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
