package de.juffel.simpleaudiorecorder;

import  android.content.Context;
import android.media.MediaRecorder;
import android.util.AttributeSet;

import java.io.IOException;

/**
 * Created by Julian on 08/06/15.
 */
public class RecordButton extends ToggleStateButton {

    private MediaRecorder recorder;

    // this constructor is called, when a RecordButton is created in code (just for completeness)
    public RecordButton(Context context) {
        super(context);
    }

    // this constructor is called, when a RecordButton is declared via XML
    public RecordButton(Context context, AttributeSet attrs) {
        super(context);

        // set this Button's animations
        this.setAnimations(R.drawable.button_record, R.drawable.button_record_animated);
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
        String filename = RecordActivity.FILENAME;
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
    void stopRecord() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }
}
