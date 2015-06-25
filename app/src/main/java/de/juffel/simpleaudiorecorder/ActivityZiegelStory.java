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

    private void playRandomStory() {
        getRandomStory(0);
    }

    private void getRandomStory(final Integer url_index) {
        if (url_index < ActivityZiegel.SERVER_URLS.length) {
            String url = ActivityZiegel.SERVER_URLS[url_index];
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
                    // retry with next url
                    getRandomStory(url_index + 1);
                }
            });
        } else {
            // not any successful server request
        }
    }

    /**
     * Parses response URL from bytes parameter and triggers replay of this story
     * @param bytes
     */
    private void processResponse(byte[] bytes) {
        Uri url = null;
        try {
            url = Uri.parse(new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("starting to play story " + url);
        MediaPlayer player = MediaPlayer.create(getApplicationContext(), url);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("done playing");
                // TODO do something
            }
        });
        player.start();
    }
}
