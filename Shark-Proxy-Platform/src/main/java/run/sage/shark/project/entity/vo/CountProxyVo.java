package run.sage.shark.project.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 统计代理Vo
 *
 * @author Sage
 * @date 2023/02/22
 */
@Data
public class CountProxyVo implements Serializable {

    /**
     * 存活代理数
     */
    private long surviveCount;

    /**
     * 存活类型统计
     */
    private Map<String, Long> typeCount;

}
