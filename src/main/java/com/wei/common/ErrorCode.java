package com.wei.common;

/**
 * 封装API的错误码
 *
 * @author Administrator
 */
public interface ErrorCode {
    /**
     * 获取错误码
     *
     * @return
     */
    long getCode();

    /**
     * 获取消息
     *
     * @return
     */
    String getMessage();

    /**
     * 获取消息弹出类型
     *
     * @return
     */
    String getType();
}