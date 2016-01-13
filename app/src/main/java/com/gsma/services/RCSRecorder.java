package com.gsma.services;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RCSRecorder extends MediaRecorder{

    //
    private final long MAX_AUDIO_DURATION=100000;

    public String getOutputFile() {
        return outputFile;
    }

    @Override
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    private String outputFile = null;
     MediaRecorder  currentRecord = null;

    public String getLastRecord() {
        return lastRecord;
    }

    public static  String lastRecord;

    public RCSRecorder()
    {

        currentRecord=new MediaRecorder();
       // currentRecord.reset();



    }


    public void init()
    {
        currentRecord.setAudioSource(AudioSource.VOICE_COMMUNICATION);
        currentRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        currentRecord.setAudioEncoder(AudioEncoder.DEFAULT);

        //recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //currentRecord.setAudioEncoder(MediaRecorder.getAudioSourceMax());
        randomFilename();

        currentRecord.setOutputFile(outputFile);
        currentRecord.setMaxDuration((int)MAX_AUDIO_DURATION); // 10 seconds
        currentRecord.setOnInfoListener(new OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Log.v("VIDEOCAPTURE", "Maximum Duration Reached");
                    mr.stop();
                    mr.reset();

                }
            }
        });
    }

    public void launchRecord ()
    {
        try {

            init();
            currentRecord.prepare();
            currentRecord.start();

        }

        catch (IllegalStateException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void stopRecord(RCSMediaPlayer pl) {
        try {
            currentRecord.stop();


        } catch (RuntimeException e) {
            //mFile.delete();  //you must delete the outputfile when the recorder stop failed.
        } finally {

            currentRecord.reset();
            //currentRecord.release();
            lastRecord=outputFile;
        }


    }


    /**
     * GEnerate a random Filename for audiofiles
     */
    public void randomFilename() {

        int lower = 1;
        int higher = 10000;
        int random = (int) (Math.random() * (higher - lower)) + lower;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecords");
        if (!f.exists())
        {
            f.mkdir();

        }
        outputFile=Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecords/recording"+random+".3gp";
        Log.w(" File path and name ",outputFile);
    }

    public final long getMaxAudioDuration()
    {
        return this.MAX_AUDIO_DURATION;


    }





}
