FaceDetectionTutorial
=====================


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
