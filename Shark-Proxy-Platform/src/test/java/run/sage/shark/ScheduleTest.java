package run.sage.shark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.project.service.ScheduleService;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleTest {

    @Resource
    private ScheduleService scheduleService;

    @Test
    public void checkProxy() {
        scheduleService.checkProxy();
    }

}
