package run.sage.shark.project.entity.to;

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

    private String ip;

    private String port;

    private String source;

    private Integer type;

}
