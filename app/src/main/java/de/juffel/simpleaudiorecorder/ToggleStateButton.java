package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Button;

import java.util.List;

/**
 * Custom Button-class that contains two states, keeps track of its state and manages and displays
 * transition animations on state changes.
 * Created by Julian on 08/06/15.
 */
public class ToggleStateButton extends Button {
    private ButtonAnimation entryState, otherState;
    /**
     * Boolean encoding the current state of the button, true means entryState, false means
     * otherState, initialized with true.
     */
    private Boolean state;

    public ToggleStateButton(Context context) {
        super(context);
    }

    public Boolean getState() {
        return state;
    }

    public void toggle() {
        if (state) {
            state = false;
            AnimationDrawable entry = entryState.getExitAnimation();
            AnimationDrawable exit = otherState.getEnterAnimation();
            // TODO call animations
        } else {
            state = true;
            AnimationDrawable entry = otherState.getExitAnimation();
            AnimationDrawable exit = entryState.getEnterAnimation();
            // TODO call animations
        }
    }
}
