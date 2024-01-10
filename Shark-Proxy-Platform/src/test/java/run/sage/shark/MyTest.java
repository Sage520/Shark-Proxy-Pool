package run.sage.shark;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import run.sage.shark.common.enums.ProxyEnum;
import run.sage.shark.common.utils.RegionUtils;
import run.sage.shark.project.entity.Proxy;
import run.sage.shark.project.mq.to.ProxyUpdateTo;
import run.sage.shark.project.repository.ProxyRepository;
import run.sage.shark.project.service.ProxyService;
import run.sage.shark.project.service.RabbitService;
import run.sage.shark.project.service.ScheduleService;
import sun.net.util.IPAddressUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Resource
    private ProxyService proxyService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RabbitService rabbitService;

    @Resource
    private ProxyRepository proxyRepository;

    @Resource
    private ScheduleService scheduleService;

    @Test
    public void test() throws Exception {
        System.out.println(IPAddressUtil.isIPv4LiteralAddress("202.5.28.777"));
    }

    @Test
    public void searcher() throws Exception {
        String search = RegionUtils.getRegionInfo("202.5.28.84");
        System.out.println(search);
    }

    @Test
    public void test333() throws Exception {
        DateTime dateTime = DateUtil.offsetHour(new Date(), -6);
        Query query1 = new Query(Criteria.where("lastCheckTime").lte(dateTime));
        query1.fields().include("ip").include("port").include("type");

        List<Proxy> proxies1 = mongoTemplate.find(query1, Proxy.class);
        System.out.println(proxies1.size());
        List<ProxyUpdateTo> tos = proxies1.stream().map(item -> {
            ProxyUpdateTo to = new ProxyUpdateTo();

            BeanUtil.copyProperties(item, to);
            to.setStatus(ProxyEnum.Status.SURVIVE.getCode());
            to.setFirstCheck(false);
            to.setLastCheckTime(new Date().getTime());
            to.setRespTime(1.23323);

            return to;
        }).collect(Collectors.toList());

        tos.forEach(rabbitService::sendProxyToUpdateQueue);
    }

    @Test
    public void countTest() throws Exception {
        Long integer = proxyRepository.countByStatus(ProxyEnum.Status.TIME_OUT.getCode());
        System.out.println(integer);
    }

}
