package com.blundell.tutorial.cam;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Manages the android camera and sets it up for face detection
 * can throw an error if face detection is not supported on this device
 */
public class FaceDetectionCamera implements OneShotFaceDetectionListener.Listener {

    private final Camera camera;

    private Listener listener;

    public FaceDetectionCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Use this to detect faces without an on screen preview
     *
     * @param listener the {@link FaceDetectionCamera.Listener} for when faces are detected
     */
    public void initialise(Listener listener) {
        initialise(new DummySurfaceHolder(), listener);
    }

    private void initialise(SurfaceHolder holder, Listener listener) {
        this.listener = listener;
        try {
            camera.stopPreview();
        } catch (Exception swallow) {
            // ignore: tried to stop a non-existent preview
        }
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            camera.setFaceDetectionListener(new OneShotFaceDetectionListener(this));
            camera.startFaceDetection();
        } catch (IOException e) {
            this.listener.onFaceDetectionNonRecoverableError();
        }
    }

    @Override
    public void onFaceDetected() {
        listener.onFaceDetected();
    }

    @Override
    public void onFaceTimedOut() {
        listener.onFaceTimedOut();
    }

    public void recycle() {
        if (camera != null) {
            camera.release();
        }
    }

    public interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();

        void onFaceDetectionNonRecoverableError();

    }
}
