package com.litongjava.ai.server.rapid.ocr.controller;

import java.io.File;
import java.net.URL;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.litongjava.ai.server.rapid.ocr.instance.EngineInstance;
import com.litongjava.tio.boot.http.TioControllerContext;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.model.HttpCors;
import com.litongjava.tio.http.server.util.HttpServerResponseUtils;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.resp.RespVo;

public class RapidOcrHandler {

  public HttpResponse rec(HttpRequest httprequest) throws Exception {
    HttpResponse httpResponse = TioControllerContext.getResponse();
    HttpServerResponseUtils.enableCORS(httpResponse, new HttpCors());

    UploadFile uploadFile = httprequest.getUploadFile("file");
    String responseFormat = httprequest.getParam("responseFormat");
    OcrResult runOcr = null;
    if (uploadFile != null) {
      byte[] fileData = uploadFile.getData();
      String name = uploadFile.getName();
      File file = new File(System.currentTimeMillis() + name);

      FileUtil.writeBytes(fileData, file);
      runOcr = EngineInstance.runOcr(file.getAbsolutePath());

      if (runOcr != null) {
        if ("json".equals(responseFormat)) {
          httpResponse = Resps.json(httprequest, RespVo.ok(runOcr));
        } else {
          httpResponse = Resps.json(httprequest, RespVo.ok(runOcr.getStrRes().trim()));
        }

      } else {
        httpResponse = Resps.json(httprequest, RespVo.fail());
      }

    } else {
      httpResponse = Resps.json(httprequest, RespVo.fail("please upload file"));
    }

    return httpResponse;
  }

  public HttpResponse test(HttpRequest httprequest) throws Exception {
    URL resource = ResourceUtil.getResource("images/flight_ticket.jpg");
    String fileString = resource.getFile();
    File file = new File(fileString);
//    OcrResult ocrResult = engine.runOcr(resource.toString());
    // OcrResult ocrResult = EngineInstance.runOcr(resource.toURI().getPath());
    OcrResult ocrResult = EngineInstance.runOcr(file.getAbsolutePath());
    return Resps.json(httprequest, ocrResult.getStrRes().trim());
  }
}
