# -*- coding: utf-8 -*-

import logging
from logging.handlers import RotatingFileHandler
import os
import socket

log_path = '/data/logs'
if not os.path.exists(log_path):
    os.makedirs(log_path)

# 获取主机名
hostname = socket.gethostname()

# 定义一个Filter，用于向record添加hostname
class HostNameFilter(logging.Filter):
    hostname = hostname

    def filter(self, record):
        record.hostname = HostNameFilter.hostname
        return True

def get_logger(class_name: str = __name__):
    """
    获取一个配置好的日志记录器。

    参数:
    class_name (str): 日志记录器的名称，默认为当前模块的名称。

    返回:
    logging.Logger: 配置好的日志记录器。
    """
    # 创建日志记录器
    logger = logging.getLogger(class_name)
    logger.setLevel(logging.DEBUG)

    # 创建 RotatingFileHandler 对象
    log_file = log_path + '/log.log'
    max_size = (1024 * 1024) * 100  # 1MB
    backup_count = 1  # 保留 5 个备份文件
    file_handler = RotatingFileHandler(log_file, maxBytes=max_size, backupCount=backup_count, encoding='utf-8')
    file_handler.setLevel(logging.DEBUG)

    # 创建控制台处理器
    console_handler = logging.StreamHandler()
    console_handler.setLevel(logging.DEBUG)

    # 定义日志格式
    formatter = logging.Formatter('%(asctime)s - %(hostname)s - %(levelname)s - %(name)s - %(message)s', datefmt='%Y-%m-%d %H:%M:%S')
    file_handler.setFormatter(formatter)
    console_handler.setFormatter(formatter)

    # 增加hostname
    hostname_filter = HostNameFilter()
    file_handler.addFilter(hostname_filter)
    console_handler.addFilter(hostname_filter)

    # 将文件处理器和控制台处理器添加到日志记录器
    if not any(isinstance(h, RotatingFileHandler) for h in logger.handlers):
        logger.addHandler(file_handler)
    if not any(isinstance(h, logging.StreamHandler) for h in logger.handlers):
        logger.addHandler(console_handler)

    return logger
