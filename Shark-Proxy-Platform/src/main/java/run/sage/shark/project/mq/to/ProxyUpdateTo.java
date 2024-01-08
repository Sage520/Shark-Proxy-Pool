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

    private String ip;

    private String port;

    private Integer status;

    private Long lastCheckTime;

    private Double respTime;

    /**
     * 是否是新增后的第一次校验
     */
    private Boolean firstCheck;

    private Integer type;

    private Integer anonymous;

}
