package com.litongjava.aio.server.tio;

import com.litongjava.jfinal.aop.annotation.ComponentScan;
import com.litongjava.tio.boot.TioApplication;

@ComponentScan
public class WhisperAsrServer {

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    // 初始化服务器并启动服务器
    TioApplication.run(WhisperAsrServer.class, args);
    long end = System.currentTimeMillis();
    System.out.println("started:" + (end - start) + "(ms)");
  }
}