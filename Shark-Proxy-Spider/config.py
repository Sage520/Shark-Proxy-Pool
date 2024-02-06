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
    },
    {
        'url': 'https://www.proxy-list.download/api/v1/get?type=http',
        'type': 1,
        'source': 'proxy-list.download'
    },
    {
        'url': 'https://www.proxy-list.download/api/v1/get?type=socks4',
        'type': 3,
        'source': 'proxy-list.download'
    },
    {
        'url': 'https://www.proxy-list.download/api/v1/get?type=socks5',
        'type': 4,
        'source': 'proxy-list.download'
    },
    {
        'url': 'https://api.proxyscrape.com/?request=displayproxies&proxytype=http',
        'type': 1,
        'source': 'proxyscrape'
    },
    {
        'url': 'https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4',
        'type': 3,
        'source': 'proxyscrape'
    },
    {
        'url': 'https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5',
        'type': 4,
        'source': 'proxyscrape'
    },
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
    {
        'url': 'https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/http.txt',
        'type': 1,
        'source': 'TheSpeedX/PROXY-List'
    },
    {
        'url': 'https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks4.txt',
        'type': 3,
        'source': 'TheSpeedX/PROXY-List'
    },
    {
        'url': 'https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt',
        'type': 4,
        'source': 'TheSpeedX/PROXY-List'
    },
    {
        'url': 'https://raw.githubusercontent.com/sunny9577/proxy-scraper/master/generated/http_proxies.txt',
        'type': 1,
        'source': 'sunny9577/proxy-scraper'
    },
    {
        'url': 'https://raw.githubusercontent.com/sunny9577/proxy-scraper/master/generated/socks4_proxies.txt',
        'type': 3,
        'source': 'sunny9577/proxy-scraper'
    },
    {
        'url': 'https://raw.githubusercontent.com/sunny9577/proxy-scraper/master/generated/socks5_proxies.txt',
        'type': 4,
        'source': 'sunny9577/proxy-scraper'
    },
    {
        'url': 'https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/http.txt',
        'type': 1,
        'source': 'monosans/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/socks4.txt',
        'type': 3,
        'source': 'monosans/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/socks5.txt',
        'type': 4,
        'source': 'monosans/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/mmpx12/proxy-list/master/http.txt',
        'type': 1,
        'source': 'mmpx12/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/mmpx12/proxy-list/master/socks4.txt',
        'type': 3,
        'source': 'mmpx12/proxy-list'
    },
    {
        'url': 'https://raw.githubusercontent.com/mmpx12/proxy-list/master/socks5.txt',
        'type': 4,
        'source': 'mmpx12/proxy-list'
    }
]

# ############# 抓取任务配置 #################
FETCHER_TASK_CONFIG = [
    {
        # 每小时执行一次
        'name': 'common',
        'cron': '0 * * * *'
    },
    {
        # 每10分钟执行一次
        'name': 'kxdaili',
        'cron': '*/10 * * * *'
    }
]
