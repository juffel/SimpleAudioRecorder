package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Julian on 03/07/15.
 */
public class ButtonExplainUploaded extends ButtonExplain {
    public ButtonExplainUploaded(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Integer getAudio() {
        return R.raw.no_audio;
    }

    @Override
    public Class getActivity() {
        return ActivitySleep.class;
    }
}
