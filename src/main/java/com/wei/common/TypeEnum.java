package com.wei.common;

/**
 * @author wlp
 * @date 2022/6/24
 **/
public enum TypeEnum {
    /**
     * 前端弹窗类型
     */
    SUCCESS("SUCCESS", "成功"),
    WARN("WARN", "警告"),
    INFO("INFO", "成功"),
    ERROR("ERROR", "错误"),
    LOAD("LOAD", "加载"),
    ;
    private final String type;
    private final String message;

    TypeEnum(String type, String message) {
        this.type = type;
        this.message = message;
    }

    private String getType() {
        return this.type;
    }

    private String getMessage() {
        return this.message;
    }
}
