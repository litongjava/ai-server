# rapid-ocr-server

## requirements
- java 1.8

## build
克隆并构建 TIO 工具库： 这些是 TIO 项目的辅助工具库。
```
git clone https://github.com/litongjava/tio-utils.git
cd tio-utils
mvn clean install -DskipTests -Dgpg.skip
```
克隆并构建 TIO 核心库： 这是所有基于 TIO 项目所需的核心库。
```
git clone https://github.com/litongjava/t-io.git
cd t-io
mvn clean install -DskipTests -Dgpg.skip
```
克隆并构建 TIO HTTP 组件： 这些组件为 TIO 应用程序启用 HTTP 功能。
```
git clone https://github.com/litongjava/tio-http.git
cd tio-http/tio-http-common/
mvn clean install -DskipTests -Dgpg.skip
```
```
cd ../tio-http-server
mvn clean install -DskipTests -Dgpg.skip
```
克隆并构建 TIO Boot： TIO Boot 简化了 TIO 应用程序的引导过程。
```
git clone https://github.com/litongjava/tio-boot.git
cd tio-boot
mvn clean install -DskipTests -Dgpg.skip
```
RapidOcr-Java
```
https://github.com/litongjava/RapidOcr-Java
cd RapidOcr-Java
mvn clean install -DskipTests -Dgpg.skip
```
构建本项目
```
mvn clean package -DskipTests -Dgpg.skip -Pproduction
```
## test
- http://localhost/rapid/ocr/test
- http://localhost/rapid/ocr/rec

## curl

```
curl --location --request POST 'http://localhost/rapid/ocr/rec' \
--header 'Accept: */*' \
--header 'Content-Type: multipart/form-data; boundary=--------------------------865945034672416949878658' \
--form 'file=@"flight_ticket.jpg"' \
--form 'responseFormat="text"'
```