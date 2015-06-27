package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


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
            ExplainButton btn = (ExplainButton) findViewById(R.id.explain_button);
            btn.performClick();
        }
    }
}
