# -*- coding: utf-8 -*-

import pika
import setting
import threading
from retry import retry


sendMsgLock = threading.Lock()

def initConn():
    """ 初始化连接 """
    global sendMsgLock
    credentials = pika.PlainCredentials(setting.MQ_USERNAME, setting.MQ_PASSWORD)  # mq用户名和密码
    # 虚拟队列需要指定参数 virtual_host，如果是默认的可以不填。
    connection = pika.BlockingConnection(pika.ConnectionParameters(host = setting.MQ_HOST,port = setting.MQ_PORT,virtual_host = setting.MQ_VIRTUAL_HOST,credentials = credentials, heartbeat=0))
    return connection

connection = initConn()
channel = connection.channel()

@retry(pika.exceptions.AMQPConnectionError, delay=5, jitter=(1, 3))
def sendMsgToMq(exchange, routing_key, json_text):
    """发送一个消息到MQ"""
    global channel, sendMsgLock, connection

    sendMsgLock.acquire()

    if not channel.is_open:
        if connection.is_open:
            channel = connection.channel()
        else:
            connection = initConn()
            channel = connection.channel()

    channel.basic_publish(exchange = exchange,routing_key = routing_key,body = json_text)

    sendMsgLock.release()

def send_msg_to_add_queue(json_text):
    """ 发送一个消息到校验队列 """
    sendMsgToMq('proxy-event-exchange', 'proxy.add', json_text)

def add_fetch_log(json_text):
    """ 发送一个消息到校验队列 """
    sendMsgToMq('fetchLog-event-exchange', 'fetchLog.add', json_text)