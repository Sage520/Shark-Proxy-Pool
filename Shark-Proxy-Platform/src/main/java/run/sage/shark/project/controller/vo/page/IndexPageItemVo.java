package run.sage.shark.project.controller.vo.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页页面Vo
 *
 * @author Sage
 * @date 2023/02/22
 */
@Data
public class IndexPageItemVo implements Serializable {

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
     * 类型 (1 = http 2 = https 3 = socks5)
     */
    private Integer type;

    /**
     * 匿名度 (1 = 透明 2 = 普匿 3 = 高匿)
     */
    private Integer anonymous;

    /**
     * 最后检查时间
     */
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date lastCheckTime;

    /**
     * 响应时间 单位s 保留3位小数
     */
    private String respTime;

    /**
     * 存活率
     */
    private Integer survivalRate;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportHttps;

    /**
     * 是否支持Post (0 = 不支持 1 = 支持)
     */
    private Integer supportPost;

}
