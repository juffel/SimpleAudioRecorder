package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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
        final ImageButton recordButton = (ImageButton) findViewById(R.id.button_record);
        OnClickListener recordListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setBackgroundResource(R.drawable.button_record);
                    stopRecord();
                } else {
                    recordButton.setBackgroundResource(R.drawable.button_record_stop);
                    startRecord();
                }
                // toggle recording status
                recording = !recording;
            }
        };
        recordButton.setBackgroundResource(R.drawable.button_record);
        recordButton.setOnClickListener(recordListener);
        recording = false;

        /////////////////////////
        // initialize play button
        final ImageButton playButton = (ImageButton) findViewById(R.id.button_replay);
        OnClickListener playListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle play start/stop
                if (playing) {
                    playButton.setBackgroundResource(R.drawable.button_play);
                    stopReplay();
                } else {
                    playButton.setBackgroundResource(R.drawable.button_play_stop);
                    startReplay();
                }
                // toggle playing status
                playing = !playing;
            }
        };
        playButton.setBackgroundResource(R.drawable.button_play);
        playButton.setOnClickListener(playListener);
        playing = false;

        ///////////////////////////
        // initialize upload button
        final ImageButton uploadButton = (ImageButton) findViewById(R.id.button_upload);
        uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });
        uploadButton.setBackgroundResource(R.drawable.button_upload);

        ///////////////////////////
        // initialize animation button
        ImageView animation_background = (ImageView) findViewById(R.id.animation_view);
        animation_background.setBackgroundResource(R.drawable.button_play_animated);
        final AnimationDrawable animation = (AnimationDrawable) animation_background.getBackground();
        final Button animationButton = (Button) findViewById(R.id.animation_button);
        animationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.stop();
                animation.start();
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
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // reset the replay button to non-playing state(ImageButton) findViewById(R.id.button_record
                ImageButton playButton = (ImageButton) findViewById(R.id.button_replay);
                playButton.performClick();
            }
        });
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
