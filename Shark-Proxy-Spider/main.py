# -*- coding: utf-8 -*-

from util.log_handler import LogHandler
from util.job import start_task

log = LogHandler("main", file=False)

if __name__ == '__main__':
    log.info('程序启动')
    start_task()
