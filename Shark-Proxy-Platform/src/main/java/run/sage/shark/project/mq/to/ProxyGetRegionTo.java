package run.sage.shark.project.mq.to;

import lombok.Data;

/**
 * 代理获取地区
 *
 * @author Sage
 * @date 2023/02/26
 */
@Data
public class ProxyGetRegionTo {

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

}
