package com.wei.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
public class LogExecutionAspect {
    @Around("@annotation(com.wei.core.aop.LogExecutionTime)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getName();
        String methodName = methodSignature.getName();
        StopWatch stopWatch = new StopWatch(className + "->" +methodName);
        stopWatch.start(methodName);
        Object result = joinPoint.proceed();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return result;
    }

}
