package com.gsma.services;

import android.media.MediaPlayer;

import java.io.File;
import java.net.URI;

/**
 * Created by sandrine on 08/01/2016.
 */
public class RCSPlayer extends MediaPlayer{

    File f;
    URI fileURi;

    RCSPlayer()
    {
        super();

    }

    public URI getFileURi() {
        return fileURi;

    }


    @Override
    public int getAudioDuration() {
        return  this.getDuration();
    }
}
