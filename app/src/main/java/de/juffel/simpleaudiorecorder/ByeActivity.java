package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.app.Activity;
import android.media.session.MediaSession;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.juffel.simpleaudiorecorder.R;

public class ByeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        System.out.println(token);

        Integer t0 = Integer.parseInt(token.substring(0, 1));
        Integer t1 = Integer.parseInt(token.substring(1,2));
        Integer t2 = Integer.parseInt(token.substring(2,3));

        TokenButton b0 = (TokenButton) findViewById(R.id.token0);
        TokenButton b1 = (TokenButton) findViewById(R.id.token1);
        TokenButton b2 = (TokenButton) findViewById(R.id.token2);

        b0.setNumber(t0);
        b1.setNumber(t1);
        b2.setNumber(t2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bye, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
