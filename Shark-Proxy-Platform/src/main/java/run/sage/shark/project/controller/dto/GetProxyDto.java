package run.sage.shark.project.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.sage.shark.common.constant.Constants;
import run.sage.shark.project.entity.Proxy;

/**
 * 获取代理API - Dto
 *
 * @author Sage
 * @date 2023/04/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetProxyDto extends Proxy {

    /**
     * 接口响应类型（json, txt）
     */
    private String respType = Constants.RESP_TYPE_JSON;

}
