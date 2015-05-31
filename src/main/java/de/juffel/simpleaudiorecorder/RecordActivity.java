package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
     * if there is a current recording, this method uploads the file to the for now hardcoded server
     * via: https://developer.android.com/training/basics/network-ops/connecting.html
     */
    private void startUpload() {
        // retrieve upload file
        FileInputStream file = null;
        try {
            file = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // connection stuff
        OutputStream os = null;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (!(ni != null && ni.isConnected())) {
            // error
            System.out.println("Network availability check failed.");
            return;
        }
        System.out.println("Success! Network ok.");

        // proceed
        try {
            URL url = new URL("http://127.0.0.1:3000/audio/put_here/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.connect();
            os = conn.getOutputStream();
            os.write(file.read());
            os.flush();
            os.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
