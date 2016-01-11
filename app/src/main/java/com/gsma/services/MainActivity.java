package com.gsma.services.rcs;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android

import com.gsma.services.DurationListener;
import com.gsma.services.RCSRecorder;
import com.gsma.services.RecordEventManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button brecord;
    Button bplay;
    Button bstop;
    RCSRecorder record;


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
        setContentView(R.layout.activity_main);
         record= new RCSRecorder(this);
        Log.w("RCS", "Path " + Environment.getExternalStorageDirectory().getAbsolutePath());
        //recorder = record;
        //bplay=(Button)findViewById(R.id.button3);
         bstop=(Button)findViewById(R.id.buttonStop);
         brecord=(Button)findViewById(R.id.buttonRecord);
         bstop.setEnabled(false);
       /* ActionListener monActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("bouton cliqué");
            }
        };*/
        brecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Record audioFile

                try {

                    record.launchRecord();
                    bstop.setEnabled(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                brecord.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }

        });

        bstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                record.stopRecord();
                brecord.setEnabled(true);
                bstop.setEnabled(false);
                //bplay.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }

        });

    }


}
