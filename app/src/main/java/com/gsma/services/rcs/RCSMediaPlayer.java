package com.gsma.services.rcs;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sandrine on 12/01/2016.
 */
public class RCSMediaPlayer extends MediaPlayer{

    public Boolean getbReset() {
        return bReset;
    }

    public  Observer os;


    Boolean bReset = false;


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

    public void setObserver(Observer o)
    {
        os=o;
    }
    public void initPlayer(String outputfile)
    {
        try {
            //reset();
            bReset=false;
            setDataSource(outputfile);
            prepareAsync();


        }catch (Exception e){}
    }

    /**
     *
     * @param outputfile
     * @param bPlay : because this method is used even if there is no file palaying just fro getting duration for a record : bool true : play mode; false record mode
     */
    public void play (String outputfile, final boolean bPlay)
    {
        initPlayer(outputfile);
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                try {
                    if(bPlay) {
                        setVolume(1.0f, 1.0f);
                        mp.start();
                    }
                        int duration = getDuration();//ms
                        Log.v("audioDurationRecord","onPrepared " );
                        if(duration>0) {
                            min = (int) ((duration / 1000) / 60) % 60;
                            sec = (int) ((duration /1000 ) % 60);
                            os.NotifyDuration(min, sec);
                        }
                    if(!bPlay) {
                        mp.reset();bReset=true;
                    }
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
                bReset=true;
                Log.v("RCSMediaPlayer", "OnCompletionListener");
            }
        });


    }

/**
    public void audioDuration()
    {

        int duration = this.getDuration();//ms
        if(duration>0) {
            this.min = (int) ((duration / 1000) / 60) % 60;
            this.sec = (int) ((duration /1000 ) % 60);

        }


    }


    public void audioDurationRecord(String outputfile)
    {
        initPlayer(outputfile);
        final RCSMediaPlayer that=this;
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                try {
                    int duration = that.getDuration();//ms

                    Log.v("audioDurationRecord","onPrepared " );
                    if(duration>0) {
                        that.min = (int) ((duration / 1000) / 60) % 60;
                        that.sec = (int) ((duration /1000 ) % 60);

                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Log.v(this.toString(), "Illegal state exception thrown in start.");
                }
            }
        });

        reset();
        bReset=true;

    }*/

    public void stopPlay() {
        try {
            if(!bReset) {
                stop();
                reset();
                Log.v("RCSMediaPlayer", "stopPlay");
            }
        } catch (RuntimeException e) {
            //mFile.delete();  //you must delete the outputfile when the recorder stop failed.
        } finally {


            //currentRecord.release();

        }


    }





}
