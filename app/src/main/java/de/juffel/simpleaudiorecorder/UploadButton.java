package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by kai on 6/16/15.
 */
public class UploadButton extends BasicButton {

    private static String file_path;

    public UploadButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.senden_kommt, R.drawable.senden_wartet, R.drawable.senden_kommt);

        file_path = context.getFilesDir() + RecordActivity.FILENAME;

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // startUpload();
                String url = RecordActivity.SERVER_URL;

                // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
                // gather parameters and upload file
                File file = new File(file_path);
                RequestParams params = new RequestParams();
                try {
                    params.put("file", file);
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }

                Context context = getContext();

                // send request
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                        System.out.println("response received with status code " + statusCode);
                        UploadButton.super.triggerExitAnimation();

                        processResponse(bytes);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                        System.out.println("response received with status code " + statusCode);
                        UploadButton.super.triggerEntryAnimation();
                    }
                });
            }
        });

        triggerEntryAnimation();
    }

    /**
     * Uploads the current recording to the server using the AsyncHttpClient Library
     */
    private void startUpload() {
        String url = RecordActivity.SERVER_URL;

        // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
        // gather parameters and upload file
        File file = new File(file_path);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        final Context context = getContext();

        // send request
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                System.out.println("response received with status code " + statusCode);
                UploadButton.super.triggerExitAnimation();

                // processResponse(bytes);

                String token = null;
                try {
                    token = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                final Intent intent = new Intent(context, ByeActivity.class);
                intent.putExtra("token", token);

                context.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("response received with status code " + statusCode);
                UploadButton.super.triggerEntryAnimation();

                // mabe error handling
            }
        });
    }

    /**
     * Parses the Token from the received bytes and starts the ByeActivity
     */
    private void processResponse(byte[] bytes) {
        String token = null;
        try {
            token = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final Intent intent = new Intent(getContext(), ByeActivity.class);
        intent.putExtra("token", token);

        // we start the next Activity from a separate thread, so that we can properly wait for
        // the Animation to end first.
        Runnable startNext = new Runnable() {
            @Override
            public void run() {
                System.out.println("Started to run");
                UploadButton.super.getContext().startActivity(intent);
            }
        };
        Handler delayHandler = new Handler();
        // play exit animation
        int waitTime = UploadButton.super.triggerExitAnimation();
        delayHandler.postDelayed(startNext, waitTime);

    }

}
