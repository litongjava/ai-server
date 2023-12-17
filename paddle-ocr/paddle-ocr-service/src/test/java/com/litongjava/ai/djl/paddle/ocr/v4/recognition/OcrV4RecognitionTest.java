package com.litongjava.ai.djl.paddle.ocr.v4.recognition;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcrV4RecognitionTest {

  @Test
  public void testGetModelPath() {
    // URL resource = ClassUtil.getClassLoader().getResource();
    URL resource = ResourceUtil.getResource("models/ch_PP-OCRv4_rec_infer/inference.onnx");
    log.info("resource:{}", resource);
    Path modelPath = null;
    try {
      modelPath = Paths.get(resource.toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    log.info("modelPath:{}", modelPath);

  }

}
