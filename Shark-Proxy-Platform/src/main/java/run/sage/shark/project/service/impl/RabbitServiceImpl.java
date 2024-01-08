package run.sage.shark.project.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.entity.to.ProxyCheckTo;
import run.sage.shark.project.entity.to.ProxyGetRegionTo;
import run.sage.shark.project.entity.to.ProxyUpdateTo;
import run.sage.shark.project.service.RabbitService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 兔子服务impl
 *
 * @author Sage
 * @date 2023/02/06
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendProxyToCheckQueue(List<ProxyCheckTo> proxies) {
        proxies.forEach(this::sendProxyToCheckQueue);
    }

    @Override
    public void sendProxyToCheckQueue(ProxyCheckTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_CHECK_QUEUE_KEY, proxy);
    }

    @Override
    public void sendProxyToUpdateQueue(ProxyUpdateTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_UPDATE_QUEUE_KEY, proxy);
    }

    @Override
    public void sendProxyToRegionQueue(ProxyGetRegionTo proxy) {
        rabbitTemplate.convertAndSend(RabbitConstants.MQ_PROXY_EXCHANGE, RabbitConstants.MQ_PROXY_REGION_QUEUE_KEY, proxy);
    }

}
