package com.wei.constant;

/**
 * @author Administrator
 */
public class PatternConstant {
    public static final String PHONE = "^([\\s\\S]{0})$|^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    public static final String PHONE_MESSAGE = "手机号格式错误";

    public static final String ADMIN_USERNAME = "^[a-zA-Z0-9_]+$";
    public static final String ADMIN_USERNAME_MESSAGE = "账号只能是数字，字母，下划线组合";

    public static final String ADMIN_PASSWORD = "^[\\S][\\s\\S]{4,30}[\\S]$";
    public static final String ADMIN_PASSWORD_MESSAGE = "密码为6-32位非空字符串";

    public static final String TRIM_BLANK_STRING = "[\\s\\S]?|^[\\S][\\s\\S]*[\\S]$";
    public static final String TRIM_BLANK_STRING_MESSAGE = "不是有效字符串";

    public static final String API_PATH = "^/[a-zA-z]+(/\\w{0,}){0,}";
    public static final String API_PATH_MESSAGE = "不是有效路由";

    public static final String API_KEY = "^\\w+::\\w+";
    public static final String API_KEY_MESSAGE = "不是有效路由键名";
}
