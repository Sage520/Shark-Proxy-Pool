# -*- coding: utf-8 -*-

# RabbitMQ 配置信息
MQ_HOST = '1.1.1.1'
MQ_PORT = 6002
MQ_USERNAME = 'sage'
MQ_PASSWORD = '!EVk@vSZSx9S'
MQ_VIRTUAL_HOST = '/'

# ############# 通用爬虫来源 #################
FETCHER_COMMON_SOURCE = [
    {
        'url': 'https://raw.githubusercontent.com/zevtyardt/proxy-list/main/http.txt',
        'type': 1,
        'source': 'zevtyardt/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/zevtyardt/proxy-list/main/socks4.txt',
        'type': 3,
        'source': 'zevtyardt/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/zevtyardt/proxy-list/main/socks5.txt',
        'type': 4,
        'source': 'zevtyardt/proxy-list'
    },
]

# ############# 抓取任务定义 #################
FETCHER_TASK_INFO = [
    {
        'method_name': 'common',
        'cron': '0 0 0 0 0 0'
    }
]
