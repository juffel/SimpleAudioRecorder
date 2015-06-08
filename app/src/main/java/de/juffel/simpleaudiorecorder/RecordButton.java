package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by Julian on 08/06/15.
 */
public class RecordButton extends ToggleStateButton {

    private MediaRecorder recorder;
    private static final String filename = "/data/data/de.juffel.simpleaudiorecorder/files/record.3gp";

    // this constructor is called, when a RecordButton is created in code
    public RecordButton(Context context) {
        super(context);
    }

    // this constructor is called, when a RecordButton is declared via XML
    public RecordButton(Context context, AttributeSet attrs) {
        super(context);

        // set this Button's animations
        this.setAnimations(R.drawable.button_record_idle_exit, R.drawable.button_record_idle_entry);

        // set own onclicklistener
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    /**
     * Toggles the state of this Button and adjusts the resource Background
     */
    @Override
    public void toggle() {
        if (super.getState()) {
            startRecord();
        } else {
            stopRecord();
        }
        super.toggle();
    }

    /**
     * Control audio capture (start & stop)
     */
    private void startRecord() {
        System.out.println("record file to path " + filename);
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

        System.out.println("starting to record to file: " + filename);
        recorder.start();
    }
    private void stopRecord() {
        recorder.stop();
        recorder.release();
        recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
    }
}
