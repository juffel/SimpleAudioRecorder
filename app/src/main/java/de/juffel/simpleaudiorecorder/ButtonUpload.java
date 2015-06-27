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
public class ButtonUpload extends ButtonBasic {

    private static String file_path;
    private Boolean upload_done = false;
    private Boolean upload_animation_done = false;
    private String token;

    public ButtonUpload(final Context context, AttributeSet attrs) {
        super(context, attrs);

        setAnimations(R.drawable.send, R.drawable.send_animated, R.drawable.send);

        file_path = context.getFilesDir() + ActivityZiegel.FILENAME;

        // install clickhandler, change Activity
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });

        triggerEntryAnimation();
    }

    /**
     * Handles the upload process to the possibly multiple urls until some upload succeeds.
     */
    private void startUpload() {
        upload(0);
    }

    private void upload(Integer url_index) {
        if (url_index < ActivityZiegel.SERVER_URLS.length) {
            uploadTo(url_index);
        } else {
            // all URLs were tried unsucessfully
            System.out.println("the file upload was unsuccessful to all of these URLs" + ActivityZiegel.SERVER_URLS);
            Integer duration = ButtonUpload.super.triggerEntryAnimation();
        }
    }

    /**
     * Uploads the current recording to the server using the AsyncHttpClient Library
     */
    private void uploadTo(final Integer url_index) {
        String url = ActivityZiegel.SERVER_URLS[url_index] + "/audio/put_here";
        System.out.println("uploading file " + file_path + " to " + url);

        Integer duration = triggerIdleAnimation();
        // the animation must play at least two cycles before ActivityToken is opened, to achieve this:
        // schedule task: after two cycles of the animation check, wether upload has already succeeded
        //    - if it has, then call switchActivity()
        //    - otherwise switchActivity() is called after the Upload has finished successfully
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                upload_animation_done = true;
                switchActivity();
            }
        }, duration * 3);

        // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
        // gather parameters and upload file
        File file = new File(file_path);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Context context = getContext();

        // send request
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                System.out.println("response received with status code " + statusCode);
                upload_done = true;
                processResponse(bytes);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("response received with status code " + statusCode);
                // retry with next url
                upload(url_index+1);
            }
        });
    }

    /**
     * Parses the Token from the received bytes and starts the ActivityZiegelBye
     */
    private void processResponse(byte[] bytes) {
        String token = null;
        try {
            token = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.token = token;
        switchActivity();
    }

    /**
     * Switches to ActivityToken, includes a check, wether the upload has finished yet, if it has not
     * it does not do anything and has to be called after upload finishes.
     */
    private void switchActivity() {
        if (upload_done && upload_animation_done) {
            triggerExitAnimation();

            final Intent intent = new Intent(getContext(), ActivityZiegelBye.class);
            intent.putExtra("token", token);

            getContext().startActivity(intent);
        }
    }
}
