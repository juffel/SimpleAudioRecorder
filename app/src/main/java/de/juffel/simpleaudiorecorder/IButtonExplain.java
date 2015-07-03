package de.juffel.simpleaudiorecorder;

/**
 * Created by Julian on 03/07/15.
 */
public interface IButtonExplain {

    /**
     * Override this method to 'set' this Button's audio resource.
     */
    public Integer getAudio();

    /**
     * Override this method to 'set' the activity that is entered after playback has finished.
     */
    public Class getActivity();
}
