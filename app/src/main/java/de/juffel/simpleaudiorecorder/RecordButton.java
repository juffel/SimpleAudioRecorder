package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by Julian on 08/06/15.
 */
public class RecordButton extends ToggleStateButton {

    private MediaRecorder recorder;
    private static String filename;

    public RecordButton(Context context) {
        super(context);

        // TODO set own onclicklistener
    }

    /**
     * Toggles the state of this Button and adjusts the resource Background
     */
    @Override
    public void toggle() {
        if (super.getState()) {

        } else {

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
}
