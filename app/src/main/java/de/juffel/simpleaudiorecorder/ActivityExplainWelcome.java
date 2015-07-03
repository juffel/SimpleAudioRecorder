package de.juffel.simpleaudiorecorder;

import android.os.Bundle;


public class ActivityExplainWelcome extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ButtonExplainWelcome btn = (ButtonExplainWelcome) findViewById(R.id.explain_button_welcome);

        // check if "returned" attribute is set in intent (that means the user has pressed back in
        // home Activity) in this case start audio replay immediately
        if (getIntent().getBooleanExtra("from_home", false)) {
            btn.performClick();
        }
    }
}