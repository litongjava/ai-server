package com.litongjava.ai.djl.paddle.ocr.v4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.core.Mat;

import com.litongjava.ai.djl.paddle.ocr.v4.common.ImageUtils;
import com.litongjava.ai.djl.paddle.ocr.v4.detection.OcrV4Detection;

import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.opencv.OpenCVImageFactory;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;

public final class OcrV4DetExample {

  private OcrV4DetExample() {
  }

  public static void main(String[] args) throws IOException, ModelException, TranslateException {
    Path imageFile = Paths.get("src/test/resources/2.jpg");
    Image image = OpenCVImageFactory.getInstance().fromFile(imageFile);

    OcrV4Detection detection = new OcrV4Detection();
    try (@SuppressWarnings("rawtypes")
    ZooModel detectionModel = ModelZoo.loadModel(detection.chDetCriteria()); @SuppressWarnings("unchecked")
    Predictor<Image, NDList> detector = detectionModel.newPredictor();
        NDManager manager = NDManager.newBaseManager();) {

      NDList dt_boxes = detector.predict(image);
      // 交给 NDManager自动管理内存
      // attach to manager for automatic memory management
      dt_boxes.attach(manager);

      for (int i = 0; i < dt_boxes.size(); i++) {
        ImageUtils.drawRect((Mat) image.getWrappedImage(), dt_boxes.get(i));
      }
      ImageUtils.saveImage(image, "detect_rect.png", "build/output");
      ((Mat) image.getWrappedImage()).release();
    }
  }
}