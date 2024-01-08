FROM alpine:3.14.3

MAINTAINER Sage (sagelovemio@qq.com)
ENV TZ='Asia/Shanghai'

WORKDIR /app
COPY config/config.yaml config/config.yaml
COPY main .

ENTRYPOINT ["./main"]
