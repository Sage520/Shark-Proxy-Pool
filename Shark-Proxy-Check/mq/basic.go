package mq

import (
	"fmt"
	"log"
	"os"
	"os/signal"
	"shark-proxy-check/config"
	"syscall"

	"github.com/streadway/amqp"
)

var host = config.Config.RabbitMQ.Host
var port = config.Config.RabbitMQ.Port
var username = config.Config.RabbitMQ.Username
var password = config.Config.RabbitMQ.Password
var virtualHost = config.Config.RabbitMQ.VirtualHost

type RabbitMQClient struct {
	conn *amqp.Connection
	ch   *amqp.Channel
}

var Client *RabbitMQClient

// 初始化
func init() {
	newClient()

	// 注册信号处理程序，以便在收到中断信号时优雅地关闭连接
	sig := make(chan os.Signal, 1)
	signal.Notify(sig, os.Interrupt, syscall.SIGTERM)
	go func() {
		<-sig
		clientClose()
		os.Exit(0)
	}()
}

// 创建新链接
func newClient() {
	url := fmt.Sprintf("amqp://%s:%s@%s:%s/%s", username, password, host, port, virtualHost)

	conn, err := amqp.Dial(url)
	if err != nil {
		log.Fatalf("无法连接到RabbitMQ: %v", err)
	}

	ch, err := conn.Channel()
	if err != nil {
		conn.Close()
		log.Fatalf("无法打开通道: %v", err)
	}

	Client = &RabbitMQClient{
		conn: conn,
		ch:   ch,
	}
}

// 断开链接
func clientClose() {
	if Client.ch != nil {
		if err := Client.ch.Close(); err != nil {
			log.Fatalf("无法关闭通道: %v", err)
		}
	}

	if Client.conn != nil {
		if err := Client.conn.Close(); err != nil {
			log.Fatalf("无法关闭连接: %v", err)
		}
	}
}
