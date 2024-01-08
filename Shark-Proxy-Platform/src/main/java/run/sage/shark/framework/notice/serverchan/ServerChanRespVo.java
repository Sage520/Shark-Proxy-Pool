package run.sage.shark.framework.notice.serverchan;

import lombok.Data;

import java.io.Serializable;

/**
 * Server酱响应体
 *
 * @author Sage
 * @date 2023/04/04
 */
@Data
public class ServerChanRespVo implements Serializable {

    private Integer code;

    private String message;

    private ServerChanRespDataVo data;

}

/**
 * Data字段
 *
 * @author Sage
 * @date 2023/04/04
 */
@Data
class ServerChanRespDataVo {

    private String pushId;

    private String readKey;

    private String error;

    private Integer errNo;

}