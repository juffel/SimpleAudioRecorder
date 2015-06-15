package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Julian on 15/06/15.
 */
public class ExplainButton extends ToggleStateButton {
    // this constructor is called, when a RecordButton is created in code (just for completeness)
    public ExplainButton(Context context) {
        super(context);
    }

    // this constructor is called, when a RecordButton is declared via XML
    public ExplainButton(Context context, AttributeSet attrs) {
        super(context);

        // set this Button's animations
        this.setAnimations(R.drawable.ausrufezeichen_kommt, R.drawable.button_record_animated);
    }
}
