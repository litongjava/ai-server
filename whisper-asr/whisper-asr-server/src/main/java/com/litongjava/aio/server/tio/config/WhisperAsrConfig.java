package com.litongjava.aio.server.tio.config;

import com.litongjava.ai.server.property.WhiserAsrProperties;
import com.litongjava.jfinal.aop.annotation.ABean;
import com.litongjava.jfinal.aop.annotation.AConfiguration;
import com.litongjava.tio.utils.environment.EnvironmentUtils;

import lombok.extern.slf4j.Slf4j;

@AConfiguration
@Slf4j
public class WhisperAsrConfig {

  @ABean
  public WhiserAsrProperties aiServiceProperties() {
    WhiserAsrProperties aiServiceProperties = new WhiserAsrProperties();
    String modelName = EnvironmentUtils.get("model.name");
    if (modelName != null) {
      log.info("modelName:{}", modelName);
      aiServiceProperties.setModelName(modelName);
    }

    return aiServiceProperties;

  }
}
