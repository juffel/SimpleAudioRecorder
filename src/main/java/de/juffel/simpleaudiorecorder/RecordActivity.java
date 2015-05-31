package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
}
