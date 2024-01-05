package com.litongjava.ai.server.utils;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WhisperAudioUtils {
  
  public static float[] toAudioData(URL url) throws UnsupportedAudioFileException, IOException {
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
    byte[] b = new byte[audioInputStream.available()];
    try {
      audioInputStream.read(b);
      return toFloat(b);
    } finally {
      audioInputStream.close();
    }
  }
  
  public static float[] toFloat(byte[] b) {
    float[] floats = new float[b.length / 2];
    for (int i = 0, j = 0; i < b.length; i += 2, j++) {
      int intSample = (int) (b[i + 1]) << 8 | (int) (b[i]) & 0xFF;
      floats[j] = intSample / 32767.0f;
    }
    return floats;
  }

}
