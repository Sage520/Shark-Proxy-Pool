package run.sage.shark.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.sage.shark.project.entity.FetchLog;
import run.sage.shark.project.entity.to.FetchLogAddTo;
import run.sage.shark.project.repository.FetchLogRepository;
import run.sage.shark.project.service.FetchLogService;


/**
 * 抓取日志服务
 *
 * @author sage
 * @date 2023/05/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FetchLogServiceImpl implements FetchLogService {

    private final FetchLogRepository fetchLogRepository;

    @Override
    public void addFetchLog(FetchLogAddTo fetchLogAddTo) {
        if (StrUtil.isNotBlank(fetchLogAddTo.getSource())) {
            FetchLog fetchLog = new FetchLog();
            BeanUtil.copyProperties(fetchLogAddTo, fetchLog);

            fetchLogRepository.insert(fetchLog);
        }
    }
}
