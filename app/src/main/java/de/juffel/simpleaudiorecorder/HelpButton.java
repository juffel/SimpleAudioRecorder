package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kai on 6/16/15.
 */
public class HelpButton extends BasicButton {

    public HelpButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.ausrufe_frage_top, R.drawable.senden_wartet, R.drawable.ausrufe_frage_top);

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, HelloActivity.class);

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
                int waitTime = HelpButton.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);

            }
        });

        triggerEntryAnimation();

    }

}
