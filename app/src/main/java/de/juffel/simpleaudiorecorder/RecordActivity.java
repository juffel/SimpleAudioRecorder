package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

// capture Audio Tutorial (via: http://developer.android.com/guide/topics/media/audio-capture.html#audiocapture)
public class RecordActivity extends Activity {

    static final String FILENAME = "/data/data/de.juffel.simpleaudiorecorder/files/record.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    /**
     * Free the audio record and playback resources so they do not eat battery in background
     */
    @Override
    protected void onPause() {
        super.onPause();

        // call stop on recorder and player
        PlayButton pb = (PlayButton) findViewById(R.id.play_button);
        pb.stopReplay();
        RecordButton rb = (RecordButton) findViewById(R.id.record_button);
        rb.stopRecord();
    }

    /**
     * Uploads the current recording to the server using the AsyncHttpClient Library
     */
    private void startUpload() {
        String url = "http://192.168.178.22:3000/audio/put_here";
        String file_path = FILENAME;

        // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
        // gather parameters and upload file
        File file = new File(file_path);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        // send request
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                System.out.println("response received with status code " + statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("response received with status code " + statusCode);
            }
        });
    }
}
