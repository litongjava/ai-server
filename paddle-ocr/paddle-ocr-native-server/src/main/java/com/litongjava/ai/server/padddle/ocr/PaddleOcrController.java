package com.litongjava.ai.server.padddle.ocr;

import java.net.URL;

import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.common.UploadFile;
import com.litongjava.tio.http.server.util.Resps;
import com.litongjava.tio.utils.hutool.ResourceUtil;
import com.litongjava.tio.utils.resp.RespVo;

//@EnableCORS
//@Controller
//@RequestPath("/paddle/ocr")
public class PaddleOcrController {

  // @RequestPath(value = "/rec")
  public HttpResponse rec(HttpRequest request) throws Exception {
    String url = request.getParam("url");
    UploadFile file = request.getUploadFile("file");
    String text = null;
    if (url != null) {
      text = PaddlePaddleOCRV4.INSTANCE.ocr(url);
    } else if (file != null) {
      byte[] fileData = file.getData();
      text = PaddlePaddleOCRV4.INSTANCE.ocr(fileData);
    }
    if (text != null) {
      return Resps.json(request, RespVo.ok(text));
    } else {
      return Resps.json(request, RespVo.fail());
    }
  }

  // @RequestPath("/test")
  public HttpResponse test(HttpRequest request) throws Exception {
    URL resource = ResourceUtil.getResource("images/flight_ticket.jpg");
    return Resps.json(request, RespVo.ok(PaddlePaddleOCRV4.INSTANCE.ocr(resource)));
  }

}
