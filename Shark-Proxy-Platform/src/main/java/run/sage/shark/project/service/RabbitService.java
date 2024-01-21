package run.sage.shark.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.mq.to.ProxyCheckTo;

import java.util.concurrent.TimeUnit;

/**
 * 兔子服务impl
 *
 * @author Sage
 * @date 2023/02/06
 */
@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送到代理校验队列
     *
     * @param proxy 代理校验TO
     */
    public void sendProxyToCheckQueue(ProxyCheckTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_CHECK_QUEUE_KEY, proxy);
    }

    /**
     * 发送到延迟代理校验队列
     *
     * @param proxy   代理校验TO
     * @param ttlTime 延迟时间(毫秒)
     */
    public void sendProxyToCheckQueue(ProxyCheckTo proxy, String ttlTime) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_DELAY_EXCHANGE, RabbitConstants.MQ_DELAY_PROXY_QUEUE_KEY, proxy, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setExpiration(ttlTime);
            return messagePostProcessor;
        });
    }

    /**
     * 发送代理下次校验信息到队列
     *
     * @param proxy 代理校验TO
     */
    public void sendProxyToNextCheckQueue(ProxyCheckTo proxy) {
        String ttlTime = String.valueOf(TimeUnit.HOURS.toMillis(6));
        this.sendProxyToCheckQueue(proxy, ttlTime);
    }

}
