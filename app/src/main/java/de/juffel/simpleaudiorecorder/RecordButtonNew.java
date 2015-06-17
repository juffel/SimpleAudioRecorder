package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class RecordButtonNew extends BasicButton {

    private MediaRecorder recorder;
    boolean recording;

    public RecordButtonNew (final Context context, AttributeSet attrs) {
        super(context, attrs);

        recording = false;

        setAnimations(R.drawable.mikro_kommt, R.drawable.mikro_wartet1, R.drawable.mikro_wartet2);



        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!recording) {
                    triggerIdleAnimation();
                    startRecord();
                    recording = true;
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
        String filename = RecordActivity.FILENAME;
        String path = getContext().getFilesDir() + filename;
        System.out.println("record file to path " + filename);
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filename);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            //recorder.prepare();

            System.out.println("starting to record to file: " + filename);
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void stopRecord() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }

}
