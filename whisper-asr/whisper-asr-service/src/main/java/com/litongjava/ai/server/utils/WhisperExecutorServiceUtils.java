package com.litongjava.ai.server.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池类
 * @author Tong Li
 *
 */
public class WhisperExecutorServiceUtils {
  public static ExecutorService executorService = Executors
      .newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

  public static <T> Future<T> submit(Callable<T> task) {
    return executorService.submit(task);
  }

}
