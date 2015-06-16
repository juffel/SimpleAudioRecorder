package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kai on 6/16/15.
 */
public class ReturnButton extends BasicButton {

    public ReturnButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.button_play, R.drawable.button_play_animated, R.drawable.button_play_animated);

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
                int waitTime = ReturnButton.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);

            }
        });

        triggerEntryAnimation();

    }

}
