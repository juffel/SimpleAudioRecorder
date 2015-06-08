package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
        // initialize button with zero frame of animation in setbuttonAnimations
    }

    public Boolean getState() {
        return state;
    }

    /**
     * Make sure animations were set via setButtonAnimations(...) before calling this method.
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
     */
    public void setButtonAnimations(ButtonAnimation entry, ButtonAnimation other) {
        this.entryState = entry;
        this.otherState = other;
        // initialize button
        state = true;
        // call entry animation of otherState
        this.setBackgroundResource(otherState.getEntryAnimation());
        AnimationDrawable anim = (AnimationDrawable) this.getBackground();
        anim.stop(); // maybe unnecessary
        anim.start();
    }
}
