# -*- coding: utf-8 -*-

from mq.basic import proxy_event_exchange, proxy_add_routing_key, RabbitMQProducer, ProxyAddTo

producer = RabbitMQProducer()

# 发送代理到新增队列
def send_to_add_queue(proxy: str):
    if proxy:
        proxy_info = proxy.split(':')
        if len(proxy_info) == 4:
            to = ProxyAddTo(proxy_info[0], proxy_info[1], proxy_info[2], proxy_info[3])
            producer.publish_message(proxy_event_exchange, proxy_add_routing_key, to.__json__())
