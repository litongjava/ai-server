package com.litongjava.ai.server.service;

import java.io.IOException;
import java.util.List;

import com.litongjava.ai.server.model.WhisperSegment;

public class TextService {
  public StringBuffer generateSrt(List<WhisperSegment> segments) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    int index = 1;
    for (WhisperSegment segment : segments) {
      String startTime = convertToSRTTime(segment.getStart() * 10);
      String endTime = convertToSRTTime(segment.getEnd() * 10);
      stringBuffer.append(index + "\n");
      stringBuffer.append(startTime + " --> " + endTime + "\n");
      stringBuffer.append(segment.getSentence() + "\n\n");
      index++;
    }
    return stringBuffer;
  }

  public String convertToSRTTime(long milliseconds) {
    int hours = (int) (milliseconds / (1000 * 60 * 60));
    int minutes = (int) ((milliseconds % (1000 * 60 * 60)) / (1000 * 60));
    int seconds = (int) ((milliseconds % (1000 * 60)) / 1000);
    int millis = (int) (milliseconds % 1000);
    return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, millis);
  }

  public StringBuffer generateVtt(List<WhisperSegment> segments) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();

    // Write the WebVTT header
    stringBuffer.append("WEBVTT\n\n");

    int counter = 1;
    for (WhisperSegment segment : segments) {
      // Convert the start and end times from milliseconds to hh:mm:ss.sss format
      String startTime = millisecondsToVttTime(segment.getStart() * 10);
      String endTime = millisecondsToVttTime(segment.getEnd() * 10);

      // Write the timestamp and the sentence to the file
      stringBuffer.append(counter++ + "\n");
      stringBuffer.append(startTime + " --> " + endTime + "\n");
      stringBuffer.append(segment.getSentence() + "\n\n");
    }
    return stringBuffer;
  }

  private String millisecondsToVttTime(long milliseconds) {
    long hours = milliseconds / 3600000;
    long minutes = (milliseconds % 3600000) / 60000;
    long seconds = (milliseconds % 60000) / 1000;
    long millis = milliseconds % 1000;
    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
  }

  public StringBuffer generateLrc(List<WhisperSegment> segments) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    for (WhisperSegment segment : segments) {
      String timestamp = millisecondsToLrcTime(segment.getStart() * 10);
      stringBuffer.append("[" + timestamp + "]" + segment.getSentence() + "\n");
    }
    return stringBuffer;
  }

  private String millisecondsToLrcTime(long milliseconds) {
    long totalSeconds = milliseconds / 1000;
    long minutes = totalSeconds / 60;
    long seconds = totalSeconds % 60;
    long millis = milliseconds % 1000;
    return String.format("%02d:%02d.%02d", minutes, seconds, millis / 10);
  }

}
