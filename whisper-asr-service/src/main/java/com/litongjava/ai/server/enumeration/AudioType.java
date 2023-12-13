package com.litongjava.ai.server.enumeration;

import cn.hutool.core.util.StrUtil;

public enum AudioType {
  WAV("wav"), MP3("mp3");

  private final String type;

  AudioType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static AudioType fromString(String text) {
    if (StrUtil.isEmptyIfStr(text)) {
      return AudioType.WAV;
    }

    for (AudioType audioType : AudioType.values()) {
      if (audioType.type.equalsIgnoreCase(text)) {
        return audioType;
      }
    }
    throw new IllegalArgumentException("No enum constant for text " + text);
  }
}
