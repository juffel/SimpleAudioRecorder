package de.juffel.simpleaudiorecorder;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URL;


public class ActivityZiegelStory extends ActivityZiegel {

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

    private void playRandomStory() {
        getRandomStory(0);
    }

    /**
     * Tries to get a random story from server with address ActivityZiegel.SERVER_URLS[url_index]
     */
    private void getRandomStory(final Integer url_index) {
        if (url_index < ActivityZiegel.SERVER_URLS.length) {
            String url = ActivityZiegel.SERVER_URLS[url_index] + "/random";
            System.out.println("requesting random story from " + url);

            // send request
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                    System.out.println("response received with status code " + statusCode);
                    // TODO trigger exit animation
                    processResponse(bytes, ActivityZiegel.SERVER_URLS[url_index]);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("response received with status code " + statusCode);
                    // retry with next url
                    getRandomStory(url_index + 1);
                }
            });
        } else {
            // not any successful server request
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop replay when activity is left
        RandomPlayButton btn = (RandomPlayButton) findViewById(R.id.random_play_button);
        btn.stopPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop replay when activity is left
        RandomPlayButton btn = (RandomPlayButton) findViewById(R.id.random_play_button);
        btn.stopPlay();
    }
    /**
     * Parses response URL from bytes parameter and triggers replay of this story
     * @param bytes
     */
    private void processResponse(byte[] bytes, String server) {
        // parse server "XML" response
        String url = server;
        try {
            url += new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RandomPlayButton btn = (RandomPlayButton) findViewById(R.id.random_play_button);
        btn.setResourceUrl(url);
        btn.performClick();
    }
}
