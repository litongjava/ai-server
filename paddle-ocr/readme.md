# paddle-ocr-server


## 简介

基于java语言的开箱即用的ocr识别服务,用到的框架和技术
- tio-boot
- djl
- opencv
- pytorch
- onnx
- paddle-ocr
## require
glic==2.28 or CentOS 8.4
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
cd paddle-ocr/paddle-ocr-server
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
测试接口不需要上传文件,使用程序自带的文件进行识别,用于测试环境安装是否成功
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
### 部署到aliyun fc

#### 推送镜像到 
##### 在 阿里云镜像仓库中 新建仓库
- 访问https://cr.console.aliyun.com/cn-hangzhou/instances/repositories
- 点击个人版
- 点击镜像仓库
- 点击创建镜像仓库 输入名称paddle-ocr-server

##### 推送镜像到 阿里云镜像仓库
- 登录查看用户名https://cr.console.aliyun.com/cn-hangzhou/instance/credentials
- 进入linux命令行
- 登录 docker login registry.cn-hangzhou.aliyuncs.com
- 拉取 litongjava/paddle-ocr-server
- tag;docker tag litongjava/paddle-ocr-server registry.cn-hangzhou.aliyuncs.com/litongjava/paddle-ocr-server
- push: docker push  registry.cn-hangzhou.aliyuncs.com/litongjava/paddle-ocr-server

#### 部署到 aliyun fc
- 登录 阿里云
- 选择"阿里云函数"计算
- 选择"服务及函数"
- 创建"服务"
- 名称:paddle-ocr-server 确定创建
- 创建函数-->使用容器镜像创建
- 函数名称 ocr
- web server模式 是
- 请求处理程序类型 处理 Http请求
- 镜像 选择刚才推送的镜像
- 点击部署