package com.gsma.services.rcs;

import android.media.MediaPlayer;

/**
 * Created by sandrine on 14/01/2016.
 */
public class Observer {


    MainActivity sujetActivity;
    RCSMediaPlayer play;
    RCSRecorder recorder;

    Observer(MainActivity a, RCSMediaPlayer pl, RCSRecorder rec)
    {
        sujetActivity=a;
        recorder=rec;
        rec.setObserver(this);
        play=pl;
        pl.setObserver(this);

    }


    public void detach()
    {
        sujetActivity=null;
        recorder=null;
        play=null;
    }

    public void NotifyDuration(int min, int sec)
    {
        sujetActivity.setDuration(play.getMin(),play.getSec());
    }
    public void NotifyEndDuration()
    {
        sujetActivity.endRecordingDuration();
    }
}
