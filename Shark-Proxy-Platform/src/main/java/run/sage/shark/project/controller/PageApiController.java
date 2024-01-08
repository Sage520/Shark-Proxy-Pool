package run.sage.shark.project.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.sage.shark.common.web.AjaxResult;
import run.sage.shark.framework.rateLimiter.LimitType;
import run.sage.shark.framework.rateLimiter.RateLimiter;
import run.sage.shark.project.service.CacheService;

import java.util.concurrent.ExecutionException;

/**
 * 页面API控制器
 *
 * @author Sage
 * @date 2023/02/22
 */
@Validated
@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class PageApiController {

    private final CacheService cacheService;

    /**
     * 首页 (最大5页)
     *
     * @param pageNo   分页页码
     * @return {@link AjaxResult}
     */
    @RateLimiter(qps = 5, limitType = LimitType.IP)
    @GetMapping("/list")
    public AjaxResult index(@RequestParam(name = "pageNo", defaultValue = "1") @Range(min = 1, max = 5) Integer pageNo) throws ExecutionException {
        return AjaxResult.success(cacheService.getIndexData(pageNo));
    }

    /**
     * 数量统计
     *
     * @return {@link AjaxResult}
     */
    @RateLimiter(qps = 5, limitType = LimitType.IP)
    @GetMapping("/count")
    public AjaxResult count() throws ExecutionException {
        return AjaxResult.success(cacheService.getCountData());
    }
}
