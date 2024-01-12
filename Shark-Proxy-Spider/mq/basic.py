# -*- coding: utf-8 -*-

import pika
import config


# 新增代理TO
class ProxyAddTo:
    def __init__(self, ip, port, type, source):
        self.ip = ip
        self.port = port
        self.type = type
        self.source = source


# RabbitMQ 服务器的连接参数
def get_parameters():
    credentials = pika.PlainCredentials(config.MQ_USERNAME, config.MQ_PASSWORD)
    return pika.ConnectionParameters(host=config.MQ_HOST, port=config.MQ_PORT, credentials=credentials, virtual_host=config.MQ_VIRTUAL_HOST, heartbeat=0)
