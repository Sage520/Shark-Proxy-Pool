package run.sage.shark.project.service;

import run.sage.shark.project.controller.dto.GetProxyDto;
import run.sage.shark.project.controller.vo.api.GetProxyVo;
import run.sage.shark.project.controller.vo.page.CountProxyVo;
import run.sage.shark.project.controller.vo.page.IndexPageVo;
import run.sage.shark.project.mq.to.ProxyAddTo;
import run.sage.shark.project.mq.to.ProxyCheckTo;
import run.sage.shark.project.mq.to.ProxyGetRegionTo;
import run.sage.shark.project.mq.to.ProxyUpdateTo;

/**
 * 代理服务
 *
 * @author Sage
 * @date 2023/02/02
 */
public interface ProxyService {

    /**
     * 添加代理
     *
     * @param proxy 代理
     */
    void addProxy(ProxyAddTo proxy);

    /**
     * 更新代理
     *
     * @param proxy 代理
     */
    void updateProxy(ProxyUpdateTo proxy);

    /**
     * 获取地区信息
     *
     * @param proxy 代理
     */
    void getRegionInfo(ProxyGetRegionTo proxy);

    /**
     * 获得代理
     *
     * @param proxy 代理
     * @return {@link GetProxyVo}
     */
    Object getProxy(GetProxyDto proxy);

    /**
     * 统计代理
     *
     * @return {@link CountProxyVo}
     */
    CountProxyVo countProxy();

    /**
     * 验证代理
     *
     * @param proxy 代理
     */
    void checkProxy(ProxyCheckTo proxyCheckTo);

    /**
     * 延迟验证代理
     *
     * @param proxy 代理
     */
    void checkProxyDelay(ProxyCheckTo proxyCheckTo);

    /**
     * 查询首页页面数据
     *
     * @param pageNo   当前页
     * @param pageSize 每页数量
     * @return {@link IndexPageVo}
     */
    IndexPageVo getIndexPageData(Integer pageNo, Integer pageSize);

    /**
     * 统计昨日新增代理数
     *
     * @return long
     */
    long countByYesterdayAdd();

}
