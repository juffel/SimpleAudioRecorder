package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import de.juffel.simpleaudiorecorder.BasicButton;

/**
 * Created by Julian on 22/06/15.
 */
public class BackToRecordButton extends BasicButton {

    public BackToRecordButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.back_to_record, null, null);

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
                        ((AnimationDrawable) BackToRecordButton.super.getBackground()).stop();
                        BackToRecordButton.super.setBackground(null);
                    }
                };
                Handler delayHandler = new Handler();
                // play exit animation
                int waitTime = BackToRecordButton.super.triggerExitAnimation();
                delayHandler.postDelayed(startNext, waitTime);
            }
        });

        triggerEntryAnimation();
    }
}
