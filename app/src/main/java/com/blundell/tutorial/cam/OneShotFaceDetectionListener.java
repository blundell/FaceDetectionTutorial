package com.blundell.tutorial.cam;

import android.hardware.Camera;

/**
 * Manage the android face detection callbacks to be more ON, OFF than real time ON ON ON ON OFF
 */
public class OneShotFaceDetectionListener implements Camera.FaceDetectionListener {

    private static final int UPDATE_SPEED = 100;
    private static final int UPDATE_SPEED_UNITS = 1000;

    private final Listener listener;

    private boolean timerComplete = true;

    OneShotFaceDetectionListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * The Android API call's this method over and over when a face is detected
     * the idea here is that we de-bounce all these calls, so that we get 1 callback when
     * a face is detected and 1 callback when it is lost
     * <p/>
     * i.e.
     * face, face, face, face, face, no face, face, face, face
     * becomes
     * face, no face, face
     */
    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 0) {
            return;
        }

        tickFaceDetectionSession();
        if (sameFaceDetectionSession()) {
            return;
        }
        startFaceDetectionSession();
        listener.onFaceDetected();
    }

    private RestartingCountDownTimer tickFaceDetectionSession() {
        return timer.startOrRestart();
    }

    private boolean sameFaceDetectionSession() {
        return !timerComplete;
    }

    private void startFaceDetectionSession() {
        timerComplete = false;
    }

    private RestartingCountDownTimer timer = new RestartingCountDownTimer(UPDATE_SPEED, UPDATE_SPEED_UNITS) {
        @Override
        public void onFinish() {
            completeFaceDetectionSession();
            listener.onFaceTimedOut();
        }
    };

    private void completeFaceDetectionSession() {
        timerComplete = true;
    }

    interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();
    }
}
