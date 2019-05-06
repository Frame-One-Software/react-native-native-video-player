package com.wog.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

/**
 * Created by nhbao on 9/7/2016.
 */
public class VideoPlayerModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    public final int VIDEO_CODE = 1;

    public VideoPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "VideoPlayerManager";
    }

    @ReactMethod
    public void showVideoPlayer(String url) {
        Activity currentActivity = getCurrentActivity();
        if ((currentActivity != null) && url.startsWith("http") ) {
            Intent videoIntent = new Intent(Intent.ACTION_VIEW);
            videoIntent.setDataAndType(Uri.parse(url), "video/*");
            currentActivity.startActivityForResult(videoIntent, VIDEO_CODE);
        } else {
            final File videoFile = new File(url);
            Uri fileUri = FileProvider.getUriForFile(this.getReactApplicationContext(), "com.grappleartcontainer.fileprovider", videoFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//DO NOT FORGET THIS EVER
            currentActivity.startActivityForResult(intent, VIDEO_CODE);
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CODE) {
            getCurrentActivity().finish();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
