package com.litongjava.aio.server.tio.controller;

import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.server.annotation.EnableCORS;
import com.litongjava.tio.http.server.annotation.RequestPath;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.environment.EnvironmentUtils;
import com.litongjava.tio.utils.resp.Resp;

@EnableCORS
@RequestPath("/env")
public class EnviormentController {
  @RequestPath("/{key}")
  public HttpResponse get(String key, HttpRequest request) {
    return Resps.json(request, Resp.ok(EnvironmentUtils.get(key)));
  }

  @RequestPath("/beans")
  public HttpResponse beans(HttpRequest request) {
    String[] beans = Aop.beans();
    return Resps.json(request, Resp.ok(beans));

  }
}
