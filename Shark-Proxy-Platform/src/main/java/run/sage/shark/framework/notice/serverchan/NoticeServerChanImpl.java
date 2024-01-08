package run.sage.shark.framework.notice.serverchan;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import run.sage.shark.framework.notice.NoticeService;

import java.util.HashMap;
import java.util.Map;

/**
 * Server酱通知服务实现
 *
 * @author Sage
 * @date 2023/04/04
 */
public class NoticeServerChanImpl implements NoticeService {

    private String url;

    public NoticeServerChanImpl(String key) {
        this.url = String.format("https://sctapi.ftqq.com/%s.send", key);
    }

    @Override
    public boolean sendMessage(String title, String text) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("title", title);
        param.put("desp", text);

        String res = HttpUtil.post(this.url, param);
        if (StrUtil.isNotBlank(res)) {
            ServerChanRespVo serverChanRespVo = JSONObject.parseObject(res, ServerChanRespVo.class);
            if (ObjectUtil.isNotNull(serverChanRespVo)) {
                if ("SUCCESS".equals(serverChanRespVo.getData().getError())) {
                    return true;
                }
            }
        }

        return false;
    }

}
