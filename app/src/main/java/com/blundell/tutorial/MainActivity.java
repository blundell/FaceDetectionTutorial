package com.blundell.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.blundell.tutorial.cam.FaceDetectionCamera;
import com.blundell.tutorial.cam.FrontCameraRetriever;

/**
 * Don't forget to add the permissions to the AndroidManifest.xml!
 * <p/>
 * <uses-feature android:name="android.hardware.camera" />
 * <uses-feature android:name="android.hardware.camera.front" />
 * <p/>
 * <uses-permission android:name="android.permission.CAMERA" />
 */
public class MainActivity extends Activity implements FrontCameraRetriever.Listener, FaceDetectionCamera.Listener {

    private static final String TAG = "FDT" + MainActivity.class.getSimpleName();

    private TextView helloWorldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloWorldTextView = (TextView) findViewById(R.id.helloWorldTextView);
        // Go get the front facing camera of the device
        // best practice is to do this asynchronously
        FrontCameraRetriever.retrieveFor(this);
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
        // When the front facing camera has been retrieved
        // then initialise it i.e turn face detection on
        camera.initialise(this);
        // If you wanted to show a preview of what the camera can see
        // here is where you would do it
    }

    @Override
    public void onFailedToLoadFaceDetectionCamera() {
        // This can happen if
        // there is no front facing camera
        // or another app is using the camera
        // or our app or another app failed to release the camera properly
        Log.wtf(TAG, "Failed to load camera, what went wrong?");
        helloWorldTextView.setText(getString(R.string.error_with_face_detection));
    }

    @Override
    public void onFaceDetected() {
        helloWorldTextView.setText(getString(R.string.face_detected_message));
    }

    @Override
    public void onFaceTimedOut() {
        helloWorldTextView.setText(getString(R.string.face_detected_then_lost_message));
    }

    @Override
    public void onFaceDetectionNonRecoverableError() {
        // This can happen if
        // Face detection not supported on this device
        // Something went wrong in the Android api
        // or our app or another app failed to release the camera properly
        helloWorldTextView.setText(getString(R.string.error_with_face_detection));
    }
}
