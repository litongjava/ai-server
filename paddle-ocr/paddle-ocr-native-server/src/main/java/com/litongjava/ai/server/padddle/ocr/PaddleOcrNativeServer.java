package com.litongjava.ai.server.padddle.ocr;

import java.io.IOException;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.tio.http.common.HttpConfig;
import com.litongjava.tio.http.common.handler.HttpRequestHandler;
import com.litongjava.tio.http.server.HttpServerStarter;
import com.litongjava.tio.http.server.handler.HttpRoutes;
import com.litongjava.tio.http.server.handler.SimpleHttpDispatcherHandler;
import com.litongjava.tio.http.server.handler.SimpleHttpRoutes;

public class PaddleOcrNativeServer {

  public static void main(String[] args) throws IOException {

    // init ocr
    PaddlePaddleOCRV4.INSTANCE.init();
    // 实例化Controller
    IndexController controller = new IndexController();
    PaddleOcrController paddleOcrController = new PaddleOcrController();

    // 手动添加路由
    HttpRoutes simpleHttpRoutes = new SimpleHttpRoutes();
    simpleHttpRoutes.add("/", controller::index);

    simpleHttpRoutes.add("/paddle/ocr/test", paddleOcrController::test);
    simpleHttpRoutes.add("/paddle/ocr/rec", paddleOcrController::rec);

    // 配置服务服务器
    HttpConfig httpConfig;
    HttpRequestHandler requestHandler;
    HttpServerStarter httpServerStarter;

    httpConfig = new HttpConfig(80, null, null, null);
    requestHandler = new SimpleHttpDispatcherHandler(httpConfig, simpleHttpRoutes);
    httpServerStarter = new HttpServerStarter(httpConfig, requestHandler);
    // 启动服务器
    httpServerStarter.start();
  }
}