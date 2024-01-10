package run.sage.shark.project.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import run.sage.shark.project.entity.Proxy;
import run.sage.shark.project.service.ProxyService;
import run.sage.shark.project.service.ScheduleService;

import java.util.Date;
import java.util.List;

/**
 * 定时任务服务
 *
 * @author Sage
 * @date 2023/05/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final MongoTemplate mongoTemplate;

    private final ProxyService proxyService;

    @Override
    public void checkProxy() {
        // 距离上次检测超过6小时
        // 排除首次校验就失败的代理(存活率 = null)
        DateTime dateTime1 = DateUtil.offsetHour(new Date(), -6);
        Query query1 = new Query(Criteria.where("lastCheckTime").lte(dateTime1.getTime()).and("survivalRate").ne(null));
        query1.fields().include("ip").include("port").include("type");

        List<Proxy> proxyList = mongoTemplate.find(query1, Proxy.class);
        for (Proxy proxy : proxyList) {
            proxyService.checkProxy(proxy, ObjectUtil.isNull(proxy.getType()));
        }
    }
}
