# build litongjava/paddle-ocr-server:1.0.1
# Use litongjava/jdk:8u211 as the base image
FROM litongjava/centos-8-jdk:8u341

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/paddle-ocr-server-1.0.1.jar /app/

# download file
RUN java -jar /app/paddle-ocr-server-1.0.1.jar --download

# Command to run the jar file
CMD ["java", "-jar", "paddle-ocr-server-1.0.1.jar", "--mode=prod"]
