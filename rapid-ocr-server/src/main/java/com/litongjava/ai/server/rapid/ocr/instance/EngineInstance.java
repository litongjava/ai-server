package com.litongjava.ai.server.rapid.ocr.instance;

import com.benjaminwan.ocrlibrary.OcrResult;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.HardwareConfig;

public enum EngineInstance {
  INSTANCE;

  private static InferenceEngine engine;

  static {
    HardwareConfig onnxConfig = HardwareConfig.getOnnxConfig();
    engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4, onnxConfig);
  }

  public static OcrResult runOcr(String imagePath) {
    return engine.runOcr(imagePath);
  }

  // just init
  public static void init() {

  }
}
