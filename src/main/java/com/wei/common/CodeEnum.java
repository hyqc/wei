package com.wei.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 枚举了一些常用API操作码
 *
 * @author Administrator
 */
public enum CodeEnum implements ErrorCode {
    /**
     * 成功
     */
    SUCCESS(0, "操作成功", TypeEnum.SUCCESS.name()),
    NOTHING_UPDATED(10000, "没有任何更新", TypeEnum.WARN.name()),
    FAILED(50000, "操作失败", TypeEnum.ERROR.name()),
    VALIDATE_PARAMS_FAILED(40000, "无效参数", TypeEnum.ERROR.name()),
    UNAUTHORIZED(40100, "未登录或登录已过期", TypeEnum.ERROR.name()),
    FORBIDDEN(40300, "没有相关权限", TypeEnum.ERROR.name()),

    PASSWORD_CONFIRM_ERROR(40001, "两次输入的密码不一致", TypeEnum.ERROR.name()),
    ACCOUNT_EXIST(40002, "该账号已存在", TypeEnum.ERROR.name()),
    ACCOUNT_NOT_EXIST(40003, "该账号不存在", TypeEnum.ERROR.name()),
    ACCOUNT_LOCKED(40005, "该账号已被禁用", TypeEnum.ERROR.name()),
    ACCOUNT_OR_PASSWORD_ERROR(40006, "账号或密码错误", TypeEnum.ERROR.name()),
    PASSWORD_NOT_UPDATED(40007, "新旧密码不能相同", TypeEnum.ERROR.name()),
    OLD_PASSWORD_ERROR(40008, "旧密码错误", TypeEnum.ERROR.name());

    private final Long code;
    private final String message;
    private final String type;

    private CodeEnum(long code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getType() {
        return type;
    }

    public static final Map<Long, CodeEnum> MAP = new HashMap<Long, CodeEnum>();

    public static String getMessageByCode(long code) {
        return MAP.get(code).getMessage();
    }

    public static String getTypeByCode(long code) {
        return MAP.get(code).getType();
    }

    static {
        for (CodeEnum codeEnumCommon : CodeEnum.values()) {
            MAP.put(codeEnumCommon.getCode(), codeEnumCommon);
        }
    }
}