# -*- coding: utf-8 -*-

import re
import requests
import json
import time
from util.webRequest import WebRequest


class ProxyFullFetcher(object):
    """
    代理全量爬取
    """

    @staticmethod
    def spider_89ip():
        """
        89免费代理 https://www.89ip.cn/
        """
        url = f"http://api.89ip.cn/tqdl.html?api=1&num=8888&port=&address=&isp=&_t={str(time.time())}"
        try:
            req = requests.get(url=url)
            if req.status_code == 200:
                regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                _proxy = re.findall(regex, req.text)
                for _ in _proxy:
                    if _ is not None and _ != '' and ':' in _:
                        yield _ + ":1"
        except Exception as e:
            print(e)

    @staticmethod
    def spider_66ip():
        """
        66免费代理 http://www.66ip.cn/index.html
        """
        url = f"http://www.66ip.cn/mo.php?sxb=&tqsl=8888&port=&export=&ktip=&sxa=&submit=%CC%E1++%C8%A1&textarea=http%3A%2F%2Fwww.66ip.cn%2F%3Fsxb%3D%26tqsl%3D8888%26ports%255B%255D2%3D%26ktip%3D%26sxa%3D%26radio%3Dradio%26submit%3D%25CC%25E1%2B%2B%25C8%25A1&_t={str(time.time())}"
        headers = {
            "Referer": "http://www.66ip.cn/pt.html",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
        }
        try:
            req = requests.get(url=url, headers=headers)
            if req.status_code == 200:
                regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                _proxy = re.findall(regex, req.text)
                for _ in _proxy:
                    if _ is not None and _ != '' and ':' in _:
                        yield _ + ":1"
        except Exception as e:
            print(e)

    @staticmethod
    def spider_fatezero():
        """ FateZero http://proxylist.fatezero.org/ """
        type_map = {"http": 1, "https": 2, "socks4": 3, "socks5": 4}

        url = f"http://proxylist.fatezero.org/proxy.list?_t={str(time.time())}"
        try:
            resp_text = WebRequest().get(url).text
            for each in resp_text.split("\n"):
                json_info = json.loads(each)
                yield "%s:%s:%s" % (json_info.get("host", ""), json_info.get("port", ""), type_map[json_info.get("type", "")])
        except Exception as e:
            print(e)

    @staticmethod
    def spider_zevtyardt():
        """ zevtyardt/proxy-list https://github.com/zevtyardt/proxy-list """
        type_map = {"http": 1, "socks4": 3, "socks5": 4}

        for key in type_map:
            time.sleep(5)
            url = f"https://raw.githubusercontent.com/zevtyardt/proxy-list/main/{key}.txt"
            try:
                req = requests.get(url=url)
                if req.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, req.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield _ + ":" + str(type_map[key])
            except Exception as e:
                print(e)

    @staticmethod
    def spider_proxy_list():
        """ proxy-list.download https://www.proxy-list.download """
        type_map = {"http": 1, "socks4": 3, "socks5": 4}

        for key in type_map:
            time.sleep(5)
            url = f'https://www.proxy-list.download/api/v1/get?type={key}&_t={str(time.time())}'
            try:
                req = requests.get(url=url)
                if req.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, req.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield _ + ":" + str(type_map[key])
            except Exception as e:
                print(e)

    @staticmethod
    def spider_proxyscrape():
        """ ProxyScrape https://api.proxyscrape.com/ """
        type_map = {"http": 1, "https": 2, "socks4": 3, "socks5": 4}

        for key in type_map:
            time.sleep(5)
            url = f'https://api.proxyscrape.com/?request=displayproxies&proxytype={key}&_t={str(time.time())}'
            try:
                req = requests.get(url=url)
                if req.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, req.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield _ + ":" + str(type_map[key])
            except Exception as e:
                print(e)

    @staticmethod
    def spider_sunny9577():
        """ sunny9577/proxy-scraper https://github.com/sunny9577/proxy-scraper """
        type_map = {"http": 1, "socks4": 3, "socks5": 4}

        for key in type_map:
            time.sleep(5)
            url = f"https://sunny9577.github.io/proxy-scraper/generated/{key}_proxies.txt"
            try:
                req = requests.get(url=url)
                if req.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, req.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield _ + ":" + str(type_map[key])
            except Exception as e:
                print(e)

    @staticmethod
    def spider_TheSpeedX():
        """ TheSpeedX/PROXY-List https://github.com/TheSpeedX/PROXY-List """
        type_map = {"http": 1, "socks4": 3, "socks5": 4}

        for key in type_map:
            time.sleep(5)
            url = f"https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/{key}.txt"
            try:
                req = requests.get(url=url)
                if req.status_code == 200:
                    regex = r"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}"
                    _proxy = re.findall(regex, req.text)
                    for _ in _proxy:
                        if _ is not None and _ != '' and ':' in _:
                            yield _ + ":" + str(type_map[key])
            except Exception as e:
                print(e)