package com.litongjava.aio.server.tio;

import com.litongjava.aio.server.tio.config.TioHttpServerApplication;
import org.tio.utils.jfinal.P;

public class AiServerTio {

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    //初始化服务器并启动服务器
    P.use("app.properties");
    TioHttpServerApplication.run(AiServerTio.class,args);
    long end = System.currentTimeMillis();
    System.out.println("启动完成,共使用了:"+(end-start)+"ms");
  }
}