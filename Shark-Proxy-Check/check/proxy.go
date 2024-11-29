package check

import (
	"encoding/json"
	"fmt"
	"net/http"
	"net/url"
	"regexp"
	"shark-proxy-check/constant"
	"time"

	"h12.io/socks"
)

// 代理实体类
type Proxy struct {
	IP            string  `json:"ip"`            // IP
	Port          string  `json:"port"`          // 端口
	Type          int     `json:"type"`          // 类型 (1 = http 2 = https 3 = socks4 4 = socks5)
	Anonymous     int     `json:"anonymous"`     // 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
	Status        int     `json:"status"`        // 状态 (0 = 超时 1 = 存活)
	SupportPost   int     `json:"supportPost"`   // 是否支持Post (0 = 不支持 1 = 支持)
	SupportHttps  int     `json:"supportHttps"`  // 是否支持Https (0 = 不支持 1 = 支持)
	LastCheckTime int64   `json:"lastCheckTime"` // 最后检查时间/时间戳
	RespTime      float64 `json:"respTime"`      // 响应时间 单位s 保留3位小数
	ProxyUrl      string  `json:"proxyUrl"`      // 代理地址
	FirstCheck    bool    `json:"firstCheck"`    // 是否为首次校验
}

// 构造函数
func NewProxyFromJSON(jsonStr string) (*Proxy, error) {
	var p Proxy
	err := json.Unmarshal([]byte(jsonStr), &p)
	if err != nil {
		return nil, err
	}

	// 组合代理地址
	if p.Type == constant.TypeHttp {
		p.ProxyUrl = fmt.Sprintf("http://%s:%s", p.IP, p.Port)
	}
	if p.Type == constant.TypeHttps {
		p.ProxyUrl = fmt.Sprintf("https://%s:%s", p.IP, p.Port)
	}
	if p.Type == constant.TypeSocks4 {
		p.ProxyUrl = fmt.Sprintf("socks4://%s:%s", p.IP, p.Port)
	}
	if p.Type == constant.TypeSocks5 {
		p.ProxyUrl = fmt.Sprintf("socks5://%s:%s", p.IP, p.Port)
	}

	return &p, nil
}

// 代理前置格式校验
func (proxy *Proxy) PreCheck() bool {
	pattern := `^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}`
	isMatch, err := regexp.MatchString(pattern, fmt.Sprintf("%s:%s", proxy.IP, proxy.Port))
	if err != nil {
		return false
	}

	return isMatch && proxy.CheckPort()
}

// 校验总入口
func (p *Proxy) Check() {
	var transport *http.Transport

	// 更新最后检查时间
	p.LastCheckTime = time.Now().UnixNano() / int64(time.Millisecond)

	if p.Type == constant.TypeHttp || p.Type == constant.TypeHttps {
		// HTTP HTTPS
		proxyURL, _ := url.Parse(p.ProxyUrl)
		transport = &http.Transport{
			Proxy: http.ProxyURL(proxyURL),
		}
	} else if p.Type == constant.TypeSocks4 || p.Type == constant.TypeSocks5 {
		// Socks4 Socks5
		dial := socks.Dial(p.ProxyUrl)
		transport = &http.Transport{Dial: dial}
	} else {
		// 忽略
		return
	}

	// 创建自定义的 Client，使用自定义的 Transport
	client := &http.Client{
		Transport: transport,
		Timeout:   time.Duration(timeout) * time.Second, // 设置请求超时时间
	}

	// 校验
	if p.FirstCheck {
		if p.PreCheck() {
			p.CheckAnonymous(client)
			p.CheckHttps(client)
			p.CheckPost(client)
		}
	} else {
		p.CheckProxy(client)
	}
}
