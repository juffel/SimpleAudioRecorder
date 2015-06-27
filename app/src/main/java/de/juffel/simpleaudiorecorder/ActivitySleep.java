package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


/**
 * This activity is displayed while the Application is sleeping.
 */
public class ActivitySleep extends ActivityZiegel {

    private Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
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
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
