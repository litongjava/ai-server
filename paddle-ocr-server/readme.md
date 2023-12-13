## paddle-ocr-server

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
