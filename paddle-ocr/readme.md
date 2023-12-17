# paddle-ocr-server


## 简介

基于java语言的开箱即用的ocr识别服务,用到的框架和技术
- tio-boot
- djl
- opencv
- pytorch
- onnx
- paddle-ocr
## How to build
```
git clone https://github.com/litongjava/ai-server.git
or
#git clone https://gitee.com/ppnt/ai-server.git
mvn install -DskipTests
```
whisper-asr-service会构建失败,不用理会,因为 whisper-asr-service依赖jdk 11,paddle-ocr-server并不依赖whisper-asr-service,保证paddle-ocr-service构建成功即可
build paddle-ocr-server
```
cd paddle-ocr-server
mvn package -DskipTests -Pproduction
```
run
```
java -jar paddle-ocr-server/target/paddle-ocr-server-1.0.1.jar
```
## 接口文档

### 在线文档地址
https://apifox.com/apidoc/shared-98cc5675-f1a3-4250-a940-cfe060854ef4
#### 测试接口
访问地址:http://localhost/paddle/ocr/test
返回数据:
```
{"data":"www.997788 + 登机牌 BOARDING PASS \n航班 FLIGHT 日期 DATE 舱位 CLASS 序号SERIALNO. 座位号SEATNO \nMU 2379 03DEC W 035 12F \n目的地TO 始发地 FROM 一 登机口 GATE 登机时间BDT \n福州 TAIYUAN G11 \nFUZHOU 身份识别IDNO. \n姓名NAME \nZHANGQIWEI 票号TKTNO. \n张祺伟 \n票价FARE ETKT7813699238489/1 \n登机口于起飞前10分钟关闭 GATES CLOSE 10MINUTES BEFORE DEPARTURE TIME + \n","ok":true}
```
### 识别接口
```
curl --location --request POST 'http://localhost/paddle/ocr/rec' \
--header 'User-Agent: Apifox/1.0.0 (https://apifox.com)' \
--form 'file=@"<file>"'
```
返回数据格式
```
{
    "data": "text数据",
    "ok": true
}
```
## Docker
### build
```
mvn package -DskipTests -Pproduction
docker build -t litongjava/paddle-ocr-server:1.0.1 .
docker tag litongjava/paddle-ocr-server:1.0.1 litongjava/paddle-ocr-server
```
### run
```
docker run --name ocr_server -dit -p 8080:80 litongjava/paddle-ocr-server
```
