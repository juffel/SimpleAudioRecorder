package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * This activity is displayed while the Application is sleeping.
 */
public class ActivitySleep extends ActivityZiegel {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_sleep, menu);
        return true;
    }
}
