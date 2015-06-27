package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class ButtonRandomPlay extends ButtonBasic {

    private String url;

    private MediaPlayer player;
    private Boolean playing;

    public ButtonRandomPlay(final Context context, AttributeSet attrs) {
        super(context, attrs);

        playing = false;
        setAnimations(R.drawable.play, R.drawable.playing, R.drawable.play);

        // install clickhandler
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    triggerEntryAnimation();
                    stopPlay();
                } else {
                    triggerIdleAnimation();
                    startPlay();
                }
            }

        });
        triggerEntryAnimation();
    }

    public void setResourceUrl(String url) {
        this.url = url;
    }

    public void startPlay() {
        playing = true;
        System.out.println("starting to play story from url " + url.toString());
        // MediaPlayer player = MediaPlayer.create(getContext(), uri);
        player = new MediaPlayer();
        try {
            player.setDataSource(this.url);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("done playing");
                // trigger click on stop, to reenter idle state
                ButtonRandomPlay.super.performClick();
            }
        });
        player.start();
    }

    public void stopPlay() {
        playing = false;
        if (player != null) {
            player.release();
            player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }
}
