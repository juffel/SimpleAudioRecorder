package de.juffel.simpleaudiorecorder;

import android.os.Bundle;


public class ActivityExplainRecorded extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_recorded);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ButtonExplainRecorded btn = (ButtonExplainRecorded) findViewById(R.id.explain_button_recorded);
        btn.performClick();
    }
}
