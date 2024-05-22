package com.litongjava.ai.server.rapid.ocr.controller;


import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.server.util.Resps;

public class IndexHandler {

  public HttpResponse index(HttpRequest httpRequest) {
    return Resps.txt(httpRequest, "rapid-ocr-server");
  }
}
