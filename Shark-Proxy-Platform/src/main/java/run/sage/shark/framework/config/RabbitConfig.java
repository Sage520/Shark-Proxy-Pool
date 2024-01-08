package run.sage.shark.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.sage.shark.common.constant.RabbitConstants;

import java.util.concurrent.TimeUnit;

/**
 * MQ配置
 *
 * @author Sage
 * @date 2022/11/18
 */
@Configuration
public class RabbitConfig {

    /**
     * json消息转换器
     *
     * @param objectMapper 对象映射器
     * @return {@link MessageConverter}
     */
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * 代理事件交换机
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange proxyEventExchange() {
        return ExchangeBuilder
                .topicExchange(RabbitConstants.MQ_PROXY_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 新增代理队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue proxyAddQueue() {
        return QueueBuilder
                .durable(RabbitConstants.MQ_PROXY_ADD_QUEUE)
                .build();
    }

    /**
     * 代理添加绑定
     *
     * @param proxyAddQueue      代理添加队列
     * @param proxyEventExchange 代理事件交换
     * @return {@link Binding}
     */
    @Bean
    public Binding proxyAddBinding(Queue proxyAddQueue, Exchange proxyEventExchange) {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */

        return BindingBuilder
                .bind(proxyAddQueue)
                .to(proxyEventExchange)
                .with(RabbitConstants.MQ_PROXY_ADD_QUEUE_KEY)
                .noargs();
    }

    /**
     * 更新代理队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue proxyUpdateQueue() {
        return QueueBuilder
                .durable(RabbitConstants.MQ_PROXY_UPDATE_QUEUE)
                .build();
    }

    /**
     * 代理绑定更新
     *
     * @param proxyUpdateQueue   代理更新队列
     * @param proxyEventExchange 代理事件交换
     * @return {@link Binding}
     */
    @Bean
    public Binding proxyUpdateBinding(Queue proxyUpdateQueue, Exchange proxyEventExchange) {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */

        return BindingBuilder
                .bind(proxyUpdateQueue)
                .to(proxyEventExchange)
                .with(RabbitConstants.MQ_PROXY_UPDATE_QUEUE_KEY)
                .noargs();
    }

    /**
     * 代理检查队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue proxyCheckQueue() {
        return QueueBuilder
                .durable(RabbitConstants.MQ_PROXY_CHECK_QUEUE)
                // 为检测队列设置超时时间，时间设定为6h，这里跟定时检测时间同步
                // 1 防止消费者消费能力不足，校验信息不重要，可丢弃
                // 2 防止消费者宕机，一次积压太多重复的数据
                .withArgument("x-message-ttl", TimeUnit.MINUTES.toMillis(5))
                .build();
    }

    /**
     * 代理检查绑定
     *
     * @param proxyCheckQueue    代理检查队列
     * @param proxyEventExchange 代理事件交换
     * @return {@link Binding}
     */
    @Bean
    public Binding proxyCheckBinding(Queue proxyCheckQueue, Exchange proxyEventExchange) {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */

        return BindingBuilder
                .bind(proxyCheckQueue)
                .to(proxyEventExchange)
                .with(RabbitConstants.MQ_PROXY_CHECK_QUEUE_KEY)
                .noargs();
    }

    /**
     * 获取日志事件交换
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange fetchLogEventExchange() {
        return ExchangeBuilder
                .topicExchange(RabbitConstants.MQ_FETCH_LOG_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 获取日志添加队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue fetchLogAddQueue() {
        return QueueBuilder
                .durable(RabbitConstants.MQ_FETCH_LOG_ADD_QUEUE)
                .build();
    }

    /**
     * 获取日志添加绑定
     *
     * @param fetchLogAddQueue      获取日志添加队列
     * @param fetchLogEventExchange 获取日志事件交换
     * @return {@link Binding}
     */
    @Bean
    public Binding fetchLogAddBinding(Queue fetchLogAddQueue, Exchange fetchLogEventExchange) {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */

        return BindingBuilder
                .bind(fetchLogAddQueue)
                .to(fetchLogEventExchange)
                .with(RabbitConstants.MQ_FETCH_LOG_ADD_QUEUE_KEY)
                .noargs();
    }

}
