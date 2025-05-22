package run.sage.shark.framework.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 番石榴缓存配置
 *
 * @author Sage
 * @date 2023/04/04
 */
@Configuration
public class GuavaCacheConfig {

    /**
     * 缓存 首页和统计
     *
     * @return {@link LoadingCache}<{@link String}, {@link Object}>
     */
    @Bean
    public LoadingCache<String, Object> pageCache(){
        return CacheBuilder.newBuilder()
                // 设置并发级别为5，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                // 设置写缓存后10秒钟后过期
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 设置缓存容器的初始容量为8
                .initialCapacity(20)
                // 设置缓存最大容量为10，超过10之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(500)
                // 指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return null;
                    }
                });
    }
}
