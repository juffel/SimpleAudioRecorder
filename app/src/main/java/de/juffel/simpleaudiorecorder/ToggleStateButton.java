package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Custom Button-class that contains two states, keeps track of its state and manages and displays
 * transition animations on state changes.
 * Created by Julian on 08/06/15.
 */
public class ToggleStateButton extends Button {
    /**
     * These two ButtonAnimations contain the Animation Objects, that are displayed, when either
     * state is being entered.
     */
    private ButtonAnimation entryState, otherState;
    /**
     * Boolean encoding the current state of the button, true means entryState, false means
     * otherState, initialized with true.
     */
    private Boolean state;

    public ToggleStateButton(Context context) {
        super(context);
        // initialize button is initialized in setAnimations
    }

    public Boolean getState() {
        return state;
    }

    /**
     * Make sure animations were set via setAnimations(...) before calling this method.
     */
    public void toggle() {
        if (state) {
            // toggle state
            state = false;
            // call exit animation of entryState
            this.setBackgroundResource(entryState.getEntryAnimation());
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            anim.stop(); // maybe unnecessary
            anim.start();
        } else {
            // toggle state
            state = true;
            // call entry animation of otherState
            this.setBackgroundResource(otherState.getEntryAnimation());
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            anim.stop(); // maybe unnecessary
            anim.start();
        }
    }

    /**
     * Set the animations for this Button. Make sure these are set before calling toggle().
     * @param entry of type Integer represents the resource id, e.g.: R.drawable.animation_entry
     *              is taken as the default state of the button
     * @param other of type Integer represents the resource id, e.g.: R.drawable.animation_entry
     *              is taken as the second state of the button
     */
    public void setAnimations(Integer entry, Integer other) {
        this.entryState = new ButtonAnimation(entry);
        this.otherState = new ButtonAnimation(other);
        // initialize button
        state = true;

        // set own onclicklistener
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        // call entry animation of otherState
        this.setBackgroundResource(otherState.getEntryAnimation());
        AnimationDrawable anim = (AnimationDrawable) this.getBackground();
        anim.stop(); // maybe unnecessary
        anim.start();
    }
}
