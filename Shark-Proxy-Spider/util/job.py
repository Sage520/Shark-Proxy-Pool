# -*- coding: utf-8 -*-

from schedule import every, repeat, run_pending
from fetcher.fetcher import ProxyFetcher
from util.log_handler import LogHandler
from mq.producer import send_to_add_queue

log = LogHandler('job', file=False)


def start_task():
    common_task()

    while True:
        run_pending()


@repeat(every(1).hour)
def common_task():
    log.info(f'common任务启动')

    for i in ProxyFetcher.common():
        send_to_add_queue(i)
