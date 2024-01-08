package run.sage.shark.project.service;

import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.sage.shark.project.controller.vo.page.CountProxyVo;
import run.sage.shark.project.controller.vo.page.IndexPageVo;

import java.util.concurrent.ExecutionException;

/**
 * 缓存服务
 * @author sage
 * @date 2024/01/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    /**
     * 分页数量
     */
    private static final int PAGE_SIZE = 10;

    private final LoadingCache<String, Object> pageCache;

    private final ProxyService proxyService;

    /**
     * 获取首页数据
     *
     * @param pageNo
     * @return {@link Object}
     * @throws ExecutionException
     */
    public Object getIndexData(Integer pageNo) throws ExecutionException {
        String cacheKey = "index:" + pageNo.toString();

        return pageCache.get(cacheKey, () -> {
            // 无缓存查数据
            IndexPageVo indexPageData = proxyService.getIndexPageData(pageNo, PAGE_SIZE);
            pageCache.put(cacheKey, indexPageData);

            return indexPageData;
        });
    }

    /**
     * 获取统计数据
     *
     * @return {@link Object}
     * @throws ExecutionException
     */
    public Object getCountData() throws ExecutionException {
        String cacheKey = "count";

        return pageCache.get(cacheKey, () -> {
            // 无缓存查数据
            CountProxyVo countProxyVo = proxyService.countProxy();
            pageCache.put(cacheKey, countProxyVo);

            return countProxyVo;
        });
    }
}
