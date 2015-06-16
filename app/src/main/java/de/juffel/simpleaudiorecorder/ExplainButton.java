package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by Julian on 15/06/15.
 */
public class ExplainButton extends BasicButton {

    private MediaPlayer player;
    private static final String filename = "erklaerbaer";

    public ExplainButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        // set exclamation mark animations, exit animation is null for now
        setAnimations(R.drawable.ausrufezeichen_kommt, R.drawable.ausrufezeichen, R.drawable.ausrufezeichen_kommt);

        // create MediaPlayer from "raw" resource audio file
        player = MediaPlayer.create(context, R.raw.explanation);
        // when the player finishes playing, switch to Home Activity
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            // after completion of the animation, reset button to initial status
            @Override
            public void onCompletion(MediaPlayer mp) {

                // for testing purposes start any other activity
                final Intent intent = new Intent(context, HomeActivity.class);

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
                int waitTime = ExplainButton.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);
                //context.startActivity(intent);
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
            }
        });

        // play entry animation
        this.triggerEntryAnimation();
    }
}
