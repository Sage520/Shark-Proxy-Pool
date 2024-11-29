package run.sage.shark.project.controller.vo.api;

import lombok.Data;
import run.sage.shark.common.enums.ProxyEnum;

import java.io.Serializable;

/**
 * 获取代理API - Vo
 *
 * @author Sage
 * @date 2023/02/22
 */
@Data
public class GetProxyVo implements Serializable {

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 类型
     */
    private String type;

    /**
     * 状态 (0 = 超时 1 = 存活)
     */
    private Integer status;

    /**
     * 国家
     */
    private String country;

    /**
     * 省 (只限国内)
     */
    private String province;

    /**
     * 响应时间 单位s 保留3位小数
     */
    private String respTime;

    /**
     * 最后校验时间
     */
    private Long lastCheckTime;

    /**
     * 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
     */
    private Integer anonymous;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportHttps;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportPost;

    public void setType(Integer type) {
        this.type = ProxyEnum.Type.getEnumByCode(type).getName();
    }

}
