package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.juffel.simpleaudiorecorder.R;

public class ByeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);

        // fetch token from passed parameters
        Intent intent = getIntent();

        String token = intent.getStringExtra("token");
        System.out.println(token);

        /*
        Character t0 = token.charAt(0);
        Character t1 = token.charAt(1);
        Character t2 = token.charAt(2);

        TextView v0 = (TextView) findViewById(R.id.token_1);
        TextView v1 = (TextView) findViewById(R.id.token_2);
        TextView v2 = (TextView) findViewById(R.id.token_3);

        v0.setText(t0);
        v1.setText(t1);
        v2.setText(t2); */
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
