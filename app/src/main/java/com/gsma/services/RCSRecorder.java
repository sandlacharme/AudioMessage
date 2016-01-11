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

    public void setMaxaudioDuration(long maxaudioDuration) {
        this.maxaudioDuration = maxaudioDuration;
    }

    private long maxaudioDuration=100000;

    public String getOutputFile() {
        return outputFile;
    }

    @Override
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    private String outputFile = null;
    MediaRecorder currentRecord = null;


    public RCSRecorder(final AppCompatActivity app)
    {

        currentRecord=new MediaRecorder();
        currentRecord.reset();
        currentRecord.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        currentRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        currentRecord.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        ;
        //recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //  currentRecord.setAudioEncoder(MediaRecorder.getAudioSourceMax());
        randomFilename();
        currentRecord.setOutputFile(outputFile);
        currentRecord.setMaxDuration((int)maxaudioDuration); // 10 seconds
        currentRecord.setOnInfoListener(new OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Log.v("VIDEOCAPTURE", "Maximum Duration Reached");
                    mr.stop();
                    mr.release();

                }
            }
        });


    }
    public void launchRecord ()
    {
        try {
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

    public void stopRecord() {
        try {
            currentRecord.stop();

        } catch (RuntimeException e) {
            //mFile.delete();  //you must delete the outputfile when the recorder stop failed.
        } finally {

            currentRecord.reset();
            currentRecord.release();
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

    public long getAudioDuration()
    {



    }
    public long getMaxAudioDuration()
    {
        return maxaudioDuration;


    }

}
