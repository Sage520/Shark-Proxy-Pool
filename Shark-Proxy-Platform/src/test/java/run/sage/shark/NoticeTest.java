package run.sage.shark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.common.enums.ProxyEnum;
import run.sage.shark.framework.notice.NoticeService;
import run.sage.shark.project.controller.vo.report.DailyReportVo;
import run.sage.shark.project.service.ReportService;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoticeTest {

    @Autowired
    NoticeService noticeService;

    @Resource
    ReportService reportService;

    @Test
    public void dailyReport() throws Exception {
        String title = "[Shark-Proxy]: 日报";

        DailyReportVo dailyReportVo = reportService.dailyReport();
        String text = String.format(
                "当前总代理数：%s\n当前总存活代理数：%s\n当前总超时代理数：%s\n昨日新增代理数：%s\n",
                dailyReportVo.getTotal(),
                dailyReportVo.getStatusCount().get(ProxyEnum.Status.SURVIVE.getName()),
                dailyReportVo.getStatusCount().get(ProxyEnum.Status.TIME_OUT.getName()),
                dailyReportVo.getYesterdayAdd()
        );

        noticeService.sendMessage(title, text);
    }

}
