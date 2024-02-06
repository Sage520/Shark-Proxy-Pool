# -*- coding: utf-8 -*-

import re
import config
import time
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

            time.sleep(10)


    @staticmethod
    def kxdaili():
        """ 开心代理 """
        type = 1
        source = 'kxdaili.com'

        target_urls = ["http://www.kxdaili.com/dailiip.html", "http://www.kxdaili.com/dailiip/2/1.html"]
        for url in target_urls:
            tree = WebRequest().get(url).tree
            for tr in tree.xpath("//table[@class='active']//tr")[1:]:
                ip = "".join(tr.xpath('./td[1]/text()')).strip()
                port = "".join(tr.xpath('./td[2]/text()')).strip()
                yield "%s:%s:%s:%s" % (ip, port, type, source)

            time.sleep(10)
