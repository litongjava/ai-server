package com.litongjava.ai.server.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import com.litongjava.ai.server.service.WhisperCppService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import javazoom.jl.decoder.JavaLayerException;

public class Mp3UtilTest {

  @Test
  public void test() throws URISyntaxException, IOException, UnsupportedAudioFileException, JavaLayerException,
      LineUnavailableException {
    URL resource = ClassUtil.getClassLoader().getResource("audios/test.mp3");
    if (resource == null) {
      return;
    }
    File file = new File(resource.toURI());
    byte[] mp3Data = FileUtil.readBytes(file);
    // Save or use wavData as needed
    Mp3Util mp3Util = new Mp3Util();
    byte[] wavData = mp3Util.convertToWav(mp3Data, 16000, 1);
    WhisperCppService whisperCppService = new WhisperCppService();
    Object index = whisperCppService.index(wavData);
    System.out.println(index);

  }

  @Test
  public void test2() throws URISyntaxException, IOException, UnsupportedAudioFileException {
    URL resource = ClassUtil.getClassLoader().getResource("audios/test.mp3");
    if (resource == null) {
      return;
    }
    File file = new File(resource.toURI());
//    byte[] convertToWav = Aop.get(Mp3Util.class).convertToWav(file, 16000, 1);

  }
}
