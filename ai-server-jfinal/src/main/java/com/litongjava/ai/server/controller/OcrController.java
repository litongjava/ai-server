package com.litongjava.ai.server.controller;

import java.io.File;
import java.io.IOException;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.upload.UploadFile;
import com.litongjava.ai.server.service.PaddleOcrService;

import ai.djl.MalformedModelException;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;

@Path("ocr")
public class OcrController extends Controller {
  PaddleOcrService paddleOcrService = Aop.get(PaddleOcrService.class);

  public void index(String url)
      throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
    DetectedObjects index = null;
    if (url != null) {
      index = paddleOcrService.index(url);
    } else {
      UploadFile uploadFile = getFile();
      File file = uploadFile.getFile();
      index = paddleOcrService.index(file);
    }
    renderJson(index.toJson());
  }
  
  public void test() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
    String url = "https://resources.djl.ai/images/flight_ticket.jpg";
    renderJson(paddleOcrService.index(url).toJson());
  }
}
