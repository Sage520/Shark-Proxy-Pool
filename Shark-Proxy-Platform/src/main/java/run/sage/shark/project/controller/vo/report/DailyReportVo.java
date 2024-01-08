package run.sage.shark.project.controller.vo.report;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 日报VO
 *
 * @author Sage
 * @date 2023/04/04
 */
@Data
public class DailyReportVo implements Serializable {

    /**
     * 总计
     */
    private long total = 0;

    /**
     * 昨日添加
     */
    private long yesterdayAdd;

    /**
     * 类型统计
     */
    private Map<String, Long> typeCount;

    /**
     * 匿名统计
     */
    private Map<String, Long> anonymousCount;

    /**
     * 状态统计
     */
    private Map<String, Long> statusCount;

    /**
     * 来源统计 / 今日数据
     */
    private Map<String, Long> sourceCount;

}
