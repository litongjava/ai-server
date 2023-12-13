# build litongjava/
# Use litongjava/jdk:8u211 as the base image
FROM litongjava/jdk:8u211

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/paddle-ocr-server-1.0.0.jar /app/

# Command to run the jar file
CMD ["/usr/java/jdk1.8.0_211/bin/java", "-jar", "paddle-ocr-server-1.0.0.jar", "--mode=prod"]
