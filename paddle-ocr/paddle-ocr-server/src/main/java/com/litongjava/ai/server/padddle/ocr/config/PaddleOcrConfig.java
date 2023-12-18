package com.litongjava.ai.server.padddle.ocr.config;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.jfinal.aop.annotation.BeforeStartConfiguration;
import com.litongjava.jfinal.aop.annotation.Initialization;

@BeforeStartConfiguration
public class PaddleOcrConfig {
  @Initialization
  public void initOcr() {
    PaddlePaddleOCRV4.INSTANCE.init();
  }
}
