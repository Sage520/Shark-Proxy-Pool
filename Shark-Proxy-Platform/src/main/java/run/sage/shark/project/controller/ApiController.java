package run.sage.shark.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.sage.shark.common.web.AjaxResult;
import run.sage.shark.framework.rateLimiter.LimitType;
import run.sage.shark.framework.rateLimiter.RateLimiter;
import run.sage.shark.project.controller.dto.GetProxyDto;
import run.sage.shark.project.service.ProxyService;


/**
 * API控制器
 *
 * @author Sage
 * @date 2023/02/02
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ProxyService proxyService;

    /**
     * 获取代理
     *
     * @return {@link AjaxResult}
     */
    @RateLimiter(qps = 5, limitType = LimitType.IP)
    @GetMapping("/get")
    public Object getProxy(GetProxyDto proxy) {
        return proxyService.getProxy(proxy);
    }
}
