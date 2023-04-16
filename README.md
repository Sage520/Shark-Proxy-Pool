<h1 align="center">Shark - Proxy</h1>

<p align="center">免费代理IP库 | Free Proxy</p>

<p align=center>
<a href="https://proxy.sage.run" target="_blank">首页</a> |
<a href="https://github.com/Sage520/shark_proxy" target="_blank">Github</a>
</p>

***

## 警告
本站免费代理IP均收集自互联网，本站不对免费代理的有效性负责，请合法使用免费代理，由用户使用免费代理IP带来的法律责任与本站无关。

## 接口文档
* URL: https://proxy.sage.run/api/get
* 请求参数

  | 参数名 | 数据类型 | 必传 | 说明 | 示例 |
      | :---|:---| :---| :--- | :--- |
  |port|string|false|端口号|8080, 80|
  |type|int|false|代理协议类型 （1 = http 2 = https）|1, 2|
  |anonymous|int|false|匿名类型 （1 = 透明 2 = 普匿 3 = 高匿）|1, 2, 3|
  |country|string|false|国家|中国, 美国, 外国 （除中国之外所有国家）|
  |province|string|false|省（只有中国地区数据才有此字段）|江苏省, 浙江省|

* 响应数据
```json
{
    "msg":"操作成功",
    "code":200,
    "data":{
        "ip":"183.89.115.39",
        "port":"8080",
        "type":"http"
    }
}
```

## 访问频率限制
接口限制并发 1秒 / 5次，多次触流控会进入黑名单！

## TODO
- 平台 - 优化前端页面
- 平台 - 支持 Socks5 类型
- 爬虫 - 支持更多数据源
- 校验器 - 使用 Golang 重写


## 参考项目
- [proxy_pool](https://github.com/jhao104/proxy_pool)
- [ProxyIpLib](https://github.com/jiangxianli/ProxyIpLib)

## 特别感谢
- [Lazycat](https://github.com/1054711110)

## Stargazers over time
[![Stargazers over time](https://starchart.cc/Sage520/shark_proxy.svg)](https://starchart.cc/Sage520/shark_proxy)
