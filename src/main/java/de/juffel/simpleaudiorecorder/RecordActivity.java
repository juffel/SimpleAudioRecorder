package de.juffel.simpleaudiorecorder;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.ByteBuffer;

// capture Audio Tutorial (via: http://developer.android.com/guide/topics/media/audio-capture.html#audiocapture)
public class RecordActivity extends Activity {

    private static String filename;

    private MediaRecorder recorder;
    private MediaPlayer player;

    private Boolean recording;
    private Boolean playing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // get path for filename
        filename = getFilesDir().getAbsolutePath();
        filename += "/record.3gp";
        System.out.println("output recordings to " + filename);

        ///////////////////////////
        // initialize record button
        final Button recordButton = (Button) findViewById(R.id.button_record);
        OnClickListener recordListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setText(R.string.button_record);
                    stopRecord();
                } else {
                    recordButton.setText(R.string.button_stop_record);
                    startRecord();
                }
                // toggle recording status
                recording = !recording;
            }
        };
        recordButton.setText(R.string.button_record);
        recordButton.setOnClickListener(recordListener);
        recording = false;

        /////////////////////////
        // initialize play button
        final Button playButton = (Button) findViewById(R.id.button_replay);
        OnClickListener playListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle play start/stop
                if (playing) {
                    playButton.setText(R.string.button_replay);
                    stopReplay();
                } else {
                    playButton.setText(R.string.button_stop_replay);
                    startReplay();
                }
                // toggle playing status
                playing = !playing;
            }
        };
        playButton.setText(R.string.button_replay);
        playButton.setOnClickListener(playListener);
        playing = false;

        ///////////////////////////
        // initialize upload button
        final Button uploadButton = (Button) findViewById(R.id.button_upload);
        uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });
    }

    /**
     * Free the audio record and playback resources so they do not eat battery in background
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * Control audio capture (start & stop)
     */
    private void startRecord() {
        // initialize recorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filename);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
    }
    private void stopRecord() {
        recorder.stop();
        recorder.release();
        recorder = null; // dunno why this is necessary but it appears in the tut, so i adopt it
    }

    /**
     * Control audio playback (start & stop)
     */
    private void startReplay() {
        player = new MediaPlayer();
        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void stopReplay() {
        player.release();
        player = null; // dunno why this is necessary but it appears in the tut, so i adopt it
    }

    /**
     * if there is a current recording, this method uploads the file to the for now hardcoded server
     * via: https://developer.android.com/training/basics/network-ops/connecting.html
     */
    private void startUpload() {
        String url = "http://192.168.178.22:3000/audio/put_here";
        String file_path = filename;

        // start new UploadTask
        new UploadTask().execute(url, file_path);
    }

    /**
     * The upload process is performed in a background thread so the ui will be able to react to
     * user input while uploading.
     */
    private class UploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO implement show progress bar in ui
        }

        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call; params[0] is the passed url
            String url_param = params[0];
            String file_param = params[1];

            // check if network connection is available (probably not necessary, but meh, why not
            // and mos of all: what could possibly go wrong?)
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (!(ni != null && ni.isConnected())) {
                // error
                System.out.println("Network availability check failed.");
                return "Network availability check failed.";
            }
            System.out.println("Success! Network ok.");

            AsyncHttpClient client = new AsyncHttpClient();
            File file = new File(file_param);
            RequestParams requestParams = new RequestParams();
            try {
                requestParams.put("filename", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            client.post(url_param, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    System.out.println("Success :)");
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("Failure :(");
                }
            });

            return "Success! File was transferred correctly.";
        }

        // TODO check, if static property is necessary, if not, move into the Upload Task
        final static String MULTIPART_BOUNDARY = "------------------563i2ndDfv2rTHiSsdfsdbouNdArYfORhxcvxcvefj3q2f";
        final static String CRLF = "\r\n";

        @Override
        protected void onPostExecute(String result) {
            // TODO implement task done event
        }
    }
}
