package run.sage.shark.project.mq.to;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增代理TO
 *
 * @author Sage
 * @date 2023/02/03
 */
@Data
public class ProxyAddTo implements Serializable {

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
     * 数据来源
     */
    private String source;

}
