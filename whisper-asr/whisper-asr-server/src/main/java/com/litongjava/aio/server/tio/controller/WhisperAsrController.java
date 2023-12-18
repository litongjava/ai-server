package com.litongjava.aio.server.tio.controller;

import java.net.URL;
import java.util.List;

import com.litongjava.ai.server.model.WhisperSegment;
import com.litongjava.ai.server.service.WhisperCppService;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.jfinal.aop.annotation.Controller;
import com.litongjava.tio.boot.annotation.EnableCORS;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.annotation.RequestPath;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.resp.Resp;

import cn.hutool.core.util.ClassUtil;

@EnableCORS
@Controller
@RequestPath("/whispser/asr")
public class WhisperAsrController {
  private WhisperCppService whisperCppService = Aop.get(WhisperCppService.class);

  @RequestPath(value = "/rec")
  public HttpResponse index(UploadFile file, String inputType, String outputType, HttpRequest request)
      throws Exception {
    Resp resp = null;
    if (file != null) {
      Object data = whisperCppService.index(file.getData(), inputType, outputType);
      resp = Resp.ok(data);
    } else {
      resp = Resp.fail("uplod file can't be null");
    }

    return Resps.json(request, resp);

  }

  @RequestPath("/test")
  public HttpResponse test(HttpRequest request) {
    // String urlStr = "https://raw.githubusercontent.com/litongjava/whisper.cpp/master/samples/jfk.wav";
    URL resource = ClassUtil.getClassLoader().getResource("audios/jfk.wav");
    if (resource != null) {
      List<WhisperSegment> list = whisperCppService.index(resource);
      return Resps.json(request, Resp.ok(list));
    }
    return null;
  }
}
