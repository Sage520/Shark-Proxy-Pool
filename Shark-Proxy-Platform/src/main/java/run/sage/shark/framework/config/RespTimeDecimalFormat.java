package run.sage.shark.framework.config;

import java.text.DecimalFormat;

/**
 * 响应时间格式化
 *
 * @author sage
 * @date 2023/05/17
 */
public class RespTimeDecimalFormat {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.###");

    public static synchronized String format(double value) {
        return DECIMAL_FORMAT.format(value);
    }

}
