# -*- coding: utf-8 -*-

from util.log_handler import LogHandler

log = LogHandler("main", file=False)

if __name__ == '__main__':
    log.info('程序启动')
