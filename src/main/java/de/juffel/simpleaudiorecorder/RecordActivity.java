package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

// capture Audio Tutorial (via: http://developer.android.com/guide/topics/media/audio-capture.html#audiocapture)
public class RecordActivity extends Activity {

    private static String filename;

    private RecordButton recordButton;
    private PlayButton playButton;

    private MediaRecorder recorder;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create Layout programmatically to easily use custom defined Button-Classes
        LinearLayout ll = new LinearLayout(this);
        recordButton = new RecordButton(this);
        ll.addView(recordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        playButton = new PlayButton(this);
        ll.addView(playButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        setContentView(ll);

        // get path for filename
        filename = getFilesDir().getAbsolutePath();
        filename += "/record.3gp";
        System.out.println("output recordings to " + filename);
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
     * Custom Subclass of Button (for Record Button), that additionally handles the state of the
     * Button.
     */
    class RecordButton extends Button {
        Boolean recording;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    setText(R.string.button_record);
                    stopRecord();
                } else {
                    setText(R.string.button_stop_record);
                    startRecord();
                }
                // toggle recording status
                recording = !recording;
            }
        };

        public RecordButton(Context context) {
            super(context);
            setText(R.string.button_record);
            setOnClickListener(clicker);
            recording = false;
        }
    }

    /**
     * Custom Subclass of Button (for Play Button), that additionally handles the state of the
     * Button.
     */
    class PlayButton extends Button {
        Boolean playing;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle play start/stop
                if (playing) {
                    setText(R.string.button_replay);
                    stopReplay();
                } else {
                    setText(R.string.button_stop_replay);
                    startReplay();
                }
                // toggle playing status
                playing = !playing;
            }
        };

        public PlayButton(Context context) {
            super(context);
            setText(R.string.button_replay);
            setOnClickListener(clicker);
            playing = false;
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
