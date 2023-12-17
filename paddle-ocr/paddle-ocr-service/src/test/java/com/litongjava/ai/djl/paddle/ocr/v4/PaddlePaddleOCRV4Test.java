package com.litongjava.ai.djl.paddle.ocr.v4;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaddlePaddleOCRV4Test {

  @Test
  public void test() {
    String url = "https://resources.djl.ai/images/flight_ticket.jpg";
    String text = null;
    try {
      text = PaddlePaddleOCRV4.INSTANCE.ocr(url);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    log.info("text:{}", text);
  }

}
