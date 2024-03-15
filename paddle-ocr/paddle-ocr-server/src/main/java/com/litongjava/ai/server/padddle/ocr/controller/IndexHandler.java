package com.litongjava.ai.server.padddle.ocr.controller;

import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.server.util.Resps;

public class IndexHandler {

  public HttpResponse index(HttpRequest httpRequest) {
    return Resps.txt(httpRequest, "paddle-ocr-server");
  }
}
