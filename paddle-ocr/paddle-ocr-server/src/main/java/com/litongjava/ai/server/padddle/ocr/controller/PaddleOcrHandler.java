package com.litongjava.ai.server.padddle.ocr.controller;

import java.net.URL;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.model.HttpCors;
import com.litongjava.tio.http.server.util.HttpServerResponseUtils;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.resp.RespVo;

public class PaddleOcrHandler {

  public HttpResponse rec(HttpRequest httprequest) throws Exception {
    UploadFile file = httprequest.getUploadFile("file");
    String url = httprequest.getParam("url");
    String text = null;
    if (url != null) {
      text = PaddlePaddleOCRV4.INSTANCE.ocr(url);
    } else if (file != null) {
      byte[] fileData = file.getData();
      text = PaddlePaddleOCRV4.INSTANCE.ocr(fileData);
    }
    HttpResponse httpResponse = null;
    if (text != null) {
      httpResponse = Resps.json(httprequest, RespVo.ok(text));
      return httpResponse;
    } else {
      httpResponse = Resps.json(httprequest, RespVo.fail());
    }
    HttpServerResponseUtils.enableCORS(httpResponse, new HttpCors());
    return httpResponse;
  }

  public HttpResponse test(HttpRequest httprequest) throws Exception {
    URL resource = ResourceUtil.getResource("images/flight_ticket.jpg");
    return Resps.json(httprequest, RespVo.ok(PaddlePaddleOCRV4.INSTANCE.ocr(resource)));
  }
}
