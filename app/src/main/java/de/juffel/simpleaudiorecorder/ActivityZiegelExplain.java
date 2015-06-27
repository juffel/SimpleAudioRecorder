package de.juffel.simpleaudiorecorder;

import android.os.Bundle;


public class ActivityZiegelExplain extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check if "returned" attribute is set in intent (that means the user has pressed back in
        // home Activity) in this case start audio replay immediately
        if (getIntent().getBooleanExtra("from_home", false)) {
            ButtonExplain btn = (ButtonExplain) findViewById(R.id.explain_button);
            btn.performClick();
        }
    }
}
