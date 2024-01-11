FROM python:3.7.4-alpine

MAINTAINER Sage (sagelovemio@qq.com)
WORKDIR /app

COPY ./requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple/

COPY . .
ENTRYPOINT ["python", "main.py"]
