# -*- coding: utf-8 -*-

import pika
from mq.basic import get_parameters, ProxyAddTo

connection = pika.BlockingConnection(get_parameters())
channel = connection.channel()


# 发布消息
def publish_message(exchange, routing_key, message):
    channel.basic_publish(exchange=exchange, routing_key=routing_key, body=message)


# 发送代理到新增队列
def send_to_add_queue(to: ProxyAddTo):
    publish_message("proxy-event-exchange", "proxy.add", to)
