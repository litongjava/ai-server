package com.litongjava.aio.server.tio.config;

import com.litongjava.ai.server.property.WhiserAsrProperties;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.jfinal.aop.annotation.Bean;
import com.litongjava.jfinal.aop.annotation.Configuration;
import com.litongjava.tio.boot.context.Enviorment;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WhisperAsrConfig {

  private Enviorment enviorment = Aop.get(Enviorment.class);

  @Bean
  public WhiserAsrProperties aiServiceProperties() {
    WhiserAsrProperties aiServiceProperties = new WhiserAsrProperties();
    String modelName = enviorment.get("model.name");
    if (modelName != null) {
      log.info("modelName:{}", modelName);
      aiServiceProperties.setModelName(modelName);
    }

    return aiServiceProperties;

  }
}
