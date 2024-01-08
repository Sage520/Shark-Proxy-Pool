package run.sage.shark.project.service;

import run.sage.shark.project.entity.vo.DailyReportVo;

/**
 * 报表服务
 *
 * @author Sage
 * @date 2023/04/04
 */
public interface ReportService {

    /**
     * 每日报告
     *
     * @return {@link DailyReportVo}
     */
    DailyReportVo dailyReport();

}
