package run.sage.shark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.common.enums.ProxyEnum;
import run.sage.shark.project.mq.to.FetchLogAddTo;
import run.sage.shark.project.mq.to.ProxyAddTo;
import run.sage.shark.project.mq.to.ProxyGetRegionTo;
import run.sage.shark.project.mq.to.ProxyUpdateTo;
import run.sage.shark.project.service.FetchLogService;
import run.sage.shark.project.service.ProxyService;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitListenerTest {

    @Resource
    private ProxyService proxyService;

    @Resource
    private FetchLogService fetchLogService;

    @Test
    public void proxyAdd() throws Exception {
        ProxyAddTo proxyAddTo = new ProxyAddTo();
        proxyAddTo.setIp("1.1.1.1");
        proxyAddTo.setPort("80");
        proxyAddTo.setSource("test");

        proxyService.addProxy(proxyAddTo);
    }

    @Test
    public void proxyUpdate() throws Exception {
        ProxyUpdateTo proxyUpdateTo = new ProxyUpdateTo();
        proxyUpdateTo.setIp("1.1.1.1");
        proxyUpdateTo.setPort("80");
        proxyUpdateTo.setFirstCheck(true);
        proxyUpdateTo.setStatus(ProxyEnum.Status.SURVIVE.getCode());
        proxyUpdateTo.setAnonymous(ProxyEnum.Anonymous.ADVANCED_ANONYMOUS.getCode());
        proxyUpdateTo.setType(ProxyEnum.Type.HTTP.getCode());
        proxyUpdateTo.setLastCheckTime(System.currentTimeMillis());
        proxyUpdateTo.setRespTime(123.23d);

        proxyService.updateProxy(proxyUpdateTo);
    }

    @Test
    public void proxyRegion() throws Exception {
        ProxyGetRegionTo proxyGetRegionTo = new ProxyGetRegionTo();
        proxyGetRegionTo.setIp("1.1.1.1");

        proxyService.getRegionInfo(proxyGetRegionTo);
    }

    @Test
    public void fetchLogAdd() throws Exception {
        FetchLogAddTo fetchLogAddTo = new FetchLogAddTo();
        fetchLogAddTo.setSource("test");
        fetchLogAddTo.setValue(123);

        fetchLogService.addFetchLog(fetchLogAddTo);
    }

}
