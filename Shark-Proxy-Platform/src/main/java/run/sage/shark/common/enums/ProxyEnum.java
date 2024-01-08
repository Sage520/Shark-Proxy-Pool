package run.sage.shark.common.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * 代理枚举
 *
 * @author Sage
 * @date 2022/03/19
 */
public class ProxyEnum {

    /**
     * 类型
     *
     * @author Sage
     * @date 2023/02/03
     */
    public enum Type {
        HTTP(1, "http"),

        HTTPS(2, "https"),

        SOCKS4(3, "socks4"),

        SOCKS5(4, "socks5");

        private Integer code;

        private String name;

        Type(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        /**
         * 代码验证
         *
         * @param code 代码
         * @return boolean
         */
        public static boolean codeVerify(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Type item : Type.values()) {
                    if (item.getCode().equals(code)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * 查询指定枚举
         *
         * @param code 代码
         * @return boolean
         */
        public static Type getEnumByCode(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Type item : Type.values()) {
                    if (item.getCode().equals(code)) {
                        return item;
                    }
                }
            }

            return null;
        }
    }

    /**
     * 匿名度
     *
     * @author Sage
     * @date 2023/02/03
     */
    public enum Anonymous {
        TRANSPARENT(1, "透明"),

        ORDINARY_ANONYMOUS(2, "普匿"),

        ADVANCED_ANONYMOUS(3, "高匿");

        private Integer code;

        private String name;

        Anonymous(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        /**
         * 代码验证
         *
         * @param code 代码
         * @return boolean
         */
        public static boolean codeVerify(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Anonymous item : Anonymous.values()) {
                    if (item.getCode().equals(code)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * 查询指定枚举
         *
         * @param code 代码
         * @return boolean
         */
        public static Anonymous getEnumByCode(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Anonymous item : Anonymous.values()) {
                    if (item.getCode().equals(code)) {
                        return item;
                    }
                }
            }

            return null;
        }
    }

    /**
     * 状态
     *
     * @author Sage
     * @date 2023/02/03
     */
    public enum Status {
        TIME_OUT(0, "超时"),

        SURVIVE(1, "存活");

        private Integer code;

        private String name;

        Status(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        /**
         * 代码验证
         *
         * @param code 代码
         * @return boolean
         */
        public static boolean codeVerify(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Status item : Status.values()) {
                    if (item.getCode().equals(code)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * 查询指定枚举
         *
         * @param code 代码
         * @return boolean
         */
        public static Status getEnumByCode(Integer code) {
            if (ObjectUtil.isNotNull(code)) {
                for (Status item : Status.values()) {
                    if (item.getCode().equals(code)) {
                        return item;
                    }
                }
            }

            return null;
        }
    }

}
