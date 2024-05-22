package com.litongjava.ai.server.rapid.ocr;

import com.litongjava.ai.server.rapid.ocr.config.RapidOcrConfig;
import com.litongjava.tio.boot.TioApplication;

public class RapidOcrServer {
  public static void main(String[] args) throws Exception {

    long start = System.currentTimeMillis();
    TioApplication.run(RapidOcrServer.class, new RapidOcrConfig(),args);
    long end = System.currentTimeMillis();
    System.out.println("started:" + (end - start) + "(ms)");
  }

}