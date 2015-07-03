package de.juffel.simpleaudiorecorder;

import android.os.Bundle;


public class ActivityExplainUploaded extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_uploaded);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ButtonExplainUploaded btn = (ButtonExplainUploaded) findViewById(R.id.explain_button_uploaded);
        btn.performClick();
    }
}
