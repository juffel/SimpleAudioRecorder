package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.app.Activity;
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

        String t0 = token.substring(0,1);
        String t1 = token.substring(1,2);
        String t2 = token.substring(2,3);

        TextView v0 = (TextView) findViewById(R.id.token1);
        TextView v1 = (TextView) findViewById(R.id.token2);
        TextView v2 = (TextView) findViewById(R.id.token3);

        v0.setText(t0);
        v1.setText(t1);
        v2.setText(t2);
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
