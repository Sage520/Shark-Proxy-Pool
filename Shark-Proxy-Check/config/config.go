package config

import (
	"log"

	"github.com/spf13/viper"
)

type MainConfig struct {
	RabbitMQ RabbitMQConfig `mapstructure:"rabbitmq"`
	Check    CheckConfig    `mapstructure:"check"`
}

type RabbitMQConfig struct {
	Host        string `mapstructure:"host"`
	Port        string `mapstructure:"port"`
	Username    string `mapstructure:"username"`
	Password    string `mapstructure:"password"`
	VirtualHost string `mapstructure:"virtual-host"`
}

type CheckConfig struct {
	HttpUrl      string `mapstructure:"http-url"`
	HttpsUrl     string `mapstructure:"https-url"`
	AnonymousUrl string `mapstructure:"anonymous-url"`
	PostUrl      string `mapstructure:"post-url"`
	Thread       int    `mapstructure:"thread"`
	Timeout      int    `mapstructure:"timeout"`
}

var Config MainConfig

func init() {
	log.Println("配置初始化")

	// 设置配置文件名称和路径
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")
	viper.AddConfigPath("config")

	// 读取配置文件
	err := viper.ReadInConfig()
	if err != nil {
		log.Fatalf("无法读取配置文件: %s", err)
	}

	// 将配置文件内容解析到全局配置类中
	err = viper.Unmarshal(&Config)
	if err != nil {
		log.Fatalf("无法解析配置文件: %s", err)
	}
}
