package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;

import java.io.File;
import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class RecordButtonNew extends BasicButton {

    private static String file_path;

    private MediaRecorder recorder;
    boolean recording;

    public RecordButtonNew (final Context context, AttributeSet attrs) {
        super(context, attrs);

        recording = false;

        setAnimations(R.drawable.mikro_kommt, R.drawable.mikro_wartet1, R.drawable.mikro_wartet2);

        file_path = context.getFilesDir() + RecordActivity.FILENAME;

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!recording) {
                    triggerIdleAnimation();
                    recording = true;
                    startRecord();
                } else {
                    stopRecord();
                    recording = false;
                    final Intent intent = new Intent(context, ProcessActivity.class);

                    // we start the next Activity from a separate thread, so that we can properly wait for
                    // the Animation to end first.
                    Runnable startNext = new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                            ((AnimationDrawable)RecordButtonNew.super.getBackground()).stop();
                            RecordButtonNew.super.setBackground(null);
                        }
                    };
                    Handler delayHandler = new Handler();
                    // play exit animation
                    int waitTime = RecordButtonNew.super.triggerExitAnimation();
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
        System.out.println("record file to path " + file_path);
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(file_path);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("starting to record to file: " + file_path);
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
