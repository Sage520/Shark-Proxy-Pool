# -*- coding: utf-8 -*-

import json
from util.rabbit import send_msg_to_add_queue, add_fetch_log
from fetcher.proxySliceFetcher import ProxySliceFetcher
from fetcher.proxyFullFetcher import ProxyFullFetcher
import setting
import schedule
import time

# 处理单个代理
def handle_proxy(proxy, source):
    send_msg = dict()

    send_msg['source'] = source
    proxyInfo = proxy.split(':')
    send_msg['ip'] = proxyInfo[0]
    send_msg['port'] = proxyInfo[1]
    send_msg['type'] = int(proxyInfo[2])

    json_text = json.dumps(send_msg)
    send_msg_to_add_queue(json_text)

# 处理爬取日志
def handle_fetch_log(value, source):
    if source is not None and source != '':
        data = dict()
        data['value'] = value
        data['source'] = source

        data_json = json.dumps(data)
        add_fetch_log(data_json)

# 增量爬取
def slice_job():
    print("增量爬取任务启动......")

    p = ProxySliceFetcher()
    for fetch_source in setting.PROXY_SLICE_FETCHER:
        count = 0
        try:
            fetcher = getattr(p, fetch_source, None)
            for proxy in fetcher():
                handle_proxy(proxy, fetch_source)

                count += 1
        except Exception as e:
            print(e)
            continue
        finally:
            print(f'增量爬取：{fetch_source}，数量：{count}')
            handle_fetch_log(count, fetch_source)

# 全量爬取
def all_job():
    print("全量爬取任务启动......")

    p = ProxyFullFetcher()
    for fetch_source in setting.PROXY_All_FETCHER:
        count = 0

        try:
            fetcher = getattr(p, fetch_source, None)
            for proxy in fetcher():
                handle_proxy(proxy, fetch_source)

                count += 1
        except Exception as e:
            print(e)
            continue
        finally:
            print(f'全量爬取：{fetch_source}，数量：{count}')
            handle_fetch_log(count, fetch_source)

if __name__ == '__main__':
    print("爬虫程序启动......")

    all_job()
    slice_job()

    schedule.every().hour.do(all_job)
    schedule.every(5).minutes.do(slice_job)

    while True:
        schedule.run_pending()
        time.sleep(1)