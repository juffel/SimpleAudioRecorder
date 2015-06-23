package de.juffel.simpleaudiorecorder;

import android.app.Activity;

/**
 * Created by Julian on 22/06/15.
 */
public class ActivityZiegel extends Activity {

    static final String FILENAME = "record.aac";
    // static final String SERVER_URL = "http://kaesim.cepheus.uberspace.de:63190/audio/put_here";
    static final String SERVER_URL = "http://kaesim.cepheus.uberspace.de:63190/audio/put_here";

    @Override
    public void onBackPressed() {
        // do nothing here to disable back-button usage
    }
}
