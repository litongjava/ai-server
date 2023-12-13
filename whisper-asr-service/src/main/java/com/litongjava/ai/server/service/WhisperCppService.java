package com.litongjava.ai.server.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.litongjava.ai.server.enumeration.AudioType;
import com.litongjava.ai.server.enumeration.TextType;
import com.litongjava.ai.server.model.WhisperSegment;
import com.litongjava.ai.server.single.LocalLargeWhisper;
import com.litongjava.ai.server.utils.Mp3Util;
import com.litongjava.jfinal.aop.Aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhisperCppService {
  private TextService textService = Aop.get(TextService.class);

  public List<WhisperSegment> index(URL url) {

    try {
      float[] floats = toAudioData(url);
      log.info("floats size:{}", floats.length);

      List<WhisperSegment> segments = LocalLargeWhisper.INSTANCE.fullTranscribeWithTime(floats, floats.length);
      log.info("size:{}", segments.size());
      return segments;
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }

    return null;

  }

  public List<WhisperSegment> index(byte[] data) {
    float[] floats = toFloat(data);
    return LocalLargeWhisper.INSTANCE.fullTranscribeWithTime(floats);
  }

  public float[] toAudioData(URL url) throws UnsupportedAudioFileException, IOException {
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
    byte[] b = new byte[audioInputStream.available()];
    try {
      audioInputStream.read(b);
      return toFloat(b);
    } finally {
      audioInputStream.close();
    }
  }

  public float[] toFloat(byte[] b) {
    float[] floats = new float[b.length / 2];
    for (int i = 0, j = 0; i < b.length; i += 2, j++) {
      int intSample = (int) (b[i + 1]) << 8 | (int) (b[i]) & 0xFF;
      floats[j] = intSample / 32767.0f;
    }
    return floats;
  }

  public StringBuffer outputSrt(URL url) throws IOException {
    List<WhisperSegment> segments = this.index(url);
    return textService.generateSrt(segments);
  }

  public StringBuffer outputVtt(URL url) throws IOException {
    List<WhisperSegment> segments = this.index(url);
    return textService.generateVtt(segments);
  }

  public StringBuffer outputLrc(URL url) throws IOException {
    List<WhisperSegment> segments = this.index(url);
    return textService.generateLrc(segments);
  }

  public Object index(byte[] data, String inputType, String outputType)
      throws IOException, UnsupportedAudioFileException {
    log.info("intputType:{},outputType:{}", inputType, outputType);
    AudioType audioType = AudioType.fromString(inputType);
    TextType textType = TextType.fromString(outputType);
    if (audioType == AudioType.MP3) {
      // 进行格式转换
      log.info("进行格式转换:{}", "mp3");
      data = Aop.get(Mp3Util.class).convertToWav(data, 16000, 1);
    }
    List<WhisperSegment> segments = index(data);
    if (textType == TextType.SRT) {
      return textService.generateSrt(segments).toString();
    } else if (textType == TextType.VTT) {
      return textService.generateVtt(segments).toString();
    } else if (textType == TextType.LRC) {
      return textService.generateLrc(segments).toString();
    }
    return segments;
  }
}