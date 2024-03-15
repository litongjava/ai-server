##
### 打包失败,错误日志如下,可能还是需要使用jni的方案才支持编译成二进制文件
```
Error: Class-path entry file:///root/.m2/repository/com/microsoft/onnxruntime/onnxruntime/1.16.0/onnxruntime-1.16.0.jar contains class ai.onnxruntime.ValueInfo. This class is part of the image builder itself (in file:///root/program/graalvm-jdk-21.0.1+12.1/lib/svm/builder/svm-enterprise.jar) and must not be passed via -cp. This can be caused by a fat-jar that illegally includes svm.jar (or graal-sdk.jar) due to its build-time dependency on it. As a workaround, -H:+AllowDeprecatedBuilderClassesOnImageClasspath allows turning this error into a warning. Note that this option is deprecated and will be removed in a future version.
```
