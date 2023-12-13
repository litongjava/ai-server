package com.litongjava.ai.server.single;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class LocalLargeWhisperTest {

  @Test
  public void test() {
    String userHome = System.getProperty("user.home");
    Path path = Paths.get(userHome,".cache","whisper","ggml-base.en.bin");
    boolean exists = Files.exists(path);
    System.out.println(exists);
  }

}
