```
docker build -t litongjava/whisper-asr-server:1.0.1 -f docker/1.0.1 .
```

run
```
docker run -p 80:80 --rm litongjava/whisper-asr-server:1.0.1
```
or
```
docker run -p 80:80 --rm litongjava/whisper-asr-server:1.0.1 /usr/java/jdk1.8.0_211/bin/java -jar whisper-asr-server-1.0.1.jar
```