package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by Julian on 15/06/15.
 */
public class ExplainButton extends BasicButton {

    private MediaPlayer player;
    private static final String filename = "erklaerbaer";

    public ExplainButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // set exclamation mark animations, exit animation is null for now
        setAnimations(R.drawable.ausrufezeichen_kommt, R.drawable.ausrufezeichen, null);

        // create MediaPlayer from "raw" resource audio file
        player = MediaPlayer.create(context, R.raw.explanation);
        /*
        // when the player finishes playing, switch to Home Activity
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            // after completion of the animation, reset button to initial status
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO build Intent and switch Activity
                ExplainButton.super.triggerExitAnimation();
            }
        });
        */

        // install clickhandler, play explanatory audio file on click
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // play idle animation while explation is playing
                ExplainButton.super.triggerIdleAnimation();
                // play explanation with Mediaplayer
                player.start();
            }
        });

        // play entry animation
        this.triggerEntryAnimation();
    }
}
