package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class ActivityBye extends ActivityZiegel {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("token");
        Log.i(TAG, token);

        Integer t0 = Integer.parseInt(token.substring(0, 1));
        Integer t1 = Integer.parseInt(token.substring(1,2));
        Integer t2 = Integer.parseInt(token.substring(2,3));

        ButtonToken b0 = (ButtonToken) findViewById(R.id.token0);
        ButtonToken b1 = (ButtonToken) findViewById(R.id.token1);
        ButtonToken b2 = (ButtonToken) findViewById(R.id.token2);

        b0.setNumber(t0);
        b1.setNumber(t1);
        b2.setNumber(t2);

        checkServer(new Long(5000));
    }

    /**
     * Periodically checks on server wether Data was entered for the story using this token.
     * If no data was entered, try again in delay milliseconds, otherwise start sleep Activity
     */
    public void checkServer(final Long delay) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = ActivityZiegel.SERVER_URL + "/token_entered?token=" + token;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // return to initial activity and lock screen
                Log.i(TAG, "App lifecycle ended.");

                Intent intent = new Intent(ActivityBye.this, ActivitySleep.class);
                ActivityBye.this.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "Server request: no changes were made to story metadata on server yet. Try again later.");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkServer(delay);
                    }
                }, delay);
            }
        });
    }

    public void endBye() {

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
