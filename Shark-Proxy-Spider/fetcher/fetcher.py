# -*- coding: utf-8 -*-

import re
import config
import time
from util.log_util import get_logger
from util.web_request import WebRequest

log = get_logger(__name__)

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

    @staticmethod
    def kuaidaili(page_count=5):
        """ 快代理 https://www.kuaidaili.com """
        type = 1
        source = 'kuaidaili.com'

        url_pattern = [
            'https://www.kuaidaili.com/free/inha/{}/',
            'https://www.kuaidaili.com/free/intr/{}/',
            'https://www.kuaidaili.com/free/fps/{}/',
        ]
        url_list = []
        for page_index in range(1, page_count + 1):
            for pattern in url_pattern:
                url_list.append(pattern.format(page_index))

        for url in url_list:
            tree = WebRequest().get(url).tree
            proxy_list = tree.xpath('.//table//tr')
            time.sleep(1)  # 必须sleep 不然第二条请求不到数据
            for tr in proxy_list[1:]:
                yield ':'.join(tr.xpath('./td/text()')[0:2]) + f':{type}:{source}'

        time.sleep(10)

    @staticmethod
    def ip3366():
        """ 云代理 """
        type = 1
        source = 'ip3366.net'

        urls = ['http://www.ip3366.net/free/?stype=1', "http://www.ip3366.net/free/?stype=2"]
        for url in urls:
            r = WebRequest().get(url, timeout=10)
            proxies = re.findall(r'<td>(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})</td>[\s\S]*?<td>(\d+)</td>', r.text)
            for proxy in proxies:
                yield ":".join(proxy) + f':{type}:{source}'
