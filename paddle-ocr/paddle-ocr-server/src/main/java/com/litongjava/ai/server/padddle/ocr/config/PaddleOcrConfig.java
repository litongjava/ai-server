package com.litongjava.ai.server.padddle.ocr.config;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.ai.server.padddle.ocr.controller.IndexHandler;
import com.litongjava.ai.server.padddle.ocr.controller.PaddleOcrHandler;
import com.litongjava.jfinal.aop.annotation.AInitialization;
import com.litongjava.jfinal.aop.annotation.BeforeStartConfiguration;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.http.server.handler.SimpleHttpRoutes;

@BeforeStartConfiguration
public class PaddleOcrConfig {
  @AInitialization
  public void initOcr() {
    // init ocr
    PaddlePaddleOCRV4.INSTANCE.init();
    // init handler

    // 创建simpleHttpRoutes
    SimpleHttpRoutes simpleHttpRoutes = new SimpleHttpRoutes();
    // 创建controller
    IndexHandler indexHandler = new IndexHandler();
    PaddleOcrHandler paddleOcrHandler = new PaddleOcrHandler();

    // 添加action
    simpleHttpRoutes.add("/", indexHandler::index);
    simpleHttpRoutes.add("/paddle/ocr/rec", paddleOcrHandler::rec);
    simpleHttpRoutes.add("/paddle/ocr/test", paddleOcrHandler::test);

    // 将simpleHttpRoutes添加到TioBootServer
    TioBootServer.me().setHttpRoutes(simpleHttpRoutes);

  }

}
