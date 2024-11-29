package run.sage.shark.project.mq.to;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新代理TO
 *
 * @author Sage
 * @date 2023/02/03
 */
@Data
public class ProxyUpdateTo implements Serializable {

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 类型 (1 = http 2 = https 3 = socks4 4 = socks5)
     */
    private Integer type;

    /**
     * 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
     */
    private Integer anonymous;

    /**
     * 状态 (0 = 超时 1 = 存活)
     */
    private Integer status;

    /**
     * 最后检查时间/时间戳
     */
    private Long lastCheckTime;

    /**
     * 响应时间 单位s 保留3位小数
     */
    private Double respTime;

    /**
     * 是否是新增后的第一次校验
     */
    private Boolean firstCheck;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportHttps;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportPost;

}
