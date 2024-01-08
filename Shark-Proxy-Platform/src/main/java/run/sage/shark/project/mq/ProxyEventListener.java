package run.sage.shark.project.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.entity.to.ProxyAddTo;
import run.sage.shark.project.entity.to.ProxyUpdateTo;
import run.sage.shark.project.service.ProxyService;

/**
 * MQ代理事件监听器
 *
 * @author Sage
 * @date 2023/02/03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProxyEventListener {

    private final ProxyService proxyService;

    /**
     * 新增队列
     *
     * @param proxy 代理
     */
    @RabbitListener(queues = RabbitConstants.MQ_PROXY_ADD_QUEUE)
    public void add(ProxyAddTo proxy) {
        try {
            proxyService.addProxy(proxy);
        } catch (DuplicateKeyException e) {
            log.info("代理新增重复");
        } catch (Exception e) {
            log.error("代理新增队列消费出错: {}", e.getMessage());
        }
    }

    /**
     * 更新队列
     *
     * @param proxy 代理
     */
    @RabbitListener(queues = RabbitConstants.MQ_PROXY_UPDATE_QUEUE)
    public void update(ProxyUpdateTo proxy) {
        try {
            proxyService.updateProxy(proxy);
        } catch (Exception e) {
            log.error("代理更新队列消费出错: {}", e.getMessage());
        }
    }
}
