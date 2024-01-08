package run.sage.shark.framework.notice;

/**
 * 通知服务
 *
 * @author Sage
 * @date 2023/04/04
 */
public interface NoticeService {

    /**
     * 发送消息
     *
     * @param title     标题
     * @param text      文本
     * @return boolean
     */
    boolean sendMessage(String title, String text);

}
