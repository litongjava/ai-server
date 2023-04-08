package com.litongjava.jfinal.web.utils;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class PropKitUtils {
  private static String configFileName = "undertow.txt";
  public static Prop prop;

  static {
    loadConfig();
  }

  public static void loadConfig() {
    if (prop == null) {
      prop = PropKit.use(configFileName);
    }
  }

  public static int getInt(String key) {
    return prop.getInt(key);
  }

  public static String get(String key) {
    return prop.get(key);
    
  }
  public static Boolean getBoolean(String key) {
    return prop.getBoolean(key);
  }
}