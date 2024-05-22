package com.litongjava.ai.server.rapid.ocr.config;

import com.litongjava.ai.server.rapid.ocr.controller.IndexHandler;
import com.litongjava.ai.server.rapid.ocr.controller.RapidOcrHandler;
import com.litongjava.ai.server.rapid.ocr.instance.EngineInstance;
import com.litongjava.tio.boot.context.TioBootConfiguration;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.http.server.router.HttpReqeustSimpleHandlerRoute;

public class RapidOcrConfig implements TioBootConfiguration {
  @Override
  public void config() {
    registerRouter();
    EngineInstance.init();
  }

  public void registerRouter() {
    HttpReqeustSimpleHandlerRoute r = TioBootServer.me().getHttpReqeustSimpleHandlerRoute();
    // 创建handler
    IndexHandler indexHandler = new IndexHandler();
    RapidOcrHandler ocrHandler = new RapidOcrHandler();

    // 添加action
    r.add("/", indexHandler::index);
    r.add("/rapid/ocr/rec", ocrHandler::rec);
    r.add("/rapid/ocr/test", ocrHandler::test);

  }

}
