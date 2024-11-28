# -*- coding: utf-8 -*-

import config
from mq.producer import send_to_add_queue
from fetcher.fetcher import ProxyFetcher
from util.log_util import get_logger
from apscheduler.triggers.cron import CronTrigger
from apscheduler.schedulers.blocking import BlockingScheduler

log = get_logger(__name__)
scheduler = BlockingScheduler()
p = ProxyFetcher()


def task_proxy(method_name):
    """
    抓取任务前置代理
    """
    log.info(f"任务: {method_name} 开始执行")

    fetch_method = getattr(p, method_name, None)

    count = 0
    for i in fetch_method():
        send_to_add_queue(i)
        count += 1

    log.info(f"任务: {method_name} 执行成功，抓取数量: {count}")


def start():
    """
    启动编排所有定时任务
    """
    # 编排定时任务
    for task_config in config.FETCHER_TASK_CONFIG:
        name_list = task_config['name']
        cron = task_config['cron']

        for name in name_list:
            # 首次执行
            task_proxy(name)

            # 编排调度
            log.info(f'任务：{name} cron: {cron} 定时初始化')
            trigger = CronTrigger.from_crontab(cron)
            scheduler.add_job(task_proxy, trigger, args=(name,), name=name, max_instances=1)

    # 启动调度器
    scheduler.start()
