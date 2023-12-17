package com.litongjava.ai.server.padddle.ocr;

import java.util.Arrays;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.jfinal.aop.annotation.ComponentScan;
import com.litongjava.tio.boot.TioApplication;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@Slf4j
public class OcrServer {

  public static void main(String[] args) throws Exception {

    boolean downloadMode = Arrays.asList(args).contains("--download");
    if (downloadMode) {
      log.info("downloadMode:{}", downloadMode);

      PaddlePaddleOCRV4.INSTANCE.init();
    } else {
      long start = System.currentTimeMillis();
      TioApplication.run(OcrServer.class, args);
      long end = System.currentTimeMillis();
      PaddlePaddleOCRV4.INSTANCE.init();
      System.out.println("started:" + (end - start) + "(ms)");

    }
  }
}