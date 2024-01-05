package com.litongjava.ai.server.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.litongjava.ai.server.enumeration.AudioType;
import com.litongjava.ai.server.enumeration.TextType;
import com.litongjava.ai.server.model.WhisperSegment;
import com.litongjava.ai.server.single.LocalLargeWhisper;
import com.litongjava.ai.server.utils.Mp3Util;
import com.litongjava.ai.server.utils.WhisperAudioUtils;
import com.litongjava.jfinal.aop.Aop;

import io.github.givimad.whisperjni.WhisperFullParams;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用Base模型
 * @author Tong Li
 *
 */
@Slf4j
public class WhisperCppLargeService {
  private TextService textService = Aop.get(TextService.class);

  public List<WhisperSegment> index(URL url,WhisperFullParams params) {

    try {
      float[] floats = WhisperAudioUtils.toAudioData(url);
      log.info("floats size:{}", floats.length);

      List<WhisperSegment> segments = LocalLargeWhisper.INSTANCE.fullTranscribeWithTime(floats, floats.length,params);
      log.info("size:{}", segments.size());
      return segments;
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }

    return null;

  }
  
  public List<WhisperSegment> index(byte[] data, WhisperFullParams params) {
    float[] floats = WhisperAudioUtils.toFloat(data);
    return LocalLargeWhisper.INSTANCE.fullTranscribeWithTime(floats, params);
  }

  public StringBuffer outputSrt(URL url,WhisperFullParams params) throws IOException {
    List<WhisperSegment> segments = this.index(url,params);
    return textService.generateSrt(segments);
  }

  public StringBuffer outputVtt(URL url,WhisperFullParams params) throws IOException {
    List<WhisperSegment> segments = this.index(url,params);
    return textService.generateVtt(segments);
  }

  public StringBuffer outputLrc(URL url,WhisperFullParams params) throws IOException {
    List<WhisperSegment> segments = this.index(url,params);
    return textService.generateLrc(segments);
  }

  public Object index(byte[] data, String inputType, String outputType)
      throws IOException, UnsupportedAudioFileException {
    return index(data, inputType, outputType, null);
  }

  public Object index(byte[] data, String inputType, String outputType, WhisperFullParams params)
      throws IOException, UnsupportedAudioFileException {
    log.info("intputType:{},outputType:{}", inputType, outputType);
    AudioType audioType = AudioType.fromString(inputType);
    TextType textType = TextType.fromString(outputType);
    if (audioType == AudioType.MP3) {
      // 进行格式转换
      log.info("进行格式转换:{}", "mp3");
      data = Aop.get(Mp3Util.class).convertToWav(data, 16000, 1);
    }
    List<WhisperSegment> segments = index(data, params);
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