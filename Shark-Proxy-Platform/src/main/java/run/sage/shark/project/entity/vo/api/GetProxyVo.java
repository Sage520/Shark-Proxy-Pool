package run.sage.shark.project.entity.vo.api;

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

    private String ip;

    private String port;

    private String type;

    public void setType(Integer type) {
        this.type = ProxyEnum.Type.getEnumByCode(type).getName();
    }

}
