FROM python:3.10-slim

MAINTAINER Sage (sagelovemio@qq.com)
WORKDIR /app

COPY ./requirements.txt .
RUN pip install --upgrade pip && pip install --no-cache-dir -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple/

COPY . .
ENTRYPOINT ["python", "main.py"]
