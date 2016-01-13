package com.gsma.services.rcs;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android

import com.gsma.services.FileDialog;
import com.gsma.services.RCSMediaPlayer;
import com.gsma.services.RCSRecorder;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button brecord;
    Button bplay;
    Button bstop;
    EditText chrono;
    boolean recording = false;
    AppCompatActivity app;
    final static  RCSMediaPlayer player = new  RCSMediaPlayer();
    final static   RCSRecorder record = new RCSRecorder();
    /**
     * Event called when record duration is reached
     */
    public void endRecordingDuration() {
        bstop.setEnabled(false);
        brecord.setEnabled(true);
        brecord.setText("Duration reached : 10 min");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app=this;
        setContentView(R.layout.activity_main);
        Log.w("RCS", "Path " + Environment.getExternalStorageDirectory().getAbsolutePath());
        //recorder = record;
        bplay = (Button) findViewById(R.id.buttonPlay);
        bstop = (Button) findViewById(R.id.buttonStop);
        brecord = (Button) findViewById(R.id.buttonRecord);
        chrono = (EditText) findViewById(R.id.chronometer);
        bstop.setEnabled(false);
        brecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bstop.setEnabled(true);
                    record.launchRecord();
                    recording = true;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                brecord.setEnabled(false);
                bplay.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }

        });

        bstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recording) {
                    record.stopRecord();
                    recording = false;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                }

                if (!brecord.isEnabled()) brecord.setEnabled(true);
                if (!bplay.isEnabled()) bplay.setEnabled(true);
                if (!bstop.isEnabled()) bstop.setEnabled(true);
            }
        });

        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the file from a list or the last record

               // player.play("/sdcard/audiorecords/recording7624.3gp");
                File mPath = new File ("/sdcard/audiorecords/");
                final String filePath;
                FileDialog fileDialog = new FileDialog(app,mPath);
                fileDialog.setFileEndsWith(".3gp");
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {
                        Log.d(getClass().getName(), "selected file " + file.toString());
                        try {
                            //if (brecord.isEnabled()) brecord.setEnabled(false);
                            //if (bplay.isEnabled()) bplay.setEnabled(false);
                             player.play(file.toString());
                            Log.w("RCSMEDIAPLAYER FILE TO PLAY", "/sdcard/audiorecords/" + file.toString());
                        } catch (Exception e) {
                        }
                    }
                });
                fileDialog.showDialog();
                //TODO Display duration
                brecord.setEnabled(true);
                bstop.setEnabled(true);
                bplay.setEnabled(true);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
        record.release();

    }



}
