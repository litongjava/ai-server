package com.litongjava.aio.server.tio.controller;

import ai.djl.MalformedModelException;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import com.litongjava.ai.server.service.PaddleOcrService;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.UploadFile;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.util.Resps;

import java.io.IOException;

@RequestPath("/ocr")
public class OcrController {
  private PaddleOcrService paddleOcrService = new PaddleOcrService();

  @RequestPath(value = "")
  public HttpResponse index(UploadFile file, String url, HttpRequest request) throws Exception {
    HttpResponse ret;
    DetectedObjects index = null;
    if (url != null) {
      index = paddleOcrService.index(url);
    } else if (file != null) {
      byte[] fileData = file.getData();
      index = paddleOcrService.index(fileData);
    }
    if (index != null) {
      return Resps.json(request, index.toJson());
    } else {
      return Resps.txt(request, "please upload the file");
    }
  }

  @RequestPath("/test")
  public HttpResponse test(HttpRequest request) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
    String url = "https://resources.djl.ai/images/flight_ticket.jpg";
    return Resps.json(request, paddleOcrService.index(url).toJson());
  }
}
