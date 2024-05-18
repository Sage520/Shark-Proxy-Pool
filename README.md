<h1 align="center">Shark - Proxy</h1>

<p align="center">免费代理IP库 | Free Proxy</p>

<p align=center>
<a href="https://proxy.010438.xyz" target="_blank">首页</a> |
<a href="https://github.com/Sage520/Shark-Proxy-Pool" target="_blank">Github</a>
</p>

***

## 警告
本站免费代理IP均收集自互联网，本站不对免费代理的有效性负责，请合法使用免费代理，由用户使用免费代理IP带来的法律责任与本站无关。

## 项目架构
- Shark-Proxy-Platform
    - 平台 |  Java  | 用于总体调度，接口开放调用
- Shark-Proxy-Check
    - 校验 | Golang | 用于校验代理有效性，可用性
- Shark-Proxy-Spider
    - 爬虫 | Python | 用于开放免费代理抓取

## 快速开始
### 1. 安装 Docker 和 Docker Compose
确保你的系统已经安装了 Docker 和 Docker Compose。你可以按照 Docker 官方文档的指引进行安装：https://docs.docker.com/get-docker/

### 2. 克隆项目代码
使用以下命令克隆项目代码到本地：
```bash
git clone https://github.com/Sage520/Shark-Proxy-Pool
```

### 3. 启动容器
在项目根目录下执行以下命令，使用 Docker Compose 构建和启动容器：
```bash
cd compose
docker-compose up -d
```

该命令会自动下载并构建项目所需的 Docker 镜像，并在后台启动容器。

### 4. 访问应用程序
一旦容器启动成功，你可以通过浏览器访问应用程序。在浏览器中输入以下地址：
```
http://localhost:80
```

## 接口文档
* URL: https://proxy.010438.xyz/api/get
* 请求方式: get
* 请求参数

  | 参数名 | 数据类型 | 必传 | 说明 | 示例 |
  | :---|:---| :---| :--- | :--- |
  |port|string|false|端口号|8080, 80|
  |type|int|false|代理协议类型 （1 = http 2 = https 3 = socks4 4 = socks5）|1, 2, 3, 4|
  |anonymous|int|false|匿名类型 （1 = 透明 2 = 普匿 3 = 高匿）|1, 2, 3|
  |country|string|false|国家|中国, 美国, 外国 （除中国之外所有国家）|
  |province|string|false|省（只有中国地区数据才有此字段）|江苏省, 浙江省|
  |respType|string|false|接口响应格式（默认json）|json, txt|

* 响应数据
```json
{
    "msg": "success",
    "code": 200,
    "data": {
        "ip": "103.96.147.225",
        "port": "3128",
        "type": "http",
        "lastCheckTime": 1704689100061,
        "anonymous": 3
    }
}
```

``` text
183.89.115.39:80
```

## 访问频率限制
接口限制并发 1秒 / 5次，多次触流控会进入黑名单！

## 更新日志
- 2024.5.18 - 优化爬虫抓取逻辑，增加通用配置
- 2024.5.18 - 增加点击表格复制IP信息功能
- 2023.6.5 - 网页增加国际化支持（简体中文，English）
- 2023.5.27 - 增加代理存活率
- 2023.4.27 - API支持txt格式返回

## 参考项目
- [proxy_pool](https://github.com/jhao104/proxy_pool)
- [ProxyIpLib](https://github.com/jiangxianli/ProxyIpLib)

## 特别感谢
- [Lazycat](https://github.com/1054711110)

## Stargazers over time
[![Stargazers over time](https://starchart.cc/Sage520/Shark-Proxy-Pool.svg?variant=adaptive)](https://starchart.cc/Sage520/Shark-Proxy-Pool)
