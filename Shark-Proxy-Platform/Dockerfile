FROM openjdk:8-jre-alpine

MAINTAINER Sage (sagelovemio@qq.com)
ENV TZ='Asia/Shanghai'

WORKDIR /app
COPY ./target/Shark-Proxy-Platform-1.0-SNAPSHOT.jar .
EXPOSE 8080

CMD ["java", "-Xms128m", "-Xmx256m", "-XX:+HeapDumpOnOutOfMemoryError", "-Dfile.encoding=utf-8", "-jar", "Shark-Proxy-Platform-1.0-SNAPSHOT.jar", "--spring.profiles.active=prod"]
