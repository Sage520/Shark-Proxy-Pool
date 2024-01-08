package run.sage.shark.framework.notice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通知属性配置
 *
 * @author Sage
 * @date 2023/04/04
 */
@Data
@Component
@ConfigurationProperties(prefix = "notice")
public class NoticeProperties {

    private Boolean enable;

    private String type;

    private ServerChanProperties serverChan;

}

/**
 * Server酱属性配置
 *
 * @author Sage
 * @date 2023/04/04
 */
@Data
class ServerChanProperties{

    /**
     * 密钥
     */
    private String key;

}