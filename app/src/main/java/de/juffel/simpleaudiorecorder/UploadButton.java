package de.juffel.simpleaudiorecorder;

import android.content.Context;
import android.util.AttributeSet;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Julian on 08/06/15.
 */
public class UploadButton extends ToggleStateButton {

    // this constructor is called, when a RecordButton is created in code (just for completeness)
    public UploadButton(Context context) {
        super(context);
    }

    // this constructor is called, when a RecordButton is declared via XML
    public UploadButton(Context context, AttributeSet attrs) {
        super(context);

        this.setAnimations(R.drawable.ausrufe_kommt, R.drawable.ausrufe);
    }

    @Override
    public void toggle() {
        if (super.getState()) {
            startUpload();
        }
        super.toggle();
    }

    /**
     * Uploads the current recording to the server using the AsyncHttpClient Library
     */
    private void startUpload() {
        String url = RecordActivity.SERVER_URL;
        String file_path = RecordActivity.FILENAME;

        // src: http://loopj.com/android-async-http/ @ Uploading Files with RequestParams
        // gather parameters and upload file
        File file = new File(file_path);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        // send request
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                // parse token from response bytes
                try {
                    String token = new String(bytes, "UTF-8");
                    System.out.println("response received with status code " + statusCode + " and token " + token);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // TODO process response token
                UploadButton.super.toEntryState();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("response received with status code " + statusCode);
                UploadButton.super.toEntryState();
            }
        });
    }
}
