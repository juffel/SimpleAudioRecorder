package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kai on 6/16/15.
 */
public class PlayButtonNew extends BasicButton {

    private boolean playing;

    public PlayButtonNew(final Context context, AttributeSet attrs) {
        super(context, attrs);

        playing = false;
        setAnimations(R.drawable.button_play, R.drawable.button_play_stop, R.drawable.button_play);

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playing) {
                    triggerEntryAnimation();
                    playing = false;
                } else {
                    triggerIdleAnimation();
                    playing = true;
                }

            }

        });
        triggerEntryAnimation();
    }
}
