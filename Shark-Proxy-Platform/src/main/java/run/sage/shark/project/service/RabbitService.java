package run.sage.shark.project.service;

import run.sage.shark.project.entity.to.ProxyCheckTo;
import run.sage.shark.project.entity.to.ProxyGetRegionTo;
import run.sage.shark.project.entity.to.ProxyUpdateTo;

import java.util.List;

/**
 * 兔子服务
 *
 * @author Sage
 * @date 2023/02/06
 */
public interface RabbitService {

    /**
     * 发送代理到检查队列
     *
     * @param proxies 代理
     */
    void sendProxyToCheckQueue(List<ProxyCheckTo> proxies);

    /**
     * 发送代理到检查队列
     *
     * @param proxy 代理
     */
    void sendProxyToCheckQueue(ProxyCheckTo proxy);

    /**
     * 发送代理到更新队列
     *
     * @param proxy 代理
     */
    void sendProxyToUpdateQueue(ProxyUpdateTo proxy);

    /**
     * 发送代理到地区队列
     *
     * @param proxy 代理
     */
    void sendProxyToRegionQueue(ProxyGetRegionTo proxy);

}
