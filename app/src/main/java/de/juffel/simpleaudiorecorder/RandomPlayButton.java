package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * Created by kai on 6/16/15.
 */
public class RandomPlayButton extends BasicButton {

    private String url;

    private MediaPlayer player;
    private Boolean playing;

    public RandomPlayButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        playing = false;

        setAnimations(R.drawable.play, R.drawable.playing, R.drawable.play);

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

    public void setResourceUrl(String url) {
        this.url = url;
    }

    private void startPlay() {
        System.out.println("starting to play story from url " + url.toString());
        // MediaPlayer player = MediaPlayer.create(getContext(), uri);
        MediaPlayer player = new MediaPlayer();
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
                // TODO do something
                RandomPlayButton.super.performClick();
            }
        });
        player.start();
    }

    private void stopPlay() {
        if (player != null) {
            player.release();
            player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
        }
    }
}
