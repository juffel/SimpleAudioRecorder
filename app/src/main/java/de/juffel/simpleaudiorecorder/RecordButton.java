package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.view.View;

import java.io.IOException;

/**
 * Created by Julian on 08/06/15.
 */
public class RecordButton extends ToggleStateButton {

    private MediaRecorder recorder;
    private static String filename;

    public RecordButton(Context context) {
        super(context);

        // create Animations
        ButtonAnimation entry = new ButtonAnimation();
        // sorry for using of a deprecated method, but for now its the easiest approach
        AnimationDrawable animation = (AnimationDrawable) getResources().getDrawable(R.drawable.button_record_idle_exit);
        entry.setEnterAnimation(animation);
        entry.setExitAnimation(animation);

        ButtonAnimation other = new ButtonAnimation();
        // and set them as this Button's animations
        this.setButtonAnimations(entry, other);

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
        super.toggle();
        if (super.getState()) {
            // TODO start recording
        } else {
            // TODO stop recording
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
