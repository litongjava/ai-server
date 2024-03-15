package com.litongjava.ai.server.padddle.ocr;

import com.benjaminwan.ocrlibrary.OcrResult;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;

public class PaddlePaddleOCRNativeV4Demo {

  public static void main(String[] args) {
    InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
    OcrResult ocrResult = engine.runOcr("E:\\code\\python\\project-litongjava\\cyg-v2\\img.png");
    System.out.println(ocrResult.getStrRes().trim());
  }
}
