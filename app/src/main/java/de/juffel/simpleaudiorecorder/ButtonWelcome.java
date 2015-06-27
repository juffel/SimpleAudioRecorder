package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Julian on 27/06/15.
 */
public class ButtonWelcome extends BasicButton {
    public ButtonWelcome(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.startanimation, null, null);
    }
}
