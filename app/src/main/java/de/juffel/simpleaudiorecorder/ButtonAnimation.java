package de.juffel.simpleaudiorecorder;

import android.graphics.drawable.AnimationDrawable;

/**
 * Animation of a State of a ToggleStateButton, containing animations for state entry and state exit.
 * Created by Julian on 08/06/15.
 */
public class ButtonAnimation {
    private AnimationDrawable enterAnimation, exitAnimation;

    public ButtonAnimation(int entry_id, int exit_id) {
    }

    public AnimationDrawable getEnterAnimation() {
        return enterAnimation;
    }

    public void setEnterAnimation(AnimationDrawable enterAnimation) {
        this.enterAnimation = enterAnimation;
    }

    public AnimationDrawable getExitAnimation() {
        return exitAnimation;
    }

    public void setExitAnimation(AnimationDrawable exitAnimation) {
        this.exitAnimation = exitAnimation;
    }
}
