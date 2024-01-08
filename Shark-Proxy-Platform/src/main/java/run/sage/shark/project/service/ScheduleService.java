package run.sage.shark.project.service;

/**
 * 定时任务服务
 *
 * @author Sage
 * @date 2023/05/17
 */
public interface ScheduleService {

    /**
     * 校验代理
     */
    void checkProxy();

    /**
     * 清理代理
     */
    void cleanProxy();

    /**
     * 每日报告
     */
    void dailyReport();

}
