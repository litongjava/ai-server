package com.litongjava.aio.server.tio.controller;

import com.litongjava.tio.http.server.annotation.RequestPath;

@RequestPath(value = "/")
public class IndexController {
  @RequestPath()
  public String respText() {
    return "whisper-asr-server";
  }
}