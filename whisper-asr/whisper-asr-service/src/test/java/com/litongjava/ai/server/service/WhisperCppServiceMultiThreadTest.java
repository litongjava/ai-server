package com.litongjava.ai.server.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhisperCppServiceMultiThreadTest {

  public static void main(String[] args) throws MalformedURLException {
    WhisperCppService whisperCppService = new WhisperCppService();
    File file = new File("E:\\code\\cpp\\project-ping\\whisper.cpp\\samples\\jfk.wav");
    URL url = file.toURI().toURL();
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    log.info("availableProcessors:{}", availableProcessors);
    for (int i = 0; i < availableProcessors*2; i++) {
      whisperCppService.index(url);
    }

  }

}
