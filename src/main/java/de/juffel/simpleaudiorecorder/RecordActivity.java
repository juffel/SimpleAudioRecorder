package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// capture Audio Tutorial (via: http://developer.android.com/guide/topics/media/audio-capture.html#audiocapture)
public class RecordActivity extends Activity {

    private static String filename;

    private MediaRecorder recorder;
    private MediaPlayer player;

    private Boolean recording;
    private Boolean playing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // get path for filename
        filename = getFilesDir().getAbsolutePath();
        filename += "/record.3gp";
        System.out.println("output recordings to " + filename);

        ///////////////////////////
        // initialize record button
        final Button recordButton = (Button) findViewById(R.id.button_record);
        OnClickListener recordListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setText(R.string.button_record);
                    stopRecord();
                } else {
                    recordButton.setText(R.string.button_stop_record);
                    startRecord();
                }
                // toggle recording status
                recording = !recording;
            }
        };
        recordButton.setText(R.string.button_record);
        recordButton.setOnClickListener(recordListener);
        recording = false;

        /////////////////////////
        // initialize play button
        final Button playButton = (Button) findViewById(R.id.button_replay);
        OnClickListener playListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle play start/stop
                if (playing) {
                    playButton.setText(R.string.button_replay);
                    stopReplay();
                } else {
                    playButton.setText(R.string.button_stop_replay);
                    startReplay();
                }
                // toggle playing status
                playing = !playing;
            }
        };
        playButton.setText(R.string.button_replay);
        playButton.setOnClickListener(playListener);
        playing = false;

        ///////////////////////////
        // initialize upload button
        final Button uploadButton = (Button) findViewById(R.id.button_upload);
        uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });
    }

    /**
     * Free the audio record and playback resources so they do not eat battery in background
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * Control audio capture (start & stop)
     */
    private void startRecord() {
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filename);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
    }
    private void stopRecord() {
        recorder.stop();
        recorder.release();
        recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
    }

    /**
     * Control audio playback (start & stop)
     */
    private void startReplay() {
        player = new MediaPlayer();
        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void stopReplay() {
        player.release();
        player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
    }

    /**
     * Uploads the current recording to the server using the AsyncHttpClient Library
     */
    private void startUpload() {
        String url = "http://192.168.178.22:3000/audio/put_here";
        String file_path = filename;

        // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
        // gather parameters and upload file
        File file = new File(file_path);
        RequestParams params = new RequestParams();
        try {
            params.put("recording.3gp", file);
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
