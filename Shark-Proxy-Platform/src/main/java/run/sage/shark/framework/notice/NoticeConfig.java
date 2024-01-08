package run.sage.shark.framework.notice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import run.sage.shark.framework.notice.serverchan.NoticeServerChanCondition;
import run.sage.shark.framework.notice.serverchan.NoticeServerChanImpl;

import javax.annotation.Resource;

/**
 * 通知配置
 *
 * @author Sage
 * @date 2023/04/04
 */
@Conditional(NoticeCondition.class)
@Configuration
public class NoticeConfig {

    @Resource
    private NoticeProperties noticeProperties;

    @Bean
    @Conditional(NoticeServerChanCondition.class)
    public NoticeService serverChanNotice() {
        return new NoticeServerChanImpl(noticeProperties.getServerChan().getKey());
    }

}
