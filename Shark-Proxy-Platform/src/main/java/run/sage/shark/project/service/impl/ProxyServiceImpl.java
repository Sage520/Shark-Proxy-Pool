package run.sage.shark.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import run.sage.shark.common.constant.Constants;
import run.sage.shark.common.enums.ProxyEnum;
import run.sage.shark.common.exception.base.BaseException;
import run.sage.shark.common.utils.RegionUtils;
import run.sage.shark.common.web.AjaxResult;
import run.sage.shark.framework.config.RespTimeDecimalFormat;
import run.sage.shark.project.entity.Proxy;
import run.sage.shark.project.controller.vo.page.CountProxyVo;
import run.sage.shark.project.controller.dto.GetProxyDto;
import run.sage.shark.project.controller.vo.api.GetProxyVo;
import run.sage.shark.project.controller.vo.page.IndexPageItemVo;
import run.sage.shark.project.controller.vo.page.IndexPageVo;
import run.sage.shark.project.mq.to.ProxyAddTo;
import run.sage.shark.project.mq.to.ProxyCheckTo;
import run.sage.shark.project.mq.to.ProxyGetRegionTo;
import run.sage.shark.project.mq.to.ProxyUpdateTo;
import run.sage.shark.project.repository.ProxyRepository;
import run.sage.shark.project.service.ProxyService;
import run.sage.shark.project.service.RabbitService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 代理服务impl
 *
 * @author Sage
 * @date 2023/02/02
 */
