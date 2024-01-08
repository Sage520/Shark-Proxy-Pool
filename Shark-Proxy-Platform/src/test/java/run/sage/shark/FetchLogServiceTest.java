package run.sage.shark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.project.mq.to.FetchLogAddTo;
import run.sage.shark.project.service.FetchLogService;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FetchLogServiceTest {

    @Resource
    private FetchLogService fetchLogService;

    @Test
    public void addFetchLog() {
        FetchLogAddTo fetchLogAddTo = new FetchLogAddTo();

        fetchLogAddTo.setSource("system");
        fetchLogAddTo.setValue(100);

        fetchLogService.addFetchLog(fetchLogAddTo);
    }

}
