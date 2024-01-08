package mq

import (
	"github.com/streadway/amqp"
	"log"
	"shark-proxy-check/check"
	"shark-proxy-check/config"
	"shark-proxy-check/constant"
	"sync"
)

const queue = "proxy.check.queue"

var maxWorker = config.Config.Check.Thread
var wg sync.WaitGroup

// 待检测队列消费者
func Consume() {
	log.Printf("启动消费者")

	// 消费消息
	msgs, err := Client.ch.Consume(
		queue, // 队列名称
		"",    // 消费者标识符
		false, // 自动应答
		false, // 是否具有排他性
		false, // 是否阻塞等待
		false, // 是否消费者等待
		nil,   // 额外参数
	)
	if err != nil {
		log.Fatalf("无法消费消息: %v", err)
	}

	// 处理接收到的消息
	for i := 0; i < maxWorker; i++ {
		go handleMessages(msgs)
		wg.Add(1)
	}

	wg.Wait()
}

// 处理消息
func handleMessages(msgs <-chan amqp.Delivery) {
	defer wg.Done()

	for msg := range msgs {
		// 处理每条消息的逻辑
		proxy, _ := check.NewProxyFromJSON(string(msg.Body))
		proxy.Check()

		// 输出存活代理结果
		if constant.StatusSurvival == proxy.Status {
			log.Printf("Proxy: %s:%s, Status: %d, RespTime: %.3fs, Anonymous: %d\n",
				proxy.IP, proxy.Port, proxy.Status, proxy.RespTime, proxy.Anonymous)
		}

		// 发送RabbitMQ
		SendResultToUpdateQueue(proxy)

		// 确认消息已被处理
		err := msg.Ack(false)
		if err != nil {
			log.Printf("无法确认消息: %v", err)
		}
	}
}
