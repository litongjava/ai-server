package com.litongjava.aio.server.tio.controller;

import java.net.URL;
import java.util.List;

import com.litongjava.ai.server.model.WhisperSegment;
import com.litongjava.ai.server.service.WhisperCppBaseService;
import com.litongjava.ai.server.service.WhisperCppLargeService;
import com.litongjava.ai.server.service.WhisperCppService;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.annotation.EnableCORS;
import com.litongjava.tio.http.server.annotation.RequestPath;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.resp.Resp;

import cn.hutool.core.util.ClassUtil;
import io.github.givimad.whisperjni.WhisperFullParams;

@EnableCORS
@RequestPath("/whispser/asr")
public class WhisperAsrController {
  private WhisperCppService whisperCppService = Aop.get(WhisperCppService.class);
  private WhisperCppBaseService whisperCppBaseService = Aop.get(WhisperCppBaseService.class);
  private WhisperCppLargeService whisperCppLargeService = Aop.get(WhisperCppLargeService.class);

  @RequestPath(value = "/rec")
  public HttpResponse index(HttpRequest request, UploadFile file, String inputType, String outputType,
      String outputFormat, WhisperFullParams params) throws Exception {
    if (file != null) {
      Object data = whisperCppService.index(file.getData(), inputType, outputType, params);
      if ("txt".equals(outputFormat)) {
        if (data instanceof String) {
          return Resps.txt(request, (String) data);
        }

      } else {
        return Resps.json(request, Resp.ok(data));
      }

    } else {
      return Resps.json(request, Resp.fail("uplod file can't be null"));
    }
    return Resps.json(request, Resp.fail("unknow error"));
  }

  @RequestPath(value = "/rec/base")
  public HttpResponse recBase(HttpRequest request, UploadFile file, String inputType, String outputType,
      String outputFormat, WhisperFullParams params) throws Exception {
    if (file != null) {
      Object data = whisperCppBaseService.index(file.getData(), inputType, outputType, params);
      if ("txt".equals(outputFormat)) {
        if (data instanceof String) {
          return Resps.txt(request, (String) data);
        }

      } else {
        return Resps.json(request, Resp.ok(data));
      }

    } else {
      return Resps.json(request, Resp.fail("uplod file can't be null"));
    }
    return Resps.json(request, Resp.fail("unknow error"));
  }

  @RequestPath(value = "/rec/large")
  public HttpResponse recLarge(UploadFile file, String inputType, String outputType, String outputFormat,
      HttpRequest request, WhisperFullParams params) throws Exception {
    if (file != null) {
      Object data = whisperCppLargeService.index(file.getData(), inputType, outputType, params);
      if ("txt".equals(outputFormat)) {
        if (data instanceof String) {
          return Resps.txt(request, (String) data);
        }

      } else {
        return Resps.json(request, Resp.ok(data));
      }

    } else {
      return Resps.json(request, Resp.fail("uplod file can't be null"));
    }
    return Resps.json(request, Resp.fail("unknow error"));
  }

  @RequestPath("/test")
  public HttpResponse test(HttpRequest request, WhisperFullParams params) {
    // String urlStr = "https://raw.githubusercontent.com/litongjava/whisper.cpp/master/samples/jfk.wav";
    URL resource = ClassUtil.getClassLoader().getResource("audios/jfk.wav");
    if (resource != null) {
      List<WhisperSegment> list = whisperCppService.index(resource, params);
      return Resps.json(request, Resp.ok(list));
    }
    return null;
  }

  @RequestPath("/test/base")
  public HttpResponse testBase(HttpRequest request, WhisperFullParams params) {
    // String urlStr = "https://raw.githubusercontent.com/litongjava/whisper.cpp/master/samples/jfk.wav";
    URL resource = ClassUtil.getClassLoader().getResource("audios/jfk.wav");
    if (resource != null) {
      List<WhisperSegment> list = whisperCppBaseService.index(resource, params);
      return Resps.json(request, Resp.ok(list));
    }
    return null;
  }

  @RequestPath("/test/large")
  public HttpResponse testLarge(HttpRequest request, WhisperFullParams params) {
    // String urlStr = "https://raw.githubusercontent.com/litongjava/whisper.cpp/master/samples/jfk.wav";
    URL resource = ClassUtil.getClassLoader().getResource("audios/jfk.wav");
    if (resource != null) {
      List<WhisperSegment> list = whisperCppLargeService.index(resource, params);
      return Resps.json(request, Resp.ok(list));
    }
    return null;
  }
}
