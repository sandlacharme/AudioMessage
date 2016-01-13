package com.gsma.services;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sandrine on 12/01/2016.
 */
public class RCSMediaPlayer extends MediaPlayer{

    public int getMin() {
        return min;
    }

    private int min;

    public int getSec() {
        return sec;
    }

    private int sec;


    public RCSMediaPlayer()
    {
        super();


    }

    public void initPlayer(String outputfile)
    {
        try {
            //reset();
            setDataSource(outputfile);
            prepare();


        }catch (Exception e){}
    }

    public void play (String outputfile)
    {
          initPlayer(outputfile);

        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                try {
                    setVolume(1.0f, 1.0f);
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Log.v(this.toString(), "Illegal state exception thrown in start.");
                }
            }
        });

        setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.reset();
            }
        });

          //start();


    }


    public void audioDuration()
    {
        int duration = this.getDuration();//ms
        if(duration>0) {
            this.min = (int) (duration / 1000) % 60;
            this.sec = (int) ((duration / (1000 * 60)) % 60);

        }


    }





}
