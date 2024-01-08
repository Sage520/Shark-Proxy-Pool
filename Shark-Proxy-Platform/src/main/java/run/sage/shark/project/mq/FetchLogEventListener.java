package run.sage.shark.project.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import run.sage.shark.common.constant.RabbitConstants;
import run.sage.shark.project.mq.to.FetchLogAddTo;
import run.sage.shark.project.service.FetchLogService;

/**
 * MQ抓取日志事件监听器
 *
 * @author Sage
 * @date 2023/05/17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FetchLogEventListener {

    private final FetchLogService fetchLogService;

    /**
     * 新增抓取日志
     *
     * @param fetchLogAddTo 新增抓取日志TO
     */
    @RabbitListener(queues = RabbitConstants.MQ_FETCH_LOG_ADD_QUEUE)
    public void addFetchLog(FetchLogAddTo fetchLogAddTo) {
        try {
            fetchLogService.addFetchLog(fetchLogAddTo);
        } catch (Exception e) {
            log.error("新增抓取日志队列消费出错: {}", e.getMessage());
        }
    }
}
