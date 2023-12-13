package com.litongjava.ai.server.padddle.ocr;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.jfinal.aop.annotation.ComponentScan;
import com.litongjava.tio.boot.TioApplication;

@ComponentScan
public class OcrServer {

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    TioApplication.run(OcrServer.class, args);
    long end = System.currentTimeMillis();
    PaddlePaddleOCRV4.INSTANCE.init();
    System.out.println("started:" + (end - start) + "(ms)");
  }
}