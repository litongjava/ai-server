package com.litongjava.aio.server.tio.config;

import com.litongjava.ai.server.property.WhiserAsrProperties;
import com.litongjava.jfinal.aop.annotation.Bean;
import com.litongjava.jfinal.aop.annotation.Configuration;
import com.litongjava.tio.utils.enviorment.EnviormentUtils;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WhisperAsrConfig {

  @Bean
  public WhiserAsrProperties aiServiceProperties() {
    WhiserAsrProperties aiServiceProperties = new WhiserAsrProperties();
    String modelName = EnviormentUtils.get("model.name");
    if (modelName != null) {
      log.info("modelName:{}", modelName);
      aiServiceProperties.setModelName(modelName);
    }

    return aiServiceProperties;

  }
}
