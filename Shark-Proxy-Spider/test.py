# -*- coding: utf-8 -*-

from mq.producer import send_to_add_queue
from fetcher.fetcher import ProxyFetcher

if __name__ == '__main__':
    for i in ProxyFetcher.ip3366():
        send_to_add_queue(i)
