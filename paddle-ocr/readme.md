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
mvn clean package -pl paddle-ocr/paddle-ocr-server -DskipTests -Pproduction
```
run
```
java -jar java -jar paddle-ocr/paddle-ocr-server/target/paddle-ocr-server-1.0.1.jar
```

## 库文件存储路径
启动后默认会下载pytorch,djl-pytorch库文件存储路径
```
$HOME/.djl.ai/pytorch
```
如果下载太慢可以到下面的地址下载pytorch库
```
https://github.com/litongjava/djl-libs/releases/tag/pytorch
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
- 镜像 选择刚才推送的镜像 registry-vpc.cn-hangzhou.aliyuncs.com/litongjava/paddle-ocr-server:latest
- 监听端口 80
- 是否使用GPU 不使用GPU
- 规格方案 默认即可
- 禁用公网访问 URL 否
- 点击部署
- 部署成功过后获取地址访问测试即可