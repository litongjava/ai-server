package com.litongjava.ai.server;

import com.jfinal.server.undertow.UndertowServer;
import com.litongjava.ai.server.config.BaseConfig;

public class AiServerJfinalApp {
  public static void main(String[] args) {
    UndertowServer.start(BaseConfig.class);
  }
}
