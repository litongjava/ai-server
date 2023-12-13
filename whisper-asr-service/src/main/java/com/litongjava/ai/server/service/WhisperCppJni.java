package com.litongjava.ai.server.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.litongjava.ai.server.model.WhisperSegment;

import io.github.givimad.whisperjni.WhisperContext;
import io.github.givimad.whisperjni.WhisperFullParams;
import io.github.givimad.whisperjni.WhisperJNI;

public class WhisperCppJni {

  private WhisperJNI whisper = null;
  private WhisperContext ctx = null;

  public void initContext(Path path) throws IOException {
    whisper = new WhisperJNI();
    ctx = whisper.init(path);
  }

  public List<WhisperSegment> fullTranscribeWithTime(WhisperFullParams params, float[] samples, int numSamples) {
    int result = whisper.full(ctx, params, samples, numSamples);
    if (result != 0) {
      throw new RuntimeException("Transcription failed with code " + result);
    }
    int numSegments = whisper.fullNSegments(ctx);
    ArrayList<WhisperSegment> segments = new ArrayList<WhisperSegment>(numSegments);

    for (int i = 0; i < numSegments; i++) {
      String text = whisper.fullGetSegmentText(ctx, i);
      long start = whisper.fullGetSegmentTimestamp0(ctx, i);
      long end = whisper.fullGetSegmentTimestamp1(ctx, i);
      segments.add(new WhisperSegment(start, end, text));
    }
    return segments;
  }

  public void close() {
    ctx.close();
  }
}
