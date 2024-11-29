package check

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net"
	"net/http"
	"shark-proxy-check/config"
	"shark-proxy-check/constant"
	"strings"
	"time"
)

var httpUrl = config.Config.Check.HttpUrl
var anonymousUrl = config.Config.Check.AnonymousUrl
var httpsUrl = config.Config.Check.HttpsUrl
var postUrl = config.Config.Check.PostUrl
var timeout = config.Config.Check.Timeout

func (proxy *Proxy) CheckProxy(client *http.Client) {
	// 创建一个请求对象
	req, err := http.NewRequest("GET", httpUrl, nil)
	if err != nil {
		return
	}

	// 发送请求并计算响应时间
	startTime := time.Now()
	resp, err := client.Do(req)
	if err != nil {
		return
	}
	defer resp.Body.Close()
	elapsedTime := time.Since(startTime).Seconds()

	// 检查响应状态码
	if resp.StatusCode == http.StatusOK {
		// 更新代理的状态和响应时间
		proxy.Status = constant.StatusSurvival
		proxy.RespTime = elapsedTime
	} else {
		// 更新代理的状态为超时
		proxy.Status = constant.StatusTimeout
	}
}

type HttpBinResponse struct {
	Args    struct{} `json:"args"`
	Headers struct {
		AcceptEncoding string `json:"Accept-Encoding"`
		Host           string `json:"Host"`
		UserAgent      string `json:"User-Agent"`
		XAmznTraceId   string `json:"X-Amzn-Trace-Id"`
	} `json:"headers"`
	Origin          string `json:"origin"`
	Url             string `json:"url"`
	ProxyConnection string `json:"Proxy-Connection"`
	XForwardedFor   string `json:"X-Forwarded-For"`
	XForwardedPort  string `json:"X-Forwarded-Port"`
	XForwardedProto string `json:"X-Forwarded-Proto"`
}

func (proxy *Proxy) CheckAnonymous(client *http.Client) {
	// 创建一个请求对象
	req, err := http.NewRequest("GET", anonymousUrl, nil)
	if err != nil {
		return
	}

	// 发送请求
	startTime := time.Now()
	resp, err := client.Do(req)
	if err != nil {
		return
	}
	defer resp.Body.Close()

	// 读取响应体
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return
	}

	// 解析响应体
	var httpBinResp HttpBinResponse
	err = json.Unmarshal(body, &httpBinResp)
	if err != nil {
		return
	}

	// 检查是否成功
	if httpBinResp.Origin != "" {
		// 更新代理状态和响应时间
		proxy.Status = constant.StatusSurvival
		respTime := time.Since(startTime).Seconds()
		proxy.RespTime = respTime

		// 判断匿名度级别
		if strings.Contains(httpBinResp.Origin, ",") {
			proxy.Anonymous = constant.AnonymousTransparent
		} else if httpBinResp.ProxyConnection != "" || httpBinResp.XForwardedFor != "" ||
			httpBinResp.XForwardedPort != "" || httpBinResp.XForwardedProto != "" {
			proxy.Anonymous = constant.AnonymousOrdinary
		} else {
			proxy.Anonymous = constant.AnonymousAdvanced
		}
	}
}

func (proxy *Proxy) CheckHttps(client *http.Client) {
	// 创建一个请求对象
	req, err := http.NewRequest("GET", httpsUrl, nil)
	if err != nil {
		return
	}

	// 发送请求
	resp, err := client.Do(req)
	if err != nil {
		return
	}
	defer resp.Body.Close()

	// 检查是否成功
	if resp.StatusCode == http.StatusOK {
		proxy.SupportHttps = constant.SupportYes
	}
}

func (proxy *Proxy) CheckPost(client *http.Client) {
	// 创建一个请求对象
	req, err := http.NewRequest("POST", postUrl, nil)
	if err != nil {
		return
	}

	// 发送请求
	resp, err := client.Do(req)
	if err != nil {
		return
	}
	defer resp.Body.Close()

	// 检查是否成功
	if resp.StatusCode == http.StatusOK {
		proxy.SupportPost = constant.SupportYes
	}
}

// 检测指定端口是否开放
func (proxy *Proxy) CheckPort() bool {
	conn, err := net.DialTimeout("tcp", fmt.Sprintf("%s:%s", proxy.IP, proxy.Port), time.Duration(timeout)*time.Second)
	if err != nil {
		return false
	}
	defer conn.Close()

	return true
}
