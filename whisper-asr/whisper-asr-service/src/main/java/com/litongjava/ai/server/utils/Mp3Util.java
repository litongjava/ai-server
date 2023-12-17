package com.litongjava.ai.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Mp3Util {

  public byte[] convertToWav(byte[] mp3Data, int ar, int ac) throws UnsupportedAudioFileException, IOException {
    // Convert byte array to AudioInputStream
    try (AudioInputStream ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(mp3Data))) {
      AudioFormat format = ais.getFormat();

      // Convert to PCM_SIGNED if not already
      if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
        format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
            //
            format.getSampleRate(),
            //
            16,
            //
            format.getChannels(),
            //
            format.getChannels() * 2,
            //
            format.getSampleRate(),
            //
            false);
        AudioInputStream tempAis = AudioSystem.getAudioInputStream(format, ais);
        return convert(tempAis, format, ar, ac);
      }
    }
    return null;
  }

  public byte[] convertToWav(File mp3File, int ar, int ac) throws IOException, UnsupportedAudioFileException {
    // Read MP3 audio
    try (AudioInputStream ais = AudioSystem.getAudioInputStream(mp3File)) {
      AudioFormat format = ais.getFormat();

      // Convert to PCM_SIGNED if not already
      if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
        format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
            //
            format.getSampleRate(),
            //
            16,
            //
            format.getChannels(),
            //
            format.getChannels() * 2,
            //
            format.getSampleRate(),
            //
            false);
        // convert
        AudioInputStream tempAis = AudioSystem.getAudioInputStream(format, ais);

        return convert(tempAis, format, ar, ac);
      }
    }
    return null;
  }

  public byte[] convert(AudioInputStream srcAis, AudioFormat srcFormat, int ar, int ac) throws IOException {
    // Convert to desired format
    AudioFormat desiredFormat = new AudioFormat(srcFormat.getEncoding(),
        //
        ar,
        //
        srcFormat.getSampleSizeInBits(),
        //
        ac,
        //
        ac * 2,
        //
        ar,
        //
        srcFormat.isBigEndian());
    // convert
    AudioInputStream desiredAis = AudioSystem.getAudioInputStream(desiredFormat, srcAis);

    // Read the entire AudioInputStream into a byte array
    ByteArrayOutputStream tmpBaos = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = desiredAis.read(buffer)) != -1) {
      tmpBaos.write(buffer, 0, bytesRead);
    }
    byte[] audioBytes = tmpBaos.toByteArray();
    return audioBytes;
  }
}