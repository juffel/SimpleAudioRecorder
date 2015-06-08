package de.juffel.simpleaudiorecorder;

import android.graphics.drawable.AnimationDrawable;

/**
 * Animation of a State of a ToggleStateButton, containing animations for state entry and state exit.
 * Created by Julian on 08/06/15.
 */
public class ButtonAnimation {
    private Integer entryAnimation;

    public ButtonAnimation() {
    }

    public Integer getEntryAnimation() {
        return entryAnimation;
    }

    public void setEntryAnimation(Integer entryAnimation) {
        this.entryAnimation = entryAnimation;
    }
}
