package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class ButtonPlay extends ButtonBasic {

    private static String file_path;

    private MediaPlayer player;
    private Boolean playing;

    public ButtonPlay(final Context context, AttributeSet attrs) {
        super(context, attrs);

        playing = false;

        setAnimations(R.drawable.play, R.drawable.playing, R.drawable.play);

        file_path = context.getFilesDir() + ActivityZiegel.FILENAME;

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    playing = false;
                    stopPlay();
                    triggerEntryAnimation();
                } else {
                    playing = true;
                    startPlay();
                    triggerIdleAnimation();
                }

            }

        });
        triggerEntryAnimation();
    }

    private void startPlay() {
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            /**
             * after completion of the animation, reset button to initial status
             * @param mp
             */
            @Override
            public void onCompletion(MediaPlayer mp) {
                // trigger click on completion, so the button returns to it's initial state
                ButtonPlay.super.performClick();
            }
        });
        try {
            player.setDataSource(file_path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlay() {
        if (player != null) {
            player.release();
            player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }
}