@Slf4j
@Service
public class ProxyServiceImpl implements ProxyService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ProxyRepository proxyRepository;

    @Resource
    private RabbitService rabbitService;

    @Override
    public void addProxy(ProxyAddTo proxy) {
        log.info("新增代理, IP: {} Port: {}", proxy.getIp(), proxy.getPort());

        if (ObjectUtil.isNotNull(proxy)
                && StrUtil.isNotBlank(proxy.getIp())
                && StrUtil.isNotBlank(proxy.getPort())
                && StrUtil.isNotBlank(proxy.getSource())
                && ProxyEnum.Type.codeVerify(proxy.getType())) {

            Proxy addProxy = new Proxy();
            addProxy.setCheckCount(0);
            addProxy.setTimeoutCount(0);
            addProxy.setSurvivalRate(0);
            BeanUtil.copyProperties(proxy, addProxy);

            // 直接新增，依靠唯一索引去重
            proxyRepository.save(addProxy);

            // 新增代理立即投入验证队列
            this.checkProxy(addProxy, Boolean.TRUE);
        }
    }

    @Override
    public void updateProxy(ProxyUpdateTo proxy) {
        log.info("更新代理, IP: {} Port: {}", proxy.getIp(), proxy.getPort());

        if (ObjectUtil.isNotNull(proxy) && StrUtil.isNotBlank(proxy.getIp()) && StrUtil.isNotBlank(proxy.getPort())) {
            Query query = new Query(Criteria.where("ip").is(proxy.getIp()).and("port").is(proxy.getPort()));
            Update update = new Update();
            int timeoutCount = 0;

            if (ProxyEnum.Status.codeVerify(proxy.getStatus())) {
                update.set("status", proxy.getStatus());

                // 设置响应时间
                if (ProxyEnum.Status.TIME_OUT.getCode().equals(proxy.getStatus())) {
                    update.set("respTime", "0");
                    update.inc("timeoutCount", 1);
                    timeoutCount = 1;
                }
                if (ProxyEnum.Status.SURVIVE.getCode().equals(proxy.getStatus())) {
                    // 新加入代理的首次校验
                    if (proxy.getFirstCheck()) {
                        if (ProxyEnum.Type.codeVerify(proxy.getType()) && ProxyEnum.Anonymous.codeVerify(proxy.getAnonymous())) {
                            update.set("type", proxy.getType());
                            update.set("anonymous", proxy.getAnonymous());
                        } else {
                            throw new BaseException("代理存活但类型，匿名度字段不存在");
                        }

                        // 地区信息
                        ProxyGetRegionTo getRegionTo = new ProxyGetRegionTo();
                        getRegionTo.setIp(proxy.getIp());
                        getRegionInfo(getRegionTo);
                        update.set("country", getRegionTo.getCountry());
                        update.set("province", getRegionTo.getProvince());
                    }

                    update.set("respTime", RespTimeDecimalFormat.format(proxy.getRespTime()));
                }
            }

            update.set("lastCheckTime", proxy.getLastCheckTime());
            update.set("updateTime", System.currentTimeMillis());
            update.inc("checkCount", 1);

            // 存活率
            Proxy proxyEntity = proxyRepository.findCountByIpAndPort(proxy.getIp(), proxy.getPort());
            Integer survivalRate = calculationOfSurvival(proxyEntity.getCheckCount() + 1, proxyEntity.getTimeoutCount() + timeoutCount);
            update.set("survivalRate", survivalRate);

            // 判断代理是否需要清理
            DateTime dateTime = DateUtil.offsetDay(new Date(), -10);
            if (survivalRate < 30 && proxyEntity.getCreateTime() <= dateTime.getTime()) {
                // 创建超过10天，存活率低于30
                proxyRepository.deleteByIpAndPort(proxy.getIp(), proxy.getPort());
            } else {
                mongoTemplate.updateFirst(query, update, Proxy.class);
            }
        }
    }

    /**
     * 计算存活率
     *
     * @param checkCount   校验统计
     * @param timeoutCount 超时统计
     * @return {@link Integer}
     */
    public Integer calculationOfSurvival(Integer checkCount, Integer timeoutCount) {
        long survivalRate = Math.round(((double) timeoutCount / checkCount) * 100);
        return (100 - Long.valueOf(survivalRate).intValue());
    }

    @Override
    public void getRegionInfo(ProxyGetRegionTo proxy) {
        log.info("获取代理地区信息, IP: {}", proxy.getIp());

        // 国家等信息补充
        String ipInfo = RegionUtils.getRegionInfo(proxy.getIp());
        if (ObjectUtil.isNotEmpty(ipInfo)) {
            String[] ipInfoArr = ipInfo.split("\\|");
            String country = ipInfoArr[0];
            String province = ipInfoArr[2];

            if (!Constants.REGION_NULL.equals(country)) {
                proxy.setCountry(country);
            }
            if (Constants.REGION_CHINA.equals(country)) {
                if (!Constants.REGION_NULL.equals(province)) {
                    proxy.setProvince(province);
                }
            }
        }
    }

    @Override
    public Object getProxy(GetProxyDto proxy) {
        if (StrUtil.isNotBlank(proxy.getRespType())) {
            if (!proxy.getRespType().equals(Constants.RESP_TYPE_JSON) && !proxy.getRespType().equals(Constants.RESP_TYPE_TXT)) {
                throw new BaseException("respType 参数错误，请重试");
            }
        }

        // 多条件查询
        List<String> fields = new ArrayList<>(8);
        fields.add("ip");
        fields.add("port");
        fields.add("status");
        fields.add("type");
        fields.add("anonymous");
        fields.add("lastCheckTime");

        Criteria criteria = Criteria.where("status").is(ProxyEnum.Status.SURVIVE.getCode());

        if (StrUtil.isNotBlank(proxy.getPort())) {
            criteria.and("port").is(proxy.getPort());
        }

        if (ObjectUtil.isNotNull(proxy.getType())) {
            if (!ProxyEnum.Type.codeVerify(proxy.getType())) {
                throw new BaseException("type 参数错误，请重试");
            }
            criteria.and("type").is(proxy.getType());
        }

        if (ObjectUtil.isNotNull(proxy.getAnonymous())) {
            if (!ProxyEnum.Anonymous.codeVerify(proxy.getAnonymous())) {
                throw new BaseException("anonymous 参数错误，请重试");
            }
            criteria.and("anonymous").is(proxy.getAnonymous());
        }

        if (ObjectUtil.isNotEmpty(proxy.getCountry())) {
            fields.add("country");

            // 外国为除中国之外
            if (Constants.REGION_EXTERNAL.equals(proxy.getCountry())) {
                criteria.and("country").ne(Constants.REGION_CHINA);
            } else {
                criteria.and("country").is(proxy.getCountry());
            }
        }

        if (ObjectUtil.isNotEmpty(proxy.getProvince())) {
            fields.add("province");
            criteria.and("province").is(proxy.getProvince());
        }

        Aggregation aggregation =
                Aggregation.newAggregation(
                         Aggregation.project(fields.toArray(new String[0])),
                        Aggregation.match(criteria),
                        Aggregation.sample(1));

        AggregationResults<Proxy> aggregate = mongoTemplate.aggregate(aggregation, Proxy.class, Proxy.class);

        // 返回数据
        Proxy res = aggregate.getUniqueMappedResult();
        if (ObjectUtil.isNotNull(res)) {
            // json
            if (Constants.RESP_TYPE_JSON.equals(proxy.getRespType())) {
                GetProxyVo vo = new GetProxyVo();
                vo.setIp(res.getIp());
                vo.setPort(res.getPort());
                vo.setType(res.getType());
                vo.setAnonymous(res.getAnonymous());
                vo.setLastCheckTime(res.getLastCheckTime());
                return AjaxResult.success(vo);
            }

            // text
            if (Constants.RESP_TYPE_TXT.equals(proxy.getRespType())) {
                return res.getIp() + ":" + res.getPort();
            }
        }

        return null;
    }

    @Override
    public CountProxyVo countProxy() {
        CountProxyVo vo = new CountProxyVo();

        // 存活代理
        Query surviveQuery = new Query(Criteria.where("status").is(ProxyEnum.Status.SURVIVE.getCode()));
        vo.setSurviveCount(mongoTemplate.count(surviveQuery, Proxy.class));

        // 类型统计
        ProxyEnum.Type[] typeList = ProxyEnum.Type.values();
        Map<String, Long> typeCountMap = new HashMap<>(typeList.length);
        for (ProxyEnum.Type type : typeList) {
            typeCountMap.put(type.getName(), proxyRepository.countByTypeAndStatus(type.getCode(), ProxyEnum.Status.SURVIVE.getCode()));
        }
        vo.setTypeCount(typeCountMap);

        return vo;
    }

    @Override
    public void checkProxy(List<Proxy> proxies, Boolean firstCheck) {
        if (CollectionUtil.isNotEmpty(proxies)) {
            proxies.forEach(item -> this.checkProxy(item, firstCheck));
        }
    }

    @Override
    public void checkProxy(Proxy proxy, Boolean firstCheck) {
        ProxyCheckTo to = new ProxyCheckTo();

        to.setIp(proxy.getIp());
        to.setPort(proxy.getPort());
        if (ProxyEnum.Type.codeVerify(proxy.getType())) {
            to.setType(proxy.getType());
        }
        to.setFirstCheck(firstCheck);

        rabbitService.sendProxyToCheckQueue(to);
    }

    @Override
    public IndexPageVo getIndexPageData(Integer pageNo, Integer pageSize) {
        Query query = new Query();
        IndexPageVo pageVo = new IndexPageVo();
        pageVo.setPageNo(pageNo);
        pageVo.setPageSize(pageSize);

        // 查询条件
        query.fields().include("ip").include("port").include("type").include("status").include("country").include("province").include("anonymous").include("lastCheckTime").include("respTime").include("survivalRate");
        query.addCriteria(Criteria.where("status").is(ProxyEnum.Status.SURVIVE.getCode()));

        // 总页码
        long count = mongoTemplate.count(query, Proxy.class);
        if (ObjectUtil.isNotNull(count)) {
            if (count > 50) {
                pageVo.setPageCount(50);
            } else {
                pageVo.setPageCount(count);
            }
        }

        // 分页参数
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        query.with(pageable);
        query.with(Sort.by(
                Sort.Order.desc("lastCheckTime"))
        );

        List<Proxy> proxyList = mongoTemplate.find(query, Proxy.class);
        List<IndexPageItemVo> itemVoList = proxyList.stream().map(proxy -> {
            IndexPageItemVo indexPageItemVo = new IndexPageItemVo();

            BeanUtil.copyProperties(proxy, indexPageItemVo);

            return indexPageItemVo;
        }).collect(Collectors.toList());
        pageVo.setList(itemVoList);

        return pageVo;
    }

    @Override
    public long countByYesterdayAdd() {
        DateTime yesterday = DateUtil.offsetDay(new Date(), -1);
        Date yesterdayDate = yesterday.toJdkDate();

        DateTime startTime = DateUtil.beginOfDay(yesterdayDate);
        DateTime endTime = DateUtil.endOfDay(yesterdayDate);

        Query query = new Query();
        Criteria criteria = Criteria
                .where("createTime").gte(startTime.getTime()).lte(endTime.getTime());
        query.addCriteria(criteria);

        return mongoTemplate.count(query, Proxy.class);
    }

}
