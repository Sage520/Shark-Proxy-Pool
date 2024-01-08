# -*- coding: utf-8 -*-

from fetcher.proxyFullFetcher import ProxyFullFetcher
from fetcher.proxySliceFetcher import ProxySliceFetcher

if __name__ == '__main__':
    # p = ProxyFullFetcher()
    # count = 0
    # for _ in p.freeProxy110():
    #     print(_)
    #     count += 1
    #
    # print(count)


    p2 = ProxySliceFetcher()
    count = 0
    for _ in p2.freeProxy207():
        print(_)
        count += 1

    print(count)
