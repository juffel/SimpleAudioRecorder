package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by Julian on 08/06/15.
 */
public class PlayButton extends ToggleStateButton {

    MediaPlayer player;

    // this constructor is called, when a RecordButton is created in code (just for completeness)
    public PlayButton(Context context) {
        super(context);
    }

    // this constructor is called, when a RecordButton is created in XMl
    public PlayButton(Context context, AttributeSet attrs) {
        super(context);

        // declare Button's animations
        this.setAnimations(R.drawable.button_play, R.drawable.button_play_animated);
    }

    @Override
    public void toggle() {
        // do play and stop stuff here
        if (super.getState()) {
            toOtherState();
        } else {
            toEntryState();
        }
    }

    @Override
    public void toEntryState() {
        stopReplay();
        super.toEntryState();
    }

    @Override
    public void toOtherState() {
        startReplay();
        super.toOtherState();
    }

    /**
     * Control audio playback (start & stop)
     */
    private void startReplay() {
        // stop possibly running mediaplayer
        stopReplay();

        String filename = RecordActivity.FILENAME;
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            /**
             * after completion of the animation, reset button to initial status
             * @param mp
             */
            @Override
            public void onCompletion(MediaPlayer mp) {
                toEntryState();
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
    void stopReplay() {
        if (player != null) {
            player.release();
            player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }
}
