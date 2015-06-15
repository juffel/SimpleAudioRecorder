package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Button that has only one state but has three animations, that can be triggered. One entry, one
 * idle and one exit Animation.
 * Created by Julian on 15/06/15.
 */
public class BasicButton extends Button {
    private Integer entryAnimation, idleAnimation, exitAnimation;

    public BasicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAnimations(Integer entryAnimation, Integer idleAnimation, Integer exitAnimation) {
        this.entryAnimation = entryAnimation;
        this.idleAnimation = idleAnimation;
        this.exitAnimation = exitAnimation;
    }

    public void triggerEntryAnimation() {
        if (null != entryAnimation) {
            this.setBackgroundResource(entryAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            anim.stop(); // maybe unnecessary
            anim.start();
        }
    }
    public void triggerIdleAnimation() {
        if (null != idleAnimation) {
            this.setBackgroundResource(idleAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            anim.stop(); // maybe unnecessary
            anim.start();
        }

    }
    public void triggerExitAnimation() {
        if (null != exitAnimation) {
            this.setBackgroundResource(exitAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            anim.stop(); // maybe unnecessary
            anim.start();
        }
    }
}
