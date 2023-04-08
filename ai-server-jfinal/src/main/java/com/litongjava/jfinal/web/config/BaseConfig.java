package com.litongjava.jfinal.web.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;
import com.litongjava.jfinal.web.utils.PropKitUtils;

public class BaseConfig extends JFinalConfig {

  public void configConstant(Constants me) {
    me.setDevMode(PropKitUtils.getBoolean("undertow.devMode"));
    // 配置对超类中的属性进行注入
    me.setInjectSuperClass(true);

    // 开启解析 json 请求，5.0.0 版本新增功能
    me.setResolveJsonRequest(true);
  }

  public void configRoute(Routes me) {
    me.scan("com.litongjava.jfinal.web.");

  }

  public void configEngine(Engine me) {
  }

  public void configPlugin(Plugins me) {
  }

  public void configInterceptor(Interceptors me) {
  }

  public void configHandler(Handlers me) {
  }
}