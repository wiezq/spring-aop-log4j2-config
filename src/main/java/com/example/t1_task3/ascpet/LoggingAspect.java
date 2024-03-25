package com.example.t1_task3.ascpet;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.example.t1_task3.service.*.*(..))")
    public void loggingPointcut() {
    }

    @Before("loggingPointcut()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Calling method: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "loggingPointcut()", returning = "result")
    public void logAfterMethodCall(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} returned: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "ex")
    public void logAfterMethodThrowsException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("Method {} threw exception: {}", methodName, ex.getMessage());
    }
}