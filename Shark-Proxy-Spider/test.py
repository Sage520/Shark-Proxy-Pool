# -*- coding: utf-8 -*-

from mq.producer import send_to_add_queue
from fetcher.fetcher import ProxyFetcher

if __name__ == '__main__':
    for i in ProxyFetcher.common():
        send_to_add_queue(i)
