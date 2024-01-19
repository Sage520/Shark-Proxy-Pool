package run.sage.shark.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 代理
 *
 * @author Sage
 * @date 2023/02/02
 */
@Data
@Document(collection = "proxy")
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(def = "{'ip': 1, 'port': 1}", unique = true)
public class Proxy extends BaseEntity {

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 国家
     */
    private String country;

    /**
     * 省 (只限国内)
     */
    private String province;

    /**
     * 类型 (1 = http 2 = https 3 = socks4 4 = socks5)
     */
    private Integer type;

    /**
     * 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
     */
    private Integer anonymous;

    /**
     * 状态 (0 = 超时 1 = 存活)
     */
    private Integer status;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 最后检查时间/时间戳
     */
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Long lastCheckTime;

    /**
     * 响应时间 单位s 保留3位小数
     */
    private String respTime;

    /**
     * 校验统计
     */
    private Integer checkCount;

    /**
     * 超时统计
     */
    private Integer timeoutCount;

    /**
     * 存活率
     * (timeoutCount / checkCount) * 100
     * 小数四舍五入
     */
    private Integer survivalRate;

}
