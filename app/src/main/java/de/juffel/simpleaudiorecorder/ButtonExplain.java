package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * This abstract class implements an interface to force subclasses to implement methods to set the
 * values for the audio resource to be played onclick and the activity that is entered after replay
 * has finished.
 * To set those values implement getAudio() and getActivity() in subclass, return the audio resource
 * id integer to play and the class of the activity to start.
 * Created by Julian on 15/06/15.
 */
public abstract class ButtonExplain extends ButtonBasic implements IButtonExplain {

    private MediaPlayer player;
    private Boolean playing = false;

    public ButtonExplain(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.question, R.drawable.playing, R.drawable.play);

        // create MediaPlayer from "raw" resource audio file
        player = MediaPlayer.create(context, getAudio());
        // when the player finishes playing, switch to Home Activity
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                // for testing purposes start any other activity
                final Intent intent = new Intent(context, getActivity());

                // we start the next Activity from a separate thread, so that we can properly wait for
                // the Animation to end first.
                Runnable startNext = new Runnable() {
                    @Override
                    public void run() {
                        context.startActivity(intent);
                        ((AnimationDrawable) ButtonExplain.super.getBackground()).stop();
                        ButtonExplain.super.setBackground(null);
                    }
                };
                Handler delayHandler = new Handler();
                // play exit animation
                int waitTime = ButtonExplain.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);

            }
        });

        // install clickhandler, play explanatory audio file on click
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    playing = false;
                    player.reset(); // on completion listener will not be triggered
                    Intent intent = new Intent(context, getActivity());
                    context.startActivity(intent);
                    ((AnimationDrawable) ButtonExplain.super.getBackground()).stop();
                    ButtonExplain.super.setBackground(null);
                } else {
                    playing = true;
                    // play idle animation while explation is playing
                    ButtonExplain.super.triggerIdleAnimation();
                    // play explanation with Mediaplayer
                    player.start();
                }
            }
        });

        // play entry animation
        this.triggerEntryAnimation();
    }
}
