package com.litongjava.ai.djl.paddle.ocr.v4;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.litongjava.ai.djl.paddle.ocr.v4.recognition.OcrV4Recognition;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.opencv.OpenCVImageFactory;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import lombok.Cleanup;

public class OcrV4RecTensorExample {
  public static void main(String[] args) {
    Path imageFile = Paths.get("E:\\code\\python\\project-litongjava\\cyg-v2\\img.png");
    Image image;
    try {
      image = OpenCVImageFactory.getInstance().fromFile(imageFile);
      OcrV4Recognition recognition = new OcrV4Recognition();

      @Cleanup
      ZooModel<Image, String> recognitionModel = ModelZoo.loadModel(recognition.chRecCriteria());

      @Cleanup
      Predictor<Image, String> newPredictor = recognitionModel.newPredictor();
      String predict = newPredictor.predict(image);
      System.out.println("result:" + predict);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
