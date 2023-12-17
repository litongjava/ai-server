package com.litongjava.ai.server.single;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.litongjava.ai.server.model.WhisperSegment;
import com.litongjava.ai.server.property.WhiserAsrProperties;
import com.litongjava.ai.server.service.WhisperCppJni;
import com.litongjava.jfinal.aop.Aop;

import io.github.givimad.whisperjni.WhisperFullParams;
import io.github.givimad.whisperjni.WhisperJNI;

public enum LocalLargeWhisper {
  INSTANCE;

  private ExecutorService executorService;
  private ThreadLocal<WhisperCppJni> threadLocalWhisper;

  LocalLargeWhisper() {
    try {
      WhisperJNI.loadLibrary();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    // C:\Users\Administrator\.cache\whisper
    String userHome = System.getProperty("user.home");
    String modelName = Aop.get(WhiserAsrProperties.class).getModelName();
    Path path = Paths.get(userHome, ".cache", "whisper", modelName);

    this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);
    threadLocalWhisper = ThreadLocal.withInitial(() -> {
      WhisperCppJni whisper = new WhisperCppJni();
      try {
        whisper.initContext(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return whisper;
    });
  }

  public List<WhisperSegment> fullTranscribeWithTime(float[] audioData, int numSamples) {
    Callable<List<WhisperSegment>> task = () -> {
      WhisperCppJni whisper = null;
      whisper = threadLocalWhisper.get();
      WhisperFullParams params = new WhisperFullParams();
      params.printProgress = false;
      return whisper.fullTranscribeWithTime(params, audioData, numSamples);
    };

    try {
      return executorService.submit(task).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<WhisperSegment> fullTranscribeWithTime(float[] floats) {
    return fullTranscribeWithTime(floats, floats.length);
  }
}