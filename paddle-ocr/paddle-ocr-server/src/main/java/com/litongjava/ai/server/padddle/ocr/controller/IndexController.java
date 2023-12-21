package com.litongjava.ai.server.padddle.ocr.controller;

import com.litongjava.jfinal.aop.annotation.Controller;
import com.litongjava.tio.http.server.annotation.EnableCORS;
import com.litongjava.tio.http.server.annotation.RequestPath;

@EnableCORS
@Controller
@RequestPath(value = "/")
public class IndexController {
  @RequestPath()
  public String respText() {
    return "paddle-ocr-server";
  }
}