package run.sage.shark.project.service.impl;

import org.springframework.stereotype.Service;
import run.sage.shark.common.enums.ProxyEnum;
import run.sage.shark.project.repository.ProxyRepository;
import run.sage.shark.project.service.ProxyService;
import run.sage.shark.project.service.ReportService;
import run.sage.shark.project.controller.vo.report.DailyReportVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 报表服务
 *
 * @author Sage
 * @date 2023/04/04
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ProxyRepository proxyRepository;

    @Resource
    private ProxyService proxyService;

    @Override
    public DailyReportVo dailyReport() {
        DailyReportVo vo = new DailyReportVo();

        // 存活状态
        ProxyEnum.Status[] statusList = ProxyEnum.Status.values();
        Map<String, Long> statusCount = new HashMap<>(statusList.length);
        for (ProxyEnum.Status status : statusList) {
            statusCount.put(status.getName(), proxyRepository.countByStatus(status.getCode()));
        }
        vo.setStatusCount(statusCount);

        // 总数
        vo.setTotal(statusCount.values().stream().mapToLong(Long::longValue).sum());

        // 昨日新增代理数
        vo.setYesterdayAdd(proxyService.countByYesterdayAdd());

        return vo;
    }

}
