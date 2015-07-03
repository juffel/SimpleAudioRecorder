package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Julian on 03/07/15.
 */
public class ButtonExplainWelcome extends ButtonExplain {
    private Integer resourceId = R.raw.explanation;

    public ButtonExplainWelcome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Integer getAudio() {
        return R.raw.explanation;
    }

    @Override
    public Class getActivity() {
        return ActivityHome.class;
    }
}
