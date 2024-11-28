# -*- coding: utf-8 -*-

from util.log_util import get_logger
from util.job import start

log = get_logger(__name__)

if __name__ == '__main__':
    log.info('程序启动')
    start()
