package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Julian on 15/06/15.
 */
public class ExplainButton extends BasicButton {

    private MediaPlayer player;
    private static final String filename = "erklaerbaer";

    public ExplainButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        // set exclamation mark animations, exit animation is null for now
        setAnimations(R.drawable.question, R.drawable.playing, null);
        // if we return to this button make clickable again
        setClickable(true);
        // create MediaPlayer from "raw" resource audio file
        player = MediaPlayer.create(context, R.raw.explanation);
        // when the player finishes playing, switch to Home Activity
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                // for testing purposes start any other activity
                final Intent intent = new Intent(context, ActivityZiegelHome.class);

                // we start the next Activity from a separate thread, so that we can properly wait for
                // the Animation to end first. 
                Runnable startNext = new Runnable() {
                    @Override
                    public void run() {
                        context.startActivity(intent);
                        ((AnimationDrawable)ExplainButton.super.getBackground()).stop();
                        ExplainButton.super.setBackground(null);
                    }
                };
                Handler delayHandler = new Handler();
                // play exit animation
                int waitTime = ExplainButton.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);

            }
        });

        // install clickhandler, play explanatory audio file on click
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // play idle animation while explation is playing
                ExplainButton.super.triggerIdleAnimation();
                // play explanation with Mediaplayer
                player.start();
                // prevent multiclick
                ExplainButton.super.setClickable(false);
            }
        });

        // play entry animation
        this.triggerEntryAnimation();
    }
}
