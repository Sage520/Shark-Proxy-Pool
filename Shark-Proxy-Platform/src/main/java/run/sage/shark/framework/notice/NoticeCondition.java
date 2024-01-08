package run.sage.shark.framework.notice;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 初始化通知服务条件
 *
 * @author Sage
 * @date 2023/04/04
 */
public class NoticeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return conditionContext.getEnvironment().getProperty("notice.enable").contains("true");
    }

}
