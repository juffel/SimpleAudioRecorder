package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class ButtonRecord extends ButtonBasic {

    private static String file_path;

    private MediaRecorder recorder;
    private Boolean recording;

    public ButtonRecord(final Context context, AttributeSet attrs) {
        super(context, attrs);

        recording = false;

        setAnimations(R.drawable.mic, R.drawable.mic_animated, R.drawable.mic);

        file_path = context.getFilesDir() + ActivityZiegel.FILENAME;

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!recording) {
                    recording = true;
                    triggerIdleAnimation();
                    startRecord();
                } else {
                    recording = false;
                    triggerEntryAnimation();
                    stopRecord();
                    final Intent intent = new Intent(context, ActivityProcess.class);

                    // we start the next Activity from a separate thread, so that we can properly wait for
                    // the Animation to end first.
                    Runnable startNext = new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                            ((AnimationDrawable) ButtonRecord.super.getBackground()).stop();
                            ButtonRecord.super.setBackground(null);
                        }
                    };
                    Handler delayHandler = new Handler();
                    // play exit animation
                    int waitTime = ButtonRecord.super.triggerExitAnimation();
                    delayHandler.postDelayed(startNext, waitTime);
                }

            }
        });

        triggerEntryAnimation();

    }

    /**
     * Control audio capture (start & stop)
     */
    private void startRecord() {
        Log.i(TAG, "record file to path " + file_path);
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(file_path);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "starting to record to file: " + file_path);
        recorder.start();
    }
    void stopRecord() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }

}
