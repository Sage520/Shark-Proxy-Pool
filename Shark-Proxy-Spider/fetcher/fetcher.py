# -*- coding: utf-8 -*-

import re
import config
from util.log_handler import LogHandler
from util.web_request import WebRequest


log = LogHandler('fetcher', file=False)


class ProxyFetcher(object):
    """
    代理爬取类
    返回值：
        ip:port:type:source
    """

    @staticmethod
    def common():
        """
        ip:port 通用类爬取
        """
        for source_info in config.FETCHER_COMMON_SOURCE:
            url = source_info['url']
            type = source_info['type']
            source = source_info['source']

            try:
                res = WebRequest().get(url=url).response
                if res.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, res.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield f'{_}:{type}:{source}'
            except Exception as e:
                log.error('common抓取异常: ', e)
