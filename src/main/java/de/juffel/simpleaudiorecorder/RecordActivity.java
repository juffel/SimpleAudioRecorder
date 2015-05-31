package de.juffel.simpleaudiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class RecordActivity extends ActionBarActivity {

    private static String filename;
    private MediaRecorder recorder;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // get path for filename
        Environment.getExternalStorageDirectory().getAbsoluteFile();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void record(View view) {
        // change record to stop button
        Button button = (Button) findViewById(R.id.button_record);
        button.setText(R.string.button_stop_record);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Button button = (Button) findViewById(R.id.button_record);
                button.setText(R.string.button_record);
                // TODO stop and save recording
                recorder.stop();
                recorder.release();
            }
        });

        // capture Audio (via: http://developer.android.com/guide/topics/media/audio-capture.html#audiocapture)
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFile(filename);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replay(View view) {
        // change event handler for button
        Button button = (Button) findViewById(R.id.button_replay);
        button.setText(R.string.button_stop_replay);
        /* button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        }); */

        player = new MediaPlayer();
        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
