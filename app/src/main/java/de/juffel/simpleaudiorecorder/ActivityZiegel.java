package de.juffel.simpleaudiorecorder;

import android.app.Activity;

/**
 * Created by Julian on 22/06/15.
 */
public class ActivityZiegel extends Activity {

    static final String FILENAME = "record.mp3";
    // a list of servers a recording will tried to be uploaded to in order of appereance,
    // stops after first sucessful upload
    static final String[] SERVER_URLS = {
            // julian home
            "http://varuna.fritz.box:3000",
            // julian eduroam
            "http://172.16.241.187:3000",
            // kai uberspace
            "http://kaesim.cepheus.uberspace.de:63190"};

    @Override
    public void onBackPressed() {
        // do nothing here to disable back-button usage
    }
}
