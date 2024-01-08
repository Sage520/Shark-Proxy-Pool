package run.sage.shark.project.mq.to;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增抓取日志TO
 *
 * @author sage
 * @date 2023/05/13
 */
@Data
public class FetchLogAddTo implements Serializable {

    /**
     * 抓取数量
     */
    private Integer value;

    /**
     * 数据来源
     */
    private String source;

}
