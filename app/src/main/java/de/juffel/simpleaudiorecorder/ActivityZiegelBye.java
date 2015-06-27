package de.juffel.simpleaudiorecorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class ActivityZiegelBye extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);

        Intent intent = getIntent();
        final String token = intent.getStringExtra("token");
        System.out.println(token);

        Integer t0 = Integer.parseInt(token.substring(0, 1));
        Integer t1 = Integer.parseInt(token.substring(1,2));
        Integer t2 = Integer.parseInt(token.substring(2,3));

        ButtonToken b0 = (ButtonToken) findViewById(R.id.token0);
        ButtonToken b1 = (ButtonToken) findViewById(R.id.token1);
        ButtonToken b2 = (ButtonToken) findViewById(R.id.token2);

        b0.setNumber(t0);
        b1.setNumber(t1);
        b2.setNumber(t2);

        // poll the server every 15 seconds to check wether user has already entered story information
        final Handler handler = new Handler();
        final Long delay = new Long(5000);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                // send request
                AsyncHttpClient client = new AsyncHttpClient();
                String url = ActivityZiegel.SERVER_URLS[0] + "/token_entered?token=" + token;
                final Runnable tmp_run = this;
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        // return to initial activity and lock screen
                        System.out.println("App lifecycle ended.");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("Server request: no changes were made to story metadata on server yet. Try again later.");
                        handler.postDelayed(tmp_run, delay);
                    }
                });
            }
        };
        handler.postDelayed(run, delay);

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
