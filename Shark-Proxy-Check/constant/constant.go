package constant

const (
	// 类型 (1 = http 2 = https 3 = socks4 4 = socks5)
	TypeHttp   = 1
	TypeHttps  = 2
	TypeSocks4 = 3
	TypeSocks5 = 4

	// 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
	AnonymousTransparent = 1
	AnonymousOrdinary    = 2
	AnonymousAdvanced    = 3

	// 状态 (0 = 超时 1 = 存活)
	StatusTimeout  = 0
	StatusSurvival = 1

	// 能力支持 (0 = 不支持 1 = 支持)
	SupportNo  = 0
	SupportYes = 1
)
