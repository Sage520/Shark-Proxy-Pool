package run.sage.shark.project.service;

import run.sage.shark.project.entity.to.FetchLogAddTo;

/**
 * 抓取日志服务
 *
 * @author sage
 * @date 2023/05/13
 */
public interface FetchLogService {

    /**
     * 添加获取日志
     *
     * @param fetchLogAddTo 获取日志
     */
    void addFetchLog(FetchLogAddTo fetchLogAddTo);

}
