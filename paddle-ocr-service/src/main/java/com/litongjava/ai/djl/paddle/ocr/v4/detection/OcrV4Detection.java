package com.litongjava.ai.djl.paddle.ocr.v4.detection;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import ai.djl.modality.cv.Image;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.training.util.ProgressBar;
import cn.hutool.core.io.resource.ResourceUtil;

/**
 * 文字检测
 */
public final class OcrV4Detection {
  /**
   * 中文文本检测
   *
   * @return
   */
  public Criteria<Image, NDList> chDetCriteria() {
    URL resource = ResourceUtil.getResource("models/ch_PP-OCRv4_det_infer/inference.onnx");
    Path modelPath = null;
    try {
      modelPath = Paths.get(resource.toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    
    Criteria<Image, NDList> criteria =
      Criteria.builder()
        .optEngine("OnnxRuntime")
        // .optModelName("inference")
        .setTypes(Image.class, NDList.class)
        .optModelPath(modelPath)
        .optTranslator(new OCRDetectionTranslator(new ConcurrentHashMap<String, String>()))
        .optProgress(new ProgressBar())
        .build();

    return criteria;
  }

}