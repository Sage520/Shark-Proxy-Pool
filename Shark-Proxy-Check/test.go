package main

import (
	"fmt"
	"shark-proxy-check/check"
	"time"
)

func main() {

	// http
	//proxy, _ := check.NewProxyFromJSON("{\"ip\": \"72.10.160.90\", \"port\": \"23743\", \"type\": 1, \"firstCheck\": true}")

	// socks4
	//proxy, _ := check.NewProxyFromJSON("{\"ip\": \"64.20.62.75\", \"port\": \"12276\", \"type\": 3, \"firstCheck\": true}")

	// socks5
	//proxy, _ := check.NewProxyFromJSON("{\"ip\": \"127.0.0.1\", \"port\": \"10808\", \"type\": 4, \"firstCheck\": true}")

	//proxy.Check()
	//fmt.Println(proxy)

	//CheckPort
	start := time.Now() // 记录开始时间

	proxy3, _ := check.NewProxyFromJSON("{\"ip\": \"1.1.1.1\", \"port\": \"80\", \"type\": 4, \"firstCheck\": true}")
	fmt.Println(proxy3.CheckPort())

	elapsed := time.Since(start) // 计算经过的时间

	fmt.Printf("代码执行耗时：%s\n", elapsed)
}
