package run.sage.shark.project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 抓取日志
 *
 * @author sage
 * @date 2023/05/13
 */
@Data
@Document(collection = "fetch_log")
@EqualsAndHashCode(callSuper = true)
public class FetchLog extends BaseEntity {

    /**
     * 抓取数量
     */
    private Integer value;

    /**
     * 数据来源
     */
    private String source;

}
