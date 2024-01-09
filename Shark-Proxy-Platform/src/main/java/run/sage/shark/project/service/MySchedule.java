package run.sage.shark.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.sage.shark.project.service.ScheduleService;

/**
 * 我的定时任务
 *
 * @author Sage
 * @date 2023/02/06
 */
@Component
@RequiredArgsConstructor
public class MySchedule {

    private final ScheduleService scheduleService;

    /**
     * 每 5分钟 检查代理
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    private void checkProxy() {
        scheduleService.checkProxy();
    }

    /**
     * 每天早上10点 发送日报
     */
    @Scheduled(cron = "0 0 10 * * ?")
    private void dailyReport() {
        scheduleService.dailyReport();
    }

}
