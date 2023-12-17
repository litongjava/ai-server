package com.litongjava.ai.server.enumeration;

import cn.hutool.core.util.StrUtil;

public enum TextType {
  DEFAULT("default"), LRC("lrc"), VTT("vtt"), SRT("srt");

  private final String type;

  TextType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  // 根据字符串值查找相应的枚举项
  public static TextType fromString(String text) {
    if (StrUtil.isEmptyIfStr(text)) {
      return TextType.DEFAULT;
    }
    for (TextType audioType : TextType.values()) {
      if (audioType.type.equalsIgnoreCase(text)) {
        return audioType;
      }
    }
    throw new IllegalArgumentException("No enum constant for text " + text);
  }
}
