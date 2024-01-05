package com.litongjava.ai.djl.paddle.ocr.v4.detection;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import ai.djl.Device;
import ai.djl.modality.cv.Image;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.Criteria.Builder;
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
    URL resource = ResourceUtil.getResource("models/ch_PP-OCRv4_det_infer.zip");
    System.out.println("resource:" + resource);
    Path modelPath = null;
    try {
      modelPath = Paths.get(resource.toURI());
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    Device device = Device.gpu();
    Builder<Image, NDList> builder = Criteria.builder()
        //engine
        .optEngine("OnnxRuntime")
        //.optEngine("PyTorch")
        
        // .optModelName("inference")
        .setTypes(Image.class, NDList.class)
        .optDevice(device)
        .optTranslator(new OCRDetectionTranslator(new ConcurrentHashMap<String, String>()))
        .optProgress(new ProgressBar());

    if (modelPath != null) {
      System.out.println("load from file");
      builder.optModelPath(modelPath).optModelName("ch_PP-OCRv4_det_infer");
    } else {
      System.out.println("load from jar");
      builder.optModelUrls("jar:///models/ch_PP-OCRv4_det_infer.zip");
    }
    Criteria<Image, NDList> criteria = builder.build();
    return criteria;
  }

}