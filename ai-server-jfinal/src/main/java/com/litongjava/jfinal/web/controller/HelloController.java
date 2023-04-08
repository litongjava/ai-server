package com.litongjava.jfinal.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;

import lombok.extern.slf4j.Slf4j;

@Path("/hello")
@Slf4j
public class HelloController extends Controller {
  public void index() {
    log.info("hello");
    renderText("Hello JFinal World.");
  }

  public void json() throws IOException, URISyntaxException {
    URL resource = this.getClass().getClassLoader().getResource("data/user.json");
    assert resource != null;
    java.nio.file.Path path = Paths.get(resource.toURI());
    byte[] bytes = Files.readAllBytes(path);
    JSONObject jsonObject = JSON.parseObject(bytes);
    log.info(jsonObject.toJSONString());
    renderJson(jsonObject);
  }
}
