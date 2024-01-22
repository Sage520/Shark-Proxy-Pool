package run.sage.shark.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import run.sage.shark.project.entity.Proxy;

/**
 * 代理库
 *
 * @author Sage
 * @date 2023/02/02
 */
public interface ProxyRepository extends MongoRepository<Proxy, String> {

    /**
     * 查询更新代理需要指定信息
     *
     * @param ip   知识产权
     * @param port 港口
     * @return {@link Proxy}
     */
    @Query(fields = "{checkCount: 1, timeoutCount: 1, createTime: 1, anonymous: 1}")
    Proxy findUpdateProxyByIpAndPort(String ip, String port);

    /**
     * 根据存活状态统计
     *
     * @param status 状态
     * @return {@link long}
     */
    long countByStatus(Integer status);

    /**
     * 根据类型统计
     *
     * @param type 类型
     * @param status 状态
     * @return {@link long}
     */
    long countByTypeAndStatus(Integer type, Integer status);

    /**
     * 删除指定代理
     *
     * @param ip   知识产权
     * @param port 港口
     */
    void deleteByIpAndPort(String ip, String port);

}
