package com.wei.common;

/**
 * 通用返回对象
 *
 * @author Administrator
 */
public class Result<T> {
    private long code;
    private String type;
    private String message;
    private T data;

    protected Result() {
    }

    protected Result(long code) {
        this.code = code;
        this.message = CodeEnum.getMessageByCode(code);
        this.data = null;
        this.type = CodeEnum.getTypeByCode(code);
    }

    protected Result(long code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        this.type = CodeEnum.getTypeByCode(code);
    }

    protected Result(long code, T data) {
        this.code = code;
        this.message = CodeEnum.getMessageByCode(code);
        this.data = data;
        this.type = CodeEnum.getTypeByCode(code);
    }

    protected Result(long code, String message, String type, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.type = type;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<T>(CodeEnum.SUCCESS.getCode());
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(CodeEnum.SUCCESS.getCode(), data);
    }

    /**
     * 返回指定成功消息
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<T>(CodeEnum.SUCCESS.getCode(), message);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> Result<T> failed(ErrorCode errorCode) {
        return new Result<T>(errorCode.getCode());
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<T>(CodeEnum.FAILED.getCode(), message);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return failed(CodeEnum.FAILED);
    }

    /**
     * 账号或密码错误
     */
    public static <T> Result<T> accountOrPasswordError() {
        return new Result<T>(CodeEnum.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateParamsFailed() {
        return failed(CodeEnum.VALIDATE_PARAMS_FAILED);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<T>(CodeEnum.UNAUTHORIZED.getCode(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized() {
        return new Result<T>(CodeEnum.UNAUTHORIZED.getCode());
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> custom(long code) {
        return new Result<T>(code);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden() {
        return new Result<T>(CodeEnum.FORBIDDEN.getCode(), CodeEnum.FORBIDDEN.getMessage());
    }

    /**
     * 两次输入的密码不一致
     *
     * @return
     */
    public static <T> Result<T> confirmPasswordFailed() {
        return new Result<T>(CodeEnum.PASSWORD_CONFIRM_ERROR.getCode());
    }

    /**
     * 账号已存在
     *
     * @return
     */
    public static <T> Result<T> accountExisted() {
        return new Result<T>(CodeEnum.ACCOUNT_EXIST.getCode());
    }

    /**
     * 密码未更新
     *
     * @return
     */
    public static <T> Result<T> passwordNotUpdate() {
        return new Result<T>(CodeEnum.PASSWORD_NOT_UPDATED.getCode());
    }

    /**
     * 登录密码错误
     *
     * @return
     */
    public static <T> Result<T> passwordError() {
        return new Result<T>(CodeEnum.OLD_PASSWORD_ERROR.getCode());
    }

    public static <T> Result<T> nothingUpdated() {
        return new Result<T>(CodeEnum.NOTHING_UPDATED.getCode());
    }
}