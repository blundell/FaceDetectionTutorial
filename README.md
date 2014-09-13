FaceDetectionTutorial
=====================

Explantory tutorial can be found here: http://blog.blundell-apps.com/tut-front-camera-face-detection-explained/

Load your front facing camera like this

`FrontCameraRetriever.retrieveFor(context);`

Listen for face detection like this

```java
camera.initialise(new FaceDetectionCamera.Listener() {
       @Override
       public void onFaceDetected() {
           
       }

       @Override
       public void onFaceTimedOut() {

       }

       @Override
       public void onFaceDetectionNonRecoverableError() {

       }
   });
```
