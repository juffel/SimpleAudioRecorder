package de.juffel.simpleaudiorecorder;

import android.graphics.drawable.AnimationDrawable;

/**
 * State of a StateButton, containing animations for state entry and state exit.
 * Created by Julian on 08/06/15.
 */
public class ButtonState {
    private AnimationDrawable enterAnimation, exitAnimation;

    public ButtonState() {
        // TODO set enter-/exitAnimation
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
