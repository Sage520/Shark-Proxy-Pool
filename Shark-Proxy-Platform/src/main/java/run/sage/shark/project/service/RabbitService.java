package run.sage.shark.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.mq.to.ProxyCheckTo;
import run.sage.shark.project.mq.to.ProxyUpdateTo;

import javax.annotation.Resource;

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

    public void sendProxyToCheckQueue(ProxyCheckTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_CHECK_QUEUE_KEY, proxy);
    }

    public void sendProxyToUpdateQueue(ProxyUpdateTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_UPDATE_QUEUE_KEY, proxy);
    }

}
