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

    private int getAnimationTime(AnimationDrawable anim) {
        // Hacky solution for waiting for an animationDrawable to end
        int time = 0;
        for (int i = 0; i < anim.getNumberOfFrames(); i++) {
            time += anim.getDuration(i);
        }
        return time;
    }

    /*each returns the length of it's respective Animation to use for waiting */

    public int triggerEntryAnimation() {
        int waitTime = 0;
        if (entryAnimation != null) {
            this.setBackgroundResource(entryAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            waitTime = getAnimationTime(anim);
            anim.stop(); // maybe unnecessary
            anim.start();
        }
        return waitTime;
    }

    public int triggerIdleAnimation() {
        int waitTime = 0;
        if (idleAnimation != null) {
            this.setBackgroundResource(idleAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            waitTime = getAnimationTime(anim);
            anim.stop(); // maybe unnecessary
            anim.start();
        }
        return waitTime;
    }
    public int triggerExitAnimation() {
        int waitTime = 0;
        if (exitAnimation != null) {
            this.setBackgroundResource(exitAnimation);
            AnimationDrawable anim = (AnimationDrawable) this.getBackground();
            waitTime = getAnimationTime(anim);
            anim.stop(); // maybe unnecessary
            anim.start();
        }
        return waitTime;
    }
}
