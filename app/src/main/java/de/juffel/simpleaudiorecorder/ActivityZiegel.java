package de.juffel.simpleaudiorecorder;

import android.app.Activity;

/**
 * Created by Julian on 22/06/15.
 */
public class ActivityZiegel extends Activity {

    static final String FILENAME = "record.3gp";
    // kai uberspace
    static final String SERVER_URL = "http://kaesim.cepheus.uberspace.de:63190";
    static final String TAG = "ActivityZiegel";

    @Override
    public void onBackPressed() {
        // do nothing here to disable back-button usage
    }
}
