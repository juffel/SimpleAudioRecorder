package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Julian on 23/06/15.
 */
public class TokenButton extends BasicButton {

    public TokenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param digit expected to be integer from the range 0-9
     */
    public void setNumber(Integer digit) {
        Integer res;
        // giant switch to choose the right image according to digit
        switch (digit) {
            case 0:
                res = R.drawable.digit_0;
                break;
            case 1:
                res = R.drawable.digit_1;
                break;
            case 2:
                res = R.drawable.digit_2;
                break;
            case 3:
                res = R.drawable.digit_3;
                break;
            case 4:
                res = R.drawable.digit_4;
                break;
            case 5:
                res = R.drawable.digit_5;
                break;
            case 6:
                res = R.drawable.digit_6;
                break;
            case 7:
                res = R.drawable.digit_7;
                break;
            case 8:
                res = R.drawable.digit_8;
                break;
            case 9:
                res = R.drawable.digit_9;
                break;
            default:
                throw new IllegalArgumentException("only integers from the range 0-9 are allowed");
        }
        super.setBackgroundResource(res);
    }
}
