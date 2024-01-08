package mq

import (
	"encoding/json"
	"github.com/streadway/amqp"
	"log"
)

const exchange = "proxy-event-exchange"
const routingKey = "proxy.update"

// 发送结果至更新队列
func SendResultToUpdateQueue(data interface{}) {
	jsonBody, _ := json.Marshal(data)

	// 发送JSON消息到队列
	err := Client.ch.Publish(
		exchange,
		routingKey,
		false, // 是否强制性发布
		false, // 是否立即发布
		amqp.Publishing{
			ContentType: "application/json",
			Body:        jsonBody,
		},
	)
	if err != nil {
		log.Fatalf("无法发送消息：%v", err)
	}
}
