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

import com.gsma.services.rcs.FileDialog;
import com.gsma.services.rcs.RCSMediaPlayer;
import com.gsma.services.rcs.RCSRecorder;
import com.gsma.services.rcs.Observer;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button brecord;
    Button bplay;
    Button bstop;
    EditText chrono;
    boolean recording = false;
    boolean playing = false;
    AppCompatActivity app;
    Observer obs;



    final static  RCSMediaPlayer player = new  RCSMediaPlayer();
    final static   RCSRecorder record = new RCSRecorder();

    /**
     * Event called when record duration is reached
     */
    public void endRecordingDuration() {
        bstop.setEnabled(true);
        brecord.setEnabled(true);
        bplay.setEnabled(true);
        Toast.makeText(getApplicationContext(), "End of Duration ", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app=this;
        obs=new Observer(this,player,record);
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
                    chrono.setText("");
                    bstop.setEnabled(true);
                    bplay.setEnabled(false);
                    brecord.setEnabled(false);
                    record.launchRecord();
                    recording = true;
                    playing = false;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }

        });

        bstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recording) {
                    record.stopRecord(player);
                    player.play(record.getOutputFile(), false);
                    recording = false;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                }
                if (playing) {
                    player.stopPlay();
                    playing = false;
                    chrono.setText("");
                }

                brecord.setEnabled(true);
                bplay.setEnabled(true);
                bstop.setEnabled(true);
            }
        });

        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(playing)
                {
                    player.stopPlay();
                    playing=false;
                }
                File mPath = new File("/sdcard/audiorecords/");
                final String filePath;
                FileDialog fileDialog = new FileDialog(app, mPath);
                fileDialog.setFileEndsWith(".3gp");
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {
                        Log.d(getClass().getName(), "selected file " + file.toString());
                        try {
                            player.play(file.toString(), true);
                            playing = true;

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


    public  void setDuration(int min, int sec)
    {
        chrono.setText("Duration : " +min+ "mins" + sec + "secs");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        record.release();
        obs.detach();

    }

}
