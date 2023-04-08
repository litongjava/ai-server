package com.litongjava.jfinal.web;

import com.jfinal.server.undertow.UndertowServer;
import com.litongjava.jfinal.web.config.BaseConfig;

public class AiServerJfinalApp {
  public static void main(String[] args) {
    UndertowServer.start(BaseConfig.class);
  }
}
