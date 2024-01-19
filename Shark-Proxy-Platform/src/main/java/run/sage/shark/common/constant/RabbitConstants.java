package run.sage.shark.common.constant;

/**
 * MQ常量
 *
 * @author Sage
 * @date 2023/02/02
 */
public class RabbitConstants {

    /**
     * 代理事件交换机
     */
    public static final String MQ_PROXY_EXCHANGE = "proxy-event-exchange";

    /**
     * 代理新增队列
     */
    public static final String MQ_PROXY_ADD_QUEUE = "proxy.add.queue";

    /**
     * 代理新增队列 key
     */
    public static final String MQ_PROXY_ADD_QUEUE_KEY = "proxy.add";

    /**
     * 代理更新队列
     */
    public static final String MQ_PROXY_UPDATE_QUEUE = "proxy.update.queue";

    /**
     * 代理更新队列 key
     */
    public static final String MQ_PROXY_UPDATE_QUEUE_KEY = "proxy.update";

    /**
     * 代理检查队列
     */
    public static final String MQ_PROXY_CHECK_QUEUE = "proxy.check.queue";

    /**
     * 代理检查队列 key
     */
    public static final String MQ_PROXY_CHECK_QUEUE_KEY = "proxy.check";

    /**
     * 代理地区队列
     */
    public static final String MQ_PROXY_REGION_QUEUE = "proxy.region.queue";

    /**
     * 代理地区队列 key
     */
    public static final String MQ_PROXY_REGION_QUEUE_KEY = "proxy.region";

    /**
     * 抓取日志事件交换机
     */
    public static final String MQ_FETCH_LOG_EXCHANGE = "fetchLog-event-exchange";

    /**
     * 抓取日志新增队列
     */
    public static final String MQ_FETCH_LOG_ADD_QUEUE = "fetchLog.add.queue";

    /**
     * 抓取日志新增队列 key
     */
    public static final String MQ_FETCH_LOG_ADD_QUEUE_KEY = "fetchLog.add";

    /**
     * 死信事件交换机
     */
    public static final String MQ_DEAD_EXCHANGE = "dead-event-exchange";

    /**
     * 死信代理队列
     */
    public static final String MQ_DEAD_PROXY_QUEUE = "dead.proxy.queue";

    /**
     * 死信代理 Key
     */
    public static final String MQ_DEAD_PROXY_QUEUE_KEY = "dead.proxy";

}
