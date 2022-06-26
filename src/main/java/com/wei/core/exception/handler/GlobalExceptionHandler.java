package com.wei.core.exception.handler;

import com.wei.common.Result;
import com.wei.core.exception.AuthException;
import com.wei.core.exception.BusinessException;
import com.wei.core.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result exceptionHandle(HttpMessageNotReadableException e) {
        log.warn("参数不合法", e);
        return Result.failed("参数不合法");
    }

    /**
     * 业务异常
     *
     * @param businessException 异常
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result exceptionHandle(BusinessException businessException) {
        log.warn("业务异常", businessException);
        return Result.failed(businessException.getMessage());
    }

    /**
     * 参数异常
     *
     * @param paramException 异常
     * @return
     */
    @ExceptionHandler(ParamException.class)
    public Result exceptionHandle(ParamException paramException) {
        log.warn("参数异常", paramException);
        return Result.failed(paramException.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result duplicateKeyHandle(DuplicateKeyException exception) {
        log.warn("数据已存在", exception);
        return Result.failed("数据已存在");
    }

    /**
     * 参数异常
     *
     * @param Exception 异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandle(Exception exception) {
        log.warn("运行异常", exception);
        return Result.failed("操作执行失败");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandle(MethodArgumentNotValidException methodArgumentNotValidException) {
        String msg = "参数验证失败";
        log.warn(msg, methodArgumentNotValidException);
        String errMsg = msg;
        List<FieldError> errsArray = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        errMsg = errsArray.get(0).getDefaultMessage();
        return Result.failed(errMsg);
    }

    @ExceptionHandler(AuthException.class)
    public Result exceptionHandle(AuthException exception) {
        log.warn("未登录或登录过期", exception);
        return Result.unauthorized();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result exceptionHandle(AccessDeniedException exception) {
        log.warn("不允许访问", exception);
        return Result.forbidden();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result exceptionHandle(MaxUploadSizeExceededException maxUploadSizeExceededException){
        String msg =  "上传大小超限";
        Matcher m = Pattern.compile("\\d+").matcher(Objects.requireNonNull(maxUploadSizeExceededException.getMessage()));
        long s = 0L;
        while (m.find()){
            s = Long.parseLong(m.group()) /1048576L;
        }
        log.error(msg, maxUploadSizeExceededException);
        if (s > 0L ){
            return Result.failed(String.format("上传文件大小不能超出%dMB",s));
        }
        return Result.failed(msg);
    }

    @ExceptionHandler(ParseException.class)
    public Result exceptionHandle(ParseException parseException){
        log.error(parseException.getMessage());
        return Result.failed("数据格式错误");
    }
}