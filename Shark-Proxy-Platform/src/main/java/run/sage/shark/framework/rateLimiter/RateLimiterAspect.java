package run.sage.shark.framework.rateLimiter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import run.sage.shark.common.exception.base.BaseException;
import run.sage.shark.common.utils.IpUtils;
import run.sage.shark.common.utils.ServletUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 速度限制器切面
 *
 * @author Sage
 * @date 2023/04/04
 */
@Slf4j
@Aspect
@Component
public class RateLimiterAspect {

    /**
     * 单机缓存
     */
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    /**
     这里记得改成你自己注解实际的包路径,我看有些小伙伴说报错, 就是路径不对识别不到
     */
    @Pointcut("@annotation(run.sage.shark.framework.rateLimiter.RateLimiter)")
    public void rateLimit() {

    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, RateLimiter.class);
        if (rateLimiter != null && rateLimiter.qps() > RateLimiter.NOT_LIMITED) {
            double qps = rateLimiter.qps();
            String key = getCombineKey(rateLimiter, point);
            if (RATE_LIMITER_CACHE.get(key) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(key, com.google.common.util.concurrent.RateLimiter.create(qps));
            }

            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(key) != null && !RATE_LIMITER_CACHE.get(key).tryAcquire(rateLimiter.timeout(), rateLimiter.timeUnit())) {
                throw new BaseException("访问过于频繁，请稍候再试");
            }
        }
        return point.proceed();
    }

    private String getCombineKey(RateLimiter rateLimiter, JoinPoint point)
    {
        StringBuffer stringBuffer = new StringBuffer();
        if (rateLimiter.limitType() == LimitType.IP)
        {
            stringBuffer.append(IpUtils.getIpAddr(ServletUtils.getRequest())).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

}
