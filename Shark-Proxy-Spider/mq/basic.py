# -*- coding: utf-8 -*-

import config
import json
import pika
import time
import socket
import threading
import traceback
from util.log_util import get_logger

logger = get_logger(__name__)
proxy_event_exchange = 'proxy-event-exchange'
proxy_add_routing_key = 'proxy.add'


# 新增代理TO
class ProxyAddTo:
    def __init__(self, ip, port, type, source):
        self.ip = ip
        self.port = port
        self.type = type
        self.source = source

    def __json__(self):
        return {
            'ip': self.ip,
            'port': self.port,
            'type': self.type,
            'source': self.source,
        }


# RabbitMQ 服务器的连接参数
def get_parameters():
    credentials = pika.PlainCredentials(config.MQ_USERNAME, config.MQ_PASSWORD)
    return pika.ConnectionParameters(host=config.MQ_HOST, port=config.MQ_PORT, credentials=credentials, virtual_host=config.MQ_VIRTUAL_HOST, heartbeat=60)

def init():
    connection = pika.BlockingConnection(get_parameters())
    channel = connection.channel()

    channel.close()
    connection.close()
    logger.info('初始化MQ队列配置完成')


init()


class RabbitMQConsumer(threading.Thread):
    def __init__(self, queue_name: str, parameters=None, consumer_name: str = '', reconnect_interval: int = 5):
        super().__init__()
        self.queue_name = queue_name
        hostname = socket.gethostname()
        self.consumer_name = consumer_name or f"{hostname}-{id(self)}"
        self.connection = None
        self.channel = None
        self.reconnect_interval = reconnect_interval  # 重连间隔时间（秒）

        if not parameters:
            parameters = get_parameters()

        self.parameters = parameters

    def connect(self):
        self.stop()

        while True:
            try:
                self.connection = pika.BlockingConnection(self.parameters)
                self.channel = self.connection.channel()
                self.channel.queue_declare(queue=self.queue_name, durable=True)
                logger.info(f"Connected to RabbitMQ for consumer '{self.consumer_name}'")
                return
            except Exception as e:
                logger.error(f"Error connecting to RabbitMQConsumer: {e}")
                logger.info(f"Retrying connection in {self.reconnect_interval} seconds...")
                time.sleep(self.reconnect_interval)

    def callback(self, json_data):
        """
        子类实现实际消费逻辑
        """
        pass

    def callback_wrapper(self, ch, method, properties, body):
        """
        回调包装器，用于调用用户传入的回调函数。
        """
        try:
            json_data = json.loads(body)
        except json.JSONDecodeError as e:
            logger.error(f"JSON解析错误：{body}")
            ch.basic_ack(delivery_tag=method.delivery_tag)
            return

        logger.info(f"{self.consumer_name} - {self.queue_name} -  接收消息: {json_data}")
        try:
            self.callback(json_data)
            ch.basic_ack(delivery_tag=method.delivery_tag)
        except Exception as e:
            logger.error(f"{self.consumer_name} - 消费消息异常: {json_data} - Exception: {str(traceback.format_exc())}")
            ch.basic_reject(delivery_tag=method.delivery_tag, requeue=True)

    def run(self):
        while True:
            try:
                self.connect()
                self.channel.basic_qos(prefetch_count=5)
                self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.callback_wrapper, consumer_tag=self.consumer_name)
                logger.info(f"Starting consumer '{self.consumer_name}' for queue '{self.queue_name}'...")
                self.channel.start_consuming()
                return
            except Exception as e:
                logger.error(f"Starting consumer error: {e}, attempting to reconnect...")
                self.connect()

    def stop(self):
        if self.channel is not None and self.channel.is_open:
            self.channel.stop_consuming()
            self.channel.close()
        if self.connection is not None and self.connection.is_open:
            self.connection.close()

class RabbitMQProducer():
    def __init__(self, parameters=None, reconnect_interval: int = 5):
        self.connection = None
        self.channel = None
        self.lock = threading.Lock()
        self.reconnect_interval = reconnect_interval  # 重连间隔时间（秒）

        if not parameters:
            parameters = get_parameters()

        self.parameters = parameters
        self.connect()

    def connect(self):
        self.stop()

        while True:
            try:
                self.connection = pika.BlockingConnection(self.parameters)
                self.channel = self.connection.channel()
                logger.info(f"Connected to RabbitMQProducer")
                return
            except Exception as e:
                logger.error(f"Error connecting to RabbitMQProducer: {e}")
                logger.info(f"Retrying connection in {self.reconnect_interval} seconds...")
                time.sleep(self.reconnect_interval)

    def publish_message(self, exchange, routing_key, message):
        while True:
            try:
                with self.lock:
                    json_str = json.dumps(message, ensure_ascii=False)
                    logger.info(f'{exchange} - {routing_key} 发送消息: {json_str}')
                    self.channel.basic_publish(exchange=exchange, routing_key=routing_key, body=json_str)
                    return
            except Exception as e:
                logger.error(f"RabbitMQProducer publish_message error: {e}, attempting to reconnect...")
                self.connect()

    def stop(self):
        if self.channel is not None and self.channel.is_open:
            self.channel.close()
        if self.connection is not None and self.connection.is_open:
            self.connection.close()