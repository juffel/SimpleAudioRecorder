package de.juffel.simpleaudiorecorder;

import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;


public class ActivityZiegelRandomStory extends ActivityZiegel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
    }

    // trigger replay of random story onResume, this method is called after the activity has become visible
    @Override
    protected void onResume() {
        super.onResume();
        playRandomStory();
    }

    /**
     * Tries to get a random story from server with address ActivityZiegel.SERVER_URLS[url_index]
     */
    private void playRandomStory() {
            String url = ActivityZiegel.SERVER_URL + "/random";
            System.out.println("requesting random story from " + url);

            // send request
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    System.out.println("response received with status code " + statusCode);
                    // TODO trigger exit animation
                    processResponse(bytes);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("response received with status code " + statusCode);
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop replay when activity is left
        ButtonRandomPlay btn = (ButtonRandomPlay) findViewById(R.id.random_play_button);
        btn.stopPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop replay when activity is left
        ButtonRandomPlay btn = (ButtonRandomPlay) findViewById(R.id.random_play_button);
        btn.stopPlay();
    }
    /**
     * Parses response URL from bytes parameter and triggers replay of this story
     * @param bytes
     */
    private void processResponse(byte[] bytes) {
        // parse server "XML" response
        String url = ActivityZiegel.SERVER_URL;
        try {
            url += new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ButtonRandomPlay btn = (ButtonRandomPlay) findViewById(R.id.random_play_button);
        btn.setResourceUrl(url);
        btn.performClick();
    }
}
