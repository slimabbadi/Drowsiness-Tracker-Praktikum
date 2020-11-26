/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.java.facedetector;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.demo.GraphicOverlay;
import com.google.mlkit.vision.demo.java.FaceModel;
import com.google.mlkit.vision.demo.java.VisionProcessorBase;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import java.util.List;
import java.util.Locale;

/** Face Detector Demo. */
public class FaceDetectorProcessor extends VisionProcessorBase<List<Face>> {

  private static final String TAG = "FaceDetectorProcessor";

  private final FaceDetector detector;
  private FaceModel faceModel;

  public FaceDetectorProcessor(Context context) {
    this(
        context,
        new FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .enableTracking()
            .build());
  }

  private Context context;
  public FaceDetectorProcessor(Context context, FaceDetectorOptions options) {
    super(context);
    this.context = context;
    detector = FaceDetection.getClient(options);
    faceModel = new FaceModel();

    //TODO die beiden Methoden showDialog() und sendBeep() zu faceModel hinzufügen

  }

  @Override
  public void stop() {
    super.stop();
    detector.close();
  }

  @Override
  protected Task<List<Face>> detectInImage(InputImage image) {
    return detector.process(image);
  }

  @Override
  protected void onSuccess(@NonNull List<Face> faces, @NonNull GraphicOverlay graphicOverlay) {
    //Geht derzeit nur bei einem Gesicht
    for (Face face : faces) {
      graphicOverlay.add(new FaceGraphic(graphicOverlay, face, faceModel));

      if(face != null) {
        if(face.getLeftEyeOpenProbability() != null && face.getRightEyeOpenProbability() != null) {

          faceModel.updateEyeOpenProbabilities(face.getLeftEyeOpenProbability(),
                  face.getRightEyeOpenProbability());
        }
      }
    }
  }


  private void showDialog(){

    // TODO: Erzeuge eine visuelle Komponente, um den Fahrer auf seine Schläfrigkeit hinzuweisen.


  }

  private void sendBeep(){

    //TODO: Erzeuge einen Signalton, um den Fahrer auf seine Schläfrigkeit hinzuweisen.

  }

  @Override
  protected void onFailure(@NonNull Exception e) {
    Log.e(TAG, "Face detection failed " + e);
  }
}
