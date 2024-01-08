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
     * 最后校验时间
     */
    private Long lastCheckTime;

    /**
     * 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
     */
    private Integer anonymous;

    public void setType(Integer type) {
        this.type = ProxyEnum.Type.getEnumByCode(type).getName();
    }

}
