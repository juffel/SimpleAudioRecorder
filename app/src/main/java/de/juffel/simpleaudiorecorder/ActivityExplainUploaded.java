package de.juffel.simpleaudiorecorder;

import android.os.Bundle;


public class ActivityExplainUploaded extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ButtonExplain btn = (ButtonExplain) findViewById(R.id.explain_button);
        btn.setResource(R.raw.no_audio);

        // check if "returned" attribute is set in intent (that means the user has pressed back in
        // home Activity) in this case start audio replay immediately
        if (getIntent().getBooleanExtra("from_home", false)) {
            btn.performClick();
        }
    }
}
