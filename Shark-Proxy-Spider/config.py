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
        'url': 'http://api.89ip.cn/tqdl.html?api=1&num=8000&port=&address=&isp=',
        'type': 1,
        'source': '89ip'
    }
]

# ############# 抓取任务配置 #################
FETCHER_TASK_CONFIG = [
    {
        # 每小时执行一次
        'name': ['common'],
        'cron': '0 * * * *'
    },
    {
        # 每10分钟执行一次
        'name': ['kxdaili', 'kuaidaili', 'ip3366'],
        'cron': '*/10 * * * *'
    }
]
