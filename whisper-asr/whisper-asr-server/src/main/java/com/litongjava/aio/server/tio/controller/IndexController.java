package com.litongjava.aio.server.tio.controller;

import com.litongjava.jfinal.aop.annotation.Controller;
import com.litongjava.tio.boot.annotation.EnableCORS;
import com.litongjava.tio.http.server.annotation.RequestPath;

@EnableCORS
@Controller
@RequestPath(value = "/")
public class IndexController {
  @RequestPath()
  public String respText() {
    return "whisper-asr-server";
  }
}