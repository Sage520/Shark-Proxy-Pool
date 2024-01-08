# -*- coding: utf-8 -*-

# ############# RabbitMQ 配置信息 #################
MQ_HOST = 'service-rabbitmq'
MQ_PORT = 5672
MQ_USERNAME = 'sage'
MQ_PASSWORD = '!EVk@vSZSx9S'
MQ_VIRTUAL_HOST = '/'

# ############# 全量爬虫源方法 #################
PROXY_All_FETCHER = [
    "spider_89ip",
    "spider_66ip",
    "spider_fatezero",
    "spider_zevtyardt",
    "spider_proxy_list",
    "spider_proxyscrape",
    "spider_sunny9577",
    "spider_TheSpeedX"
]

# ############# 增量爬虫源方法 #################
PROXY_SLICE_FETCHER = [
]
